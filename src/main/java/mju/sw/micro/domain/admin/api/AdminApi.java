package mju.sw.micro.domain.admin.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.admin.application.AdminService;
import mju.sw.micro.domain.admin.dto.request.AdminRequestDto;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.global.common.response.ApiResponse;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApi {
	private final AdminService adminService;

	@Operation(summary = "관리자 권한 부여")
	@PatchMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<Void> registerAdmin(@Validated AdminRequestDto dto) {
		return adminService.registerAdmin(dto.getEmail());
	}

	@Operation(summary = "관리자 권한 해지")
	@PatchMapping("/revoke")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<Void> revokeAdmin(@Validated AdminRequestDto dto) {
		return adminService.revokeAdmin(dto.getEmail());
	}

	@Operation(summary = "관리자 권한으로 계정 삭제")
	@DeleteMapping("/user/delete")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<Void> deleteUserByAdmin(@Validated AdminRequestDto dto) {
		return adminService.deleteUserByAdmin(dto.getEmail());
	}

	@Operation(summary = "관리자 권한 계정 조회")
	@GetMapping("/retrieve")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<List<User>> getUsersByAdminRole() {
		return adminService.getUsersByAdminRole();
	}

	@Operation(summary = "관리자 권한 제외 계정 조회")
	@GetMapping("/user/retrieve")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<List<User>> getUsersWithoutAdminRole() {
		return adminService.getUsersWithoutAdminRole();
	}
}
