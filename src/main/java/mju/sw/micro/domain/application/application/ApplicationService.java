package mju.sw.micro.domain.application.application;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static mju.sw.micro.global.error.exception.ErrorCode.*;

@Service
public class ApplicationService {
	private final ApplicationRepository applicationRepository;
	private final GroupRecruitmentRepository groupRecruitmentRepository;
	private final UserRepository userRepository;
	private final StudentGroupRepository studentGroupRepository;

	@Autowired
	public ApplicationService(ApplicationRepository studentGroupDao,
							  UserRepository userRepository,
							  GroupRecruitmentRepository groupRecruitmentRepository,
							  StudentGroupRepository studentGroupRepository) {
		this.applicationRepository = studentGroupDao;
		this.userRepository = userRepository;
		this.groupRecruitmentRepository = groupRecruitmentRepository;
		this.studentGroupRepository = studentGroupRepository;
	}

	public ApiResponse<String> saveApplication(ApplicationRequestDto dto, CustomUserDetails userDetails) {
		Optional<GroupRecruitment> optionalRecruitment = groupRecruitmentRepository.findById(dto.getRecruitmentId());
		if (optionalRecruitment.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}
		Long userId = userDetails.getUserId();
		boolean hasExistingApplication = applicationRepository.existsByUserIdAndRecruitmentId(userId, dto.getRecruitmentId());
		if (hasExistingApplication) {
			return ApiResponse.withError(CONFLICT, "You already have an application for this recruitment.");
		}
		Application application = new Application(dto, userDetails);
		applicationRepository.save(application);
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}
		return ApiResponse.ok("Application saved successfully.");
	}

	public ApiResponse<String> submitApplication(Long applicationId, CustomUserDetails userDetails) {
		Application application = applicationRepository.findById(applicationId)
			.orElseThrow(() -> new RuntimeException("Application not found"));
		if (!application.getUserId().equals(userDetails.getUserId())) {
			return ApiResponse.withError(FORBIDDEN, "You don't have permission to submit this application.");
		}
		application.setIsSubmit(true);
		applicationRepository.save(application);
		return ApiResponse.ok("Application submitted successfully.");
	}

	public ApiResponse<String> setPassStatus(Long applicationId, Boolean passStatus, CustomUserDetails userDetails) {
		Application application = applicationRepository.findById(applicationId)
			.orElseThrow(() -> new RuntimeException("Application not found"));
		if (!application.getUserId().equals(userDetails.getUserId())) {
			return ApiResponse.withError(FORBIDDEN, "You don't have permission to set pass status for this application.");
		}
		application.setPassStatus(passStatus);
		applicationRepository.save(application);
		return ApiResponse.ok("Pass status set successfully.");
	}

	public ApiResponse<String> updateApplication(Long applicationId, ApplicationRequestDto dto, CustomUserDetails userDetails) {
		Application application = applicationRepository.findById(applicationId)
			.orElseThrow(() -> new RuntimeException("Application not found"));

		if (!application.getUserId().equals(userDetails.getUserId())) {
			return ApiResponse.withError(FORBIDDEN, "You don't have permission to update this application.");
		}
		if (application.getIsSubmit()) {
			return ApiResponse.withError(INVALID_UPDATE_OPERATION, "Cannot update a submitted application.");
		}
		application.setAnswers(dto.getAnswers());
		application.setGrade(dto.getGrade());
		application.setSupportField(dto.getSupportField());
		application.setIsAttending(dto.getIsAttending());
		applicationRepository.save(application);
		return ApiResponse.ok("Application updated successfully.");
	}

	public ApiResponse<String> deleteApplication(Long applicationId, CustomUserDetails userDetails) {
		Application application = applicationRepository.findById(applicationId)
			.orElseThrow(() -> new RuntimeException("Application not found"));
		if (!application.getUserId().equals(userDetails.getUserId())) {
			return ApiResponse.withError(FORBIDDEN, "You don't have permission to delete this application.");
		}
		if (application.getIsSubmit()) {
			return ApiResponse.withError(INVALID_DELETE_OPERATION, "Cannot delete a submitted application.");
		}
		applicationRepository.delete(application);
		return ApiResponse.ok("Application deleted successfully.");
	}

	public ApiResponse<List<ApplicationResponseDto>> getUserApplications(CustomUserDetails userDetails) {
		List<Application> applications = applicationRepository.findByUserId(userDetails.getUserId());
		List<ApplicationResponseDto> responseDtos = applications.stream()
			.map(ApplicationResponseDto::fromApplication)
			.collect(Collectors.toList());
		return ApiResponse.ok(responseDtos);
	}

	public ApiResponse<List<ApplicationResponseDto>> getPresidentApplications(CustomUserDetails userDetails,  Long recruitmentId) {
		Long userId = userDetails.getUserId();
		Optional<StudentGroup> optionalStudentGroup = studentGroupRepository.findByPresidentId(userId);
		if (optionalStudentGroup.isEmpty()) {
			return ApiResponse.withError(FORBIDDEN, "You are not the president of any group.");
		}
		Optional<GroupRecruitment> optionalRecruitment = groupRecruitmentRepository.findById(recruitmentId);
		if (optionalRecruitment.isEmpty()) {
			return ApiResponse.withError(NOT_FOUND);
		}
		StudentGroup studentGroup = optionalStudentGroup.get();
		GroupRecruitment recruitment = optionalRecruitment.get();
		if (!studentGroup.getId().equals(recruitment.getGroup().getId())) {
			return ApiResponse.withError(UNAUTHORIZED, "You are not authorized to access this recruitment.");
		}
		List<Application> applications = applicationRepository.findByRecruitmentId(recruitmentId);
		List<ApplicationResponseDto> responseDtos = applications.stream()
			.map(ApplicationResponseDto::fromApplication)
			.collect(Collectors.toList());
		return ApiResponse.ok(responseDtos);
	}
}
