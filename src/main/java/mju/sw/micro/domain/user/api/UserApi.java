package mju.sw.micro.domain.user.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.user.application.UserService;
import mju.sw.micro.domain.user.dto.request.LogoutRequestDto;
import mju.sw.micro.domain.user.dto.request.UserModifyRequestDto;
import mju.sw.micro.domain.user.dto.response.UserInfoResponseDto;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.security.CustomUserDetails;

@Tag(name = "회원 API", description = "로그아웃, 회원 정보 조회 및 수정, 회원 탈퇴를 합니다.")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {
	private final UserService userService;

	/**
	 * 로그아웃, AuthApi에 로그아웃을 두지 않은 이유는 로그아웃 할 때는 UserDetails를 이용하기 때문에 토큰이 필요함
	 * 필터에서 api/auth는 anonymous로 설정되어 있어서 액세스 토큰이 있으면 권한이 있는 사용자이기 때문에 UnAuthorized 됨
	 */
	@Operation(summary = "로그아웃")
	@PostMapping("/logout")
	public ApiResponse<Void> logout(@Validated @RequestBody LogoutRequestDto dto,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		return userService.logout(dto, userDetails);
	}

	@Operation(summary = "회원 정보 조회")
	@GetMapping("/my")
	public ApiResponse<UserInfoResponseDto> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
		return userService.getUserInfo(userDetails.getEmail());
	}

	@Operation(summary = "회원 정보 수정")
	@PutMapping("/my")
	public ApiResponse<Void> modifyUserInfo(@Validated @RequestPart("dto") UserModifyRequestDto dto,
		@RequestPart(value = "file", required = false) MultipartFile imageFile,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		return userService.modifyUserInfo(dto, imageFile, userDetails.getEmail());
	}

	@Operation(summary = "회원 탈퇴")
	@DeleteMapping("/my")
	public ApiResponse<Void> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
		return userService.deleteUser(userDetails.getEmail());
	}
}
