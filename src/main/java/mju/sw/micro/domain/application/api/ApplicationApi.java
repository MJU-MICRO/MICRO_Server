package mju.sw.micro.domain.application.api;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.application.application.ApplicationService;
import mju.sw.micro.domain.application.dto.ApplicationRequestDto;
import mju.sw.micro.domain.application.dto.ApplicationResponseDto;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.security.CustomUserDetails;

@Tag(name = "신청서 API", description = "신청서 CRUD")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationApi {
	private final ApplicationService applicationService;

	@Operation(summary = "신청서 첫 저장/임시저장")
	@PostMapping("/application/initialSave")
	public ApiResponse<String> saveApplication(@Validated @RequestBody ApplicationRequestDto dto,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		return applicationService.saveApplication(dto, userDetails);
	}

	@Operation(summary = "임시 저장한 신청서 제출")
	@PutMapping("/application/submit/{applicationId}")
	public ApiResponse<String> submitApplication(@PathVariable Long applicationId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		return applicationService.submitApplication(applicationId, userDetails);
	}

	@Operation(summary = "신청서 합격/불합격 설정")
	@PutMapping("/president/application/setPassStatus/{applicationId}")
	public ApiResponse<String> setPassStatus(@PathVariable Long applicationId,
		@RequestParam Boolean passStatus,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		return applicationService.setPassStatus(applicationId, passStatus, userDetails);
	}

	@Operation(summary = "임시 저장한 신청서 수정")
	@PutMapping("/application/update/{applicationId}")
	public ApiResponse<String> updateApplication(@PathVariable Long applicationId,
		@Valid @RequestBody ApplicationRequestDto dto,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		return applicationService.updateApplication(applicationId, dto, userDetails);
	}

	@Operation(summary = "임시 저장한 신청서 삭제")
	@DeleteMapping("/application/delete/{applicationId}")
	public ApiResponse<String> deleteApplication(@PathVariable Long applicationId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		return applicationService.deleteApplication(applicationId, userDetails);
	}

	@Operation(summary = "지원자가 제출한 신청서 리스트 조회")
	@GetMapping("/application/userList")
	public ApiResponse<List<ApplicationResponseDto>> getUserApplications(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		return applicationService.getUserApplications(userDetails);
	}

	@Operation(summary = "지원자가 임시 저장한 신청서 리스트 조회")
	@GetMapping("/application/tempList")
	public ApiResponse<List<ApplicationResponseDto>> getTemporalApplications(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		return applicationService.getTemporalApplications(userDetails);
	}

	@Operation(summary = "회장이 확인할 신청서 리스트 조회")
	@GetMapping("/president/application/list")
	public ApiResponse<List<ApplicationResponseDto>> getPresidentApplications(@RequestParam Long recruitmentId,
		@RequestParam Long groupId, @AuthenticationPrincipal CustomUserDetails userDetails) {
		return applicationService.getPresidentApplications(userDetails, recruitmentId, groupId);
	}
}
