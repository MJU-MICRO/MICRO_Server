package mju.sw.micro.domain.application.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.application.application.ApplicationService;
import mju.sw.micro.domain.application.dto.ApplicationRequestDto;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "신청서 API", description = "신청서 CRUD")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationApi {
	private final ApplicationService applicationService;

	@Operation(summary = "신청서 저장")
	@PostMapping("/application/save")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<String> saveApplication(@Valid @RequestPart("dto") ApplicationRequestDto dto,
											  @AuthenticationPrincipal CustomUserDetails userDetails) {
		return applicationService.saveApplication(dto, userDetails);
	}

	@Operation(summary = "신청서 처음 임시저장")
	@PostMapping("/application/saveDraft")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<String> saveDraftApplication(@Valid @RequestPart("dto") ApplicationRequestDto dto,
											  @AuthenticationPrincipal CustomUserDetails userDetails) {
		return applicationService.saveDraftApplication(dto, userDetails);
	}
}
