package mju.sw.micro.domain.group.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.group.application.StudentGroupService;
import mju.sw.micro.domain.group.dto.StudentGroupRequestDto;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "학생단체 API", description = "학생단체 CRUD")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentGroupApi {
	private final StudentGroupService studentGroupService;

	@Operation(summary = "학생단체 등록 요청")
	@PostMapping("/group/apply")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<String> applyGroupInfo(@Valid @RequestPart("dto") StudentGroupRequestDto dto,
											  @AuthenticationPrincipal CustomUserDetails userDetails,
											  @RequestPart(value = "file", required = false) MultipartFile imageFile) {
		return studentGroupService.applyGroupInfo(dto, userDetails, imageFile);
	}
}
