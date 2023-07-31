package mju.sw.micro.domain.group.application;

import mju.sw.micro.domain.group.dao.StudentGroupRepository;
import mju.sw.micro.domain.group.domain.StudentGroup;
import mju.sw.micro.domain.group.dto.StudentGroupRequestDto;
import mju.sw.micro.domain.user.dao.UserRepository;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static mju.sw.micro.domain.user.domain.Role.ROLE_PRESIDENT;
import static mju.sw.micro.global.error.exception.ErrorCode.NOT_FOUND;

@Service
public class StudentGroupService {
	private final StudentGroupRepository studentGroupDao;
	private final UserRepository userRepository;

	@Autowired
	public StudentGroupService(StudentGroupRepository studentGroupDao, UserRepository userRepository) {
		this.studentGroupDao = studentGroupDao;
		this.userRepository = userRepository;
	}

	public ApiResponse<Object> saveGroupInfo(StudentGroupRequestDto requestDto, CustomUserDetails userDetails) {
		if (requestDto.getGroupName() == null || requestDto.getRelatedTag() == null || requestDto.getIntroduction() == null) {
			throw new IllegalArgumentException("Required fields exist.");
		}

		StudentGroup studentGroup = new StudentGroup(requestDto, userDetails);

		studentGroupDao.save(studentGroup);

		Optional<User> optionalUser = userRepository.findById(userDetails.getUserId());
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}
		optionalUser.get().addRole(ROLE_PRESIDENT);
		return ApiResponse.ok("Add Role: PRESIDENT");
	}
}
