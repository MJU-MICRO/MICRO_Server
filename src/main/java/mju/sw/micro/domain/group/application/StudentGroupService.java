package mju.sw.micro.domain.group.application;

import mju.sw.micro.domain.group.dao.StudentGroupRepository;
import mju.sw.micro.domain.group.domain.StudentGroup;
import mju.sw.micro.domain.group.dto.GroupSimpleResponseDto;
import mju.sw.micro.domain.group.dto.StudentGroupRequestDto;
import mju.sw.micro.domain.group.dto.StudentGroupResponseDto;
import mju.sw.micro.domain.user.dao.UserRepository;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.global.adapter.S3Uploader;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.error.exception.ErrorCode;
import mju.sw.micro.global.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static mju.sw.micro.domain.user.domain.Role.ROLE_PRESIDENT;
import static mju.sw.micro.global.error.exception.ErrorCode.NOT_FOUND;

@Service
public class StudentGroupService {
	private final StudentGroupRepository studentGroupDao;
	private final UserRepository userRepository;
	private final S3Uploader s3Uploader;

	@Autowired
	public StudentGroupService(StudentGroupRepository studentGroupDao, UserRepository userRepository, S3Uploader s3Uploader) {
		this.studentGroupDao = studentGroupDao;
		this.userRepository = userRepository;
		this.s3Uploader = s3Uploader;
	}

	public ApiResponse<String> applyGroupInfo(StudentGroupRequestDto dto, CustomUserDetails userDetails, MultipartFile imageFile) {
		String imageUrl = uploadImage(imageFile).getData();

		StudentGroup studentGroup = new StudentGroup(dto, userDetails, imageUrl);

		studentGroupDao.save(studentGroup);

		Optional<User> optionalUser = userRepository.findById(userDetails.getUserId());
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}
		optionalUser.get().addRole(ROLE_PRESIDENT);
		return ApiResponse.ok("Apply Group: SUCCESS / Add User Role: PRESIDENT");
	}

	public ApiResponse<List<GroupSimpleResponseDto>> getAllGroupInfo() {
		List<StudentGroup> groups = studentGroupDao.findAll();
		List<GroupSimpleResponseDto> groupInfoList = new ArrayList<>();

		for (StudentGroup group : groups) {
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

			groupInfoList.add(responseDto);
		}

		return ApiResponse.ok("학생단체 리스트를 가져왔습니다", groupInfoList);
	}

	public ApiResponse<StudentGroupResponseDto> getDetailGroupInfo(Long groupId) {
		StudentGroup studentGroup = studentGroupDao.findById(groupId).orElseThrow(() -> new NotFoundException("Group not found with id: " + groupId));
		StudentGroupResponseDto responseDto = new StudentGroupResponseDto();
		responseDto.setId(studentGroup.getId());
		responseDto.setPresidentId(studentGroup.getPresidentId());
		responseDto.setGroupName(studentGroup.getGroupName());
		responseDto.setLogoImageUrl(studentGroup.getLogoImageUrl());
		responseDto.setEstablishedYear(studentGroup.getEstablishedYear());
		responseDto.setNumOfMember(studentGroup.getNumOfMember());
		responseDto.setIntroduction(studentGroup.getIntroduction());
		responseDto.setRelationMajor(studentGroup.getRelationMajor());
		responseDto.setRelatedTag(studentGroup.getRelatedTag());
		responseDto.setActivityTitle(studentGroup.getActivityTitle());
		responseDto.setActivityContent(studentGroup.getActivityContent());
		responseDto.setRecruit(studentGroup.isRecruit());
		responseDto.setApprove(studentGroup.isApprove());
		responseDto.setCampus(studentGroup.getCampus());
		responseDto.setLargeCategory(studentGroup.getLargeCategory());
		responseDto.setMediumCategory(studentGroup.getMediumCategory());
		responseDto.setSmallCategory(studentGroup.getSmallCategory());
		responseDto.setSubCategory(studentGroup.getSubCategory());
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
//				return ApiResponse.withError("해당 단체를 삭제할 권한이 없습니다");
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
//				return ApiResponse.withError("권한이 없습니다");
			}
		} else {
			throw new NotFoundException("Group not found with id: " + groupId);
		}
		return ApiResponse.ok("회장 권한을 위임했습니다");
	}

	private ApiResponse<String> uploadImage(MultipartFile multipartFile) {
		if (multipartFile == null) {
			return ApiResponse.ok("이미지가 없습니다", null);
		}
		return s3Uploader.uploadFile(multipartFile);
	}
//
//	public ApiResponse updateGroup(StudentGroupRequestDto studentGroupRequestDto,CustomUserDetails userDetails, Long groupId) {
//		Optional<User> optionalUser = userRepository.findById(userDetails.getUserId());
//		if (optionalUser.isEmpty()) {
//			return ApiResponse.withError(NOT_FOUND);
//		}
//		Optional<StudentGroup> studentGroup = studentGroupDao.findById(groupId);
//		if(!studentGroup.isPresent()){
//			throw new NotFoundException("Group not found with id: " + groupId);
//		}
//		Long presidentId = studentGroup.get().getPresidentId();
//
//		if(!presidentId.equals(userDetails.getUserId())){
//			return ApiResponse.withError(ErrorCode.valueOf("해당 단체를 수정할 권한이 없습니다"));
//		}
//		List<StudentGroupRequestDto> list = new ArrayList<>();
//		list.add(studentGroupRequestDto);
//		if(list.get(0).getGroupName()!=null){
//			studentGroup.get().setGroupName(list.get(0).getGroupName());
//		}
//		if(list.get(0).getActivityContent()!=null){
//			studentGroup.get().setActivityContent(list.get(0).getActivityContent());
//		}
//		if(list.get(0).getIntroduction()!=null){
//			studentGroup.get().setIntroduction(list.get(0).getIntroduction());
//		}
//		if(list.get(0).getCampus()!=null){
//			studentGroup.get().setCampus(list.get(0).getCampus());
//		}
//		if(list.get(0).getLargeCategory()!=null){
//			studentGroup.get().setGroupName(list.get(0).getGroupName());
//		}
//		if(list.get(0).getEstablishedYear()!=0){
//			studentGroup.get().setEstablishedYear(list.get(0).getEstablishedYear());
//		}
//		if(list.get(0).getActivityTitle()!=null){
//			studentGroup.get().setActivityTitle(list.get(0).getActivityTitle());
//		}
//		if(list.get(0).getLogoImageUrl()!=null){
//			studentGroup.get().setLogoImageUrl(list.get(0).getLogoImageUrl());
//		}
//		if(list.get(0).getMediumCategory()!=null){
//			studentGroup.get().setMediumCategory(list.get(0).getMediumCategory());
//		}
//		if(list.get(0).getNumOfMember()!=null){
//			studentGroup.get().setNumOfMember(list.get(0).getNumOfMember());
//		}
//		if(list.get(0).getRelatedTag()!=null){
//			studentGroup.get().setRelatedTag(list.get(0).getRelatedTag());
//		}
//		if(list.get(0).getRelationMajor()!=null){
//			studentGroup.get().setRelationMajor(list.get(0).getRelationMajor());
//		}
//		if(list.get(0).getSmallCategory()!=null){
//			studentGroup.get().setSmallCategory(list.get(0).getSmallCategory());
//		}
//		if(list.get(0).getSubCategory()!=null){
//			studentGroup.get().setSubCategory(list.get(0).getSubCategory());
//		}
//		return ApiResponse.ok("해당 단체를 수정했습니다");
//	}
}
