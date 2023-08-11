package mju.sw.micro.domain.admin.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.admin.application.AdminService;
import mju.sw.micro.domain.admin.dto.request.AdminRequestDto;
import mju.sw.micro.global.common.response.ApiResponse;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApi {
	private final AdminService adminService;

	@Operation(summary = "관리자 등록")
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<Void> registerAdmin(@Validated AdminRequestDto dto) {
		return adminService.registerAdmin(dto.getEmail());
	}
}
