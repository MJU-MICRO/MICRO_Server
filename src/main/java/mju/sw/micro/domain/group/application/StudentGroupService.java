package mju.sw.micro.domain.group.application;

import static mju.sw.micro.domain.user.domain.Role.*;
import static mju.sw.micro.global.error.exception.ErrorCode.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mju.sw.micro.domain.group.dao.StudentGroupRepository;
import mju.sw.micro.domain.group.domain.StudentGroup;
import mju.sw.micro.domain.group.dto.StudentGroupRequestDto;
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

	private ApiResponse<String> uploadImage(MultipartFile multipartFile) {
		if (multipartFile == null) {
			return ApiResponse.ok("이미지가 없습니다", null);
		}
		return s3Uploader.uploadFile(multipartFile);
	}
}
