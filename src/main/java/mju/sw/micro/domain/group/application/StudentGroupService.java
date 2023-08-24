package mju.sw.micro.domain.group.application;

import static mju.sw.micro.domain.user.domain.Role.*;
import static mju.sw.micro.global.error.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import mju.sw.micro.domain.group.dao.StudentGroupRepository;
import mju.sw.micro.domain.group.domain.StudentGroup;
import mju.sw.micro.domain.group.dto.GroupSimpleResponseDto;
import mju.sw.micro.domain.group.dto.StudentGroupRequestDto;
import mju.sw.micro.domain.group.dto.StudentGroupResponseDto;
import mju.sw.micro.domain.user.dao.UserRepository;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.global.adapter.S3Uploader;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.security.CustomUserDetails;

@Service
public class StudentGroupService {
	private final StudentGroupRepository studentGroupDao;
	private final UserRepository userRepository;
	private final S3Uploader s3Uploader;

	@Autowired
	public StudentGroupService(StudentGroupRepository studentGroupDao, UserRepository userRepository,
		S3Uploader s3Uploader) {
		this.studentGroupDao = studentGroupDao;
		this.userRepository = userRepository;
		this.s3Uploader = s3Uploader;
	}

	public ApiResponse<String> applyGroupInfo(StudentGroupRequestDto dto, CustomUserDetails userDetails,
		MultipartFile imageFile) {
		String imageUrl = uploadImage(imageFile).getData();

		StudentGroup studentGroup = new StudentGroup(dto, userDetails, imageUrl);

		studentGroupDao.save(studentGroup);

		Optional<User> optionalUser = userRepository.findById(userDetails.getUserId());
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}

		User user = optionalUser.get();
		user.addRole(ROLE_PRESIDENT);
		userRepository.save(user);

		return ApiResponse.ok("Apply Group: SUCCESS / Add User Role: PRESIDENT");
	}

	private GroupSimpleResponseDto mapToGroupSimpleResponseDto(StudentGroup group) {
		GroupSimpleResponseDto responseDto = new GroupSimpleResponseDto();
		responseDto.setId(group.getId());
		responseDto.setGroupName(group.getGroupName());
		responseDto.setLogoImageUrl(group.getLogoImageUrl());
		responseDto.setIntroduction(group.getIntroduction());
		responseDto.setRelationMajor(group.getRelationMajor());
		responseDto.setRelatedTag(group.getRelatedTag());
		responseDto.setRecruit(group.isRecruit());
		responseDto.setApprove(group.isApprove());
		responseDto.setCampus(group.getCampus());
		responseDto.setSubCategory(group.getSubCategory());
		responseDto.setSmallCategory(group.getSmallCategory());
		responseDto.setMediumCategory(group.getMediumCategory());
		responseDto.setLargeCategory(group.getLargeCategory());
		responseDto.setPresidentId(group.getPresidentId());
		return responseDto;
	}

	public ApiResponse<List<GroupSimpleResponseDto>> getAllGroupInfo() {
		List<StudentGroup> groups = studentGroupDao.findAll();
		List<GroupSimpleResponseDto> groupInfoList = new ArrayList<>();

		for (StudentGroup group : groups) {
			groupInfoList.add(mapToGroupSimpleResponseDto(group));
		}

		return ApiResponse.ok("학생단체 리스트를 가져왔습니다", groupInfoList);
	}

	public ApiResponse<List<GroupSimpleResponseDto>> getApprovedGroupInfo() {
		List<StudentGroup> groups = studentGroupDao.findAll();
		List<GroupSimpleResponseDto> approvedGroupInfoList = groups.stream()
			.filter(StudentGroup::isApprove)
			.map(this::mapToGroupSimpleResponseDto)
			.collect(Collectors.toList());

		return ApiResponse.ok("승인된 학생단체 리스트를 가져왔습니다", approvedGroupInfoList);
	}

	public ApiResponse<String> updateGroupInfo(Long groupId, StudentGroupRequestDto dto, CustomUserDetails userDetails,
		MultipartFile imageFile) {
		Optional<StudentGroup> optionalGroup = studentGroupDao.findById(groupId);

		if (optionalGroup.isPresent()) {
			StudentGroup group = optionalGroup.get();
			if (group.getPresidentId().equals(userDetails.getUserId())) {
				group.setIntroduction(dto.getIntroduction());
				group.setRelationMajor(dto.getRelationMajor());
				group.setRelatedTag(dto.getRelatedTag());
				group.setCampus(dto.getCampus());
				group.setSubCategory(dto.getSubCategory());
				if (imageFile != null) {
					String imageUrl = uploadImage(imageFile).getData();
					group.setLogoImageUrl(imageUrl);
				}
				studentGroupDao.save(group);
				return ApiResponse.ok("학생단체 정보를 수정했습니다");
			} else {
				return ApiResponse.withError(UNAUTHORIZED);
			}
		} else {
			throw new NotFoundException("Group not found with id: " + groupId);
		}
	}

	public ApiResponse<StudentGroupResponseDto> getDetailGroupInfo(Long groupId) {
		StudentGroup studentGroup = studentGroupDao.findById(groupId)
			.orElseThrow(() -> new NotFoundException("Group not found with id: " + groupId));
		StudentGroupResponseDto responseDto = mapToStudentGroupResponseDto(studentGroup);
		return ApiResponse.ok("학생단체의 상세정보를 가져왔습니다", responseDto);
	}

	public ApiResponse<String> approveGroup(Long groupId) {
		Optional<StudentGroup> studentGroup = studentGroupDao.findById(groupId);
		if (studentGroup.isPresent()) {
			StudentGroup group = studentGroup.get();
			group.setApprove(!group.isApprove());
			studentGroupDao.save(group);
		} else {
			throw new NotFoundException("Group not found with id: " + groupId);
		}
		return ApiResponse.ok("승인 상태를 변경했습니다");
	}

	public ApiResponse<String> deleteGroup(CustomUserDetails userDetails, Long groupId) {
		Optional<User> optionalUser = userRepository.findById(userDetails.getUserId());
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}
		Optional<StudentGroup> studentGroup = studentGroupDao.findById(groupId);
		if (studentGroup.isPresent()) {
			Long presidentId = studentGroup.get().getPresidentId();
			if (presidentId.equals(userDetails.getUserId())) {
				studentGroupDao.deleteById(groupId);
			} else {
				return ApiResponse.withError(UNAUTHORIZED);
			}
		} else {
			throw new NotFoundException("Group not found with id: " + groupId);
		}
		return ApiResponse.ok("해당 단체를 삭제했습니다");
	}

	public ApiResponse<String> mandateGroupPresident(CustomUserDetails userDetails, Long groupId, Long userId) {
		Optional<User> optionalUser = userRepository.findById(userDetails.getUserId());
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}
		Optional<StudentGroup> studentGroup = studentGroupDao.findById(groupId);
		if (studentGroup.isPresent()) {
			Long presidentId = studentGroup.get().getPresidentId();
			if (presidentId.equals(userDetails.getUserId())) {
				Optional<User> successor = userRepository.findById(userId);
				if (successor.isEmpty()) {
					return ApiResponse.withError(NOT_FOUND);
				}
				StudentGroup group = studentGroup.get();
				group.setPresidentId(userId);
				studentGroupDao.save(group);
				successor.get().addRole(ROLE_PRESIDENT);
			} else {
				return ApiResponse.withError(UNAUTHORIZED);
			}
		} else {
			throw new NotFoundException("Group not found with id: " + groupId);
		}
		return ApiResponse.ok("회장 권한을 위임했습니다");
	}

	@Transactional
	public ApiResponse<String> toggleBookmark(Long userId, Long groupId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}
		User user = optionalUser.get();
		String groupIdString = String.valueOf(groupId);
		if (user.getBookmark().contains(groupIdString)) {
			user.getBookmark().remove(groupIdString);
			userRepository.save(user);
			return ApiResponse.ok("Bookmark removed successfully.");
		} else {
			user.getBookmark().add(groupIdString);
			userRepository.save(user);
			return ApiResponse.ok("Bookmark added successfully.");
		}
	}

	private ApiResponse<String> uploadImage(MultipartFile multipartFile) {
		if (multipartFile == null) {
			return ApiResponse.ok("이미지가 없습니다", null);
		}
		return s3Uploader.uploadFile(multipartFile);
	}

	public ApiResponse<List<StudentGroupResponseDto>> getBookmarkedGroups(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}
		User user = optionalUser.get();
		List<String> bookmarkedGroupIds = user.getBookmark();
		List<Long> validBookmarkedGroupIdLongs = new ArrayList<>();

		for (String groupIdString : bookmarkedGroupIds) {
			try {
				Long groupIdLong = Long.valueOf(groupIdString);
				Optional<StudentGroup> groupOptional = studentGroupDao.findById(groupIdLong);

				if (groupOptional.isPresent()) {
					validBookmarkedGroupIdLongs.add(groupIdLong);
				} else {
					user.getBookmark().remove(groupIdString);
				}
			} catch (NumberFormatException e) {
			}
		}
		user.setBookmark(validBookmarkedGroupIdLongs.stream().map(Object::toString).collect(Collectors.toList()));
		userRepository.save(user);

		List<StudentGroupResponseDto> bookmarkedGroups = validBookmarkedGroupIdLongs.stream()
			.map(groupId -> studentGroupDao.findById(groupId).orElse(null))
			.filter(Objects::nonNull)
			.map(this::mapToStudentGroupResponseDto)
			.collect(Collectors.toList());
		return ApiResponse.ok("북마크한 학생단체 리스트를 가져왔습니다", bookmarkedGroups);
	}

	private StudentGroupResponseDto mapToStudentGroupResponseDto(StudentGroup group) {
		StudentGroupResponseDto responseDto = new StudentGroupResponseDto();
		responseDto.setId(group.getId());
		responseDto.setPresidentId(group.getPresidentId());
		responseDto.setGroupName(group.getGroupName());
		responseDto.setLogoImageUrl(group.getLogoImageUrl());
		responseDto.setEstablishedYear(group.getEstablishedYear());
		responseDto.setNumOfMember(group.getNumOfMember());
		responseDto.setIntroduction(group.getIntroduction());
		responseDto.setRelationMajor(group.getRelationMajor());
		responseDto.setRelatedTag(group.getRelatedTag());
		responseDto.setActivityTitle(group.getActivityTitle());
		responseDto.setActivityContent(group.getActivityContent());
		responseDto.setRecruit(group.isRecruit());
		responseDto.setApprove(group.isApprove());
		responseDto.setCampus(group.getCampus());
		responseDto.setLargeCategory(group.getLargeCategory());
		responseDto.setMediumCategory(group.getMediumCategory());
		responseDto.setSmallCategory(group.getSmallCategory());
		responseDto.setSubCategory(group.getSubCategory());
		return responseDto;
	}
}
