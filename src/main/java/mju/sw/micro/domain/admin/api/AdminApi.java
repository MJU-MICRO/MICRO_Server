package mju.sw.micro.domain.admin.api;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.admin.application.AdminService;
import mju.sw.micro.domain.admin.dto.request.AdminRequestDto;
import mju.sw.micro.domain.admin.dto.response.AdminInfoResponseDto;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.security.CustomUserDetails;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApi {
	private final AdminService adminService;

	@Operation(summary = "관리자 권한 부여")
	@PatchMapping("/register")
	public ApiResponse<Void> registerAdmin(@Validated @RequestBody AdminRequestDto dto) {
		return adminService.registerAdmin(dto.getEmail());
	}

	@Operation(summary = "관리자 권한 해지")
	@PatchMapping("/revoke")
	public ApiResponse<Void> revokeAdmin(@AuthenticationPrincipal CustomUserDetails userDetails,
		@Validated @RequestBody AdminRequestDto dto) {
		return adminService.revokeAdmin(userDetails.getEmail(), dto.getEmail());
	}

	@Operation(summary = "관리자 권한으로 계정 삭제")
	@DeleteMapping("/user/delete")
	public ApiResponse<Void> deleteUserByAdmin(@AuthenticationPrincipal CustomUserDetails userDetails,
		@Validated @RequestBody AdminRequestDto dto) {
		return adminService.deleteUserByAdmin(userDetails.getEmail(), dto.getEmail());
	}

	@Operation(summary = "관리자 권한 계정 조회")
	@GetMapping("/retrieve")
	public ApiResponse<List<AdminInfoResponseDto>> getUsersByAdminRole() {
		return adminService.getUsersByAdminRole();
	}

	@Operation(summary = "관리자 권한 제외 계정 조회")
	@GetMapping("/user/retrieve")
	public ApiResponse<List<AdminInfoResponseDto>> getUsersWithoutAdminRole() {
		return adminService.getUsersWithoutAdminRole();
	}

	@Operation(summary = "차단 권한 부여")
	@PatchMapping("/banned/register")
	public ApiResponse<Void> registerBanned(@Validated @RequestBody AdminRequestDto dto) {
		return adminService.registerBanned(dto.getEmail());
	}

	@Operation(summary = "차단 권한 해지")
	@PatchMapping("/banned/revoke")
	public ApiResponse<Void> revokeBanned(@Validated @RequestBody AdminRequestDto dto) {
		return adminService.revokeBanned(dto.getEmail());
	}
}
