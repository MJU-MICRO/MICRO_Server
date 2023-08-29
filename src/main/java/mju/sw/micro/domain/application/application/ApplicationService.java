package mju.sw.micro.domain.application.application;

import static mju.sw.micro.global.error.exception.ErrorCode.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.application.dao.ApplicationRepository;
import mju.sw.micro.domain.application.domain.Application;
import mju.sw.micro.domain.application.dto.ApplicationRequestDto;
import mju.sw.micro.domain.application.dto.ApplicationResponseDto;
import mju.sw.micro.domain.group.dao.StudentGroupRepository;
import mju.sw.micro.domain.group.domain.StudentGroup;
import mju.sw.micro.domain.recruitment.dao.GroupRecruitmentRepository;
import mju.sw.micro.domain.recruitment.domain.GroupRecruitment;
import mju.sw.micro.domain.user.dao.UserRepository;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.security.CustomUserDetails;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationService {
	private final ApplicationRepository applicationRepository;
	private final GroupRecruitmentRepository groupRecruitmentRepository;
	private final UserRepository userRepository;
	private final StudentGroupRepository studentGroupRepository;

	public ApiResponse<String> saveApplication(ApplicationRequestDto dto, CustomUserDetails userDetails) {
		Optional<GroupRecruitment> optionalRecruitment = groupRecruitmentRepository.findById(dto.getRecruitmentId());
		if (optionalRecruitment.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}
		Optional<User> optionalUser = userRepository.findById(userDetails.getUserId());
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}
		Optional<Application> optionalApplication = applicationRepository.findByUserIdAndRecruitmentId(
			userDetails.getUserId(), dto.getRecruitmentId());
		if (optionalApplication.isPresent() && Boolean.TRUE.equals((optionalApplication.get().getIsSubmit()))) {
			return ApiResponse.withError(CONFLICT_APPLICATION);
		}
		GroupRecruitment groupRecruitment = optionalRecruitment.get();
		User user = optionalUser.get();

		Application application = new Application(dto);
		groupRecruitment.addApplication(application);
		user.addApplication(application);
		applicationRepository.save(application);
		return ApiResponse.ok("Application saved successfully.");
	}

	public ApiResponse<String> submitApplication(Long applicationId, CustomUserDetails userDetails) {
		Application application = validateApplication(applicationId, userDetails).getData();
		application.updateIsSubmit(true);
		return ApiResponse.ok("Application submitted successfully.");
	}

	public ApiResponse<String> setPassStatus(Long applicationId, Boolean passStatus, CustomUserDetails userDetails) {
		Application application = validateApplication(applicationId, userDetails).getData();
		application.updatePassStatus(passStatus);
		return ApiResponse.ok("Pass status set successfully.");
	}

	public ApiResponse<String> updateApplication(Long applicationId, ApplicationRequestDto dto,
		CustomUserDetails userDetails) {
		Application application = validateApplication(applicationId, userDetails).getData();
		if (application.getIsSubmit()) {
			return ApiResponse.withError(INVALID_UPDATE_OPERATION);
		}
		application.updateApplication(dto);
		return ApiResponse.ok("Application updated successfully.");
	}

	public ApiResponse<String> deleteApplication(Long applicationId, CustomUserDetails userDetails) {
		Application application = validateApplication(applicationId, userDetails).getData();
		if (application.getIsSubmit()) {
			return ApiResponse.withError(INVALID_DELETE_OPERATION);
		}
		applicationRepository.delete(application);
		return ApiResponse.ok("Application deleted successfully.");
	}

	public ApiResponse<List<ApplicationResponseDto>> getUserApplications(CustomUserDetails userDetails) {
		List<Application> applications = applicationRepository.findByUserId(userDetails.getUserId());
		List<ApplicationResponseDto> responseDtos = applications.stream()
			.filter(Application::getIsSubmit)
			.map(ApplicationResponseDto::fromApplication)
			.toList();
		return ApiResponse.ok(responseDtos);
	}

	public ApiResponse<List<ApplicationResponseDto>> getTemporalApplications(CustomUserDetails userDetails) {
		List<Application> applications = applicationRepository.findByUserId(userDetails.getUserId());
		List<ApplicationResponseDto> responseDtos = applications.stream()
			.filter(application -> !application.getIsSubmit())
			.map(ApplicationResponseDto::fromApplication)
			.toList();
		return ApiResponse.ok(responseDtos);
	}

	public ApiResponse<List<ApplicationResponseDto>> getPresidentApplications(CustomUserDetails userDetails,
		Long recruitmentId, Long groupId) {
		Long userId = userDetails.getUserId();
		List<StudentGroup> groupList = studentGroupRepository.findAllByPresidentId(userId);
		if (groupList.isEmpty()) {
			return ApiResponse.withError(FORBIDDEN_PRESIDENT);
		}
		Optional<StudentGroup> optionalStudentGroup = groupList.stream()
			.filter(studentGroup -> studentGroup.getId().equals(groupId))
			.findFirst();

		if (optionalStudentGroup.isEmpty()) {
			return ApiResponse.withError(BAD_REQUEST);
		}

		Optional<GroupRecruitment> optionalRecruitment = groupRecruitmentRepository.findById(recruitmentId);
		if (optionalRecruitment.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}
		StudentGroup studentGroup = optionalStudentGroup.get();
		GroupRecruitment recruitment = optionalRecruitment.get();
		if (!studentGroup.getId().equals(recruitment.getGroup().getId())) {
			return ApiResponse.withError(UNAUTHORIZED_RECRUITMENT);
		}
		List<Application> applications = applicationRepository.findByRecruitmentId(recruitmentId);
		List<ApplicationResponseDto> responseDtos = applications.stream()
			.map(ApplicationResponseDto::fromApplication)
			.toList();
		return ApiResponse.ok(responseDtos);
	}

	private ApiResponse<Application> validateApplication(Long applicationId, CustomUserDetails userDetails) {
		Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
		if (optionalApplication.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}
		Application application = optionalApplication.get();
		if (!application.getUser().getId().equals(userDetails.getUserId())) {
			return ApiResponse.withError(FORBIDDEN);
		}
		return ApiResponse.ok("Validation success.", application);
	}
}
