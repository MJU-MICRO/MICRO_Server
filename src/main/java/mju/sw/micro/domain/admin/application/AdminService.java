package mju.sw.micro.domain.admin.application;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.admin.dto.response.AdminInfoResponseDto;
import mju.sw.micro.domain.user.dao.UserRepository;
import mju.sw.micro.domain.user.domain.Role;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.global.adapter.MailService;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.constants.EmailConstants;
import mju.sw.micro.global.error.exception.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
	private final MailService mailService;
	private final UserRepository userRepository;

	@Transactional
	public ApiResponse<Void> registerAdmin(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(ErrorCode.NOT_FOUND);
		}
		User user = optionalUser.get();
		if (user.isAdmin()) {
			return ApiResponse.withError(ErrorCode.BAD_REQUEST);
		}
		user.addRole(Role.ROLE_ADMIN);
		return ApiResponse.ok("관리자 권한 등록에 성공했습니다.");
	}

	@Transactional
	public ApiResponse<Void> revokeAdmin(String adminEmail, String reqEmail) {
		if (adminEmail.equals(reqEmail)) {
			return ApiResponse.withError(ErrorCode.ADMIN_NOT_REVOKE_MYSELF);
		}
		Optional<User> optionalUser = userRepository.findByEmail(reqEmail);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(ErrorCode.NOT_FOUND);
		}
		User user = optionalUser.get();
		if (!user.isAdmin()) {
			return ApiResponse.withError(ErrorCode.BAD_REQUEST);
		}
		user.deleteRole(Role.ROLE_ADMIN);
		return ApiResponse.ok("관리자 권한 해지에 성공했습니다.");
	}

	@Transactional
	public ApiResponse<Void> deleteUserByAdmin(String adminEmail, String reqEmail) {
		if (adminEmail.equals(reqEmail)) {
			return ApiResponse.withError(ErrorCode.ADMIN_NOT_DELETE_MYSELF);
		}
		Optional<User> optionalUser = userRepository.findByEmail(reqEmail);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(ErrorCode.NOT_FOUND);
		}
		User user = optionalUser.get();
		userRepository.delete(user);
		mailService.sendMessage(user.getEmail(), EmailConstants.EMAIL_WITHDRAWAL_TITLE,
			EmailConstants.ADMIN_EMAIL_WITHDRAWAL_CONTENT_HTML, user.getEmail());
		return ApiResponse.ok("관리자가 회원을 삭제했습니다.");
	}

	public ApiResponse<List<AdminInfoResponseDto>> getUsersByAdminRole() {
		List<User> adminList = userRepository.findAllUsersByAdminRole(Role.ROLE_ADMIN);
		if (adminList.isEmpty()) {
			return ApiResponse.ok("관리자 권한을 가진 계정이 없습니다.", Collections.emptyList());
		}
		List<AdminInfoResponseDto> responseDtoList = adminList.stream()
			.map(user -> new AdminInfoResponseDto(
				user.getName(),
				user.getCreatedDateTime().toLocalDate(),
				user.getPhoneNumber(),
				user.getEmail(),
				user.getProfileImageUrl()))
			.toList();
		return ApiResponse.ok("관리자 권한을 가진 모든 계정을 조회했습니다.", responseDtoList);
	}

	public ApiResponse<List<AdminInfoResponseDto>> getUsersWithoutAdminRole() {
		List<User> userList = userRepository.findAllUsersWithoutAdminRole(Role.ROLE_ADMIN);
		if (userList.isEmpty()) {
			return ApiResponse.ok("관리자 권한을 제외한 계정 정보가 없습니다.", Collections.emptyList());
		}
		List<AdminInfoResponseDto> responseDtoList = userList.stream()
			.map(user -> new AdminInfoResponseDto(
				user.getName(),
				user.getCreatedDateTime().toLocalDate(),
				user.getPhoneNumber(),
				user.getEmail(),
				user.getProfileImageUrl()))
			.toList();
		return ApiResponse.ok("관리자 권한을 갖지 않은 모든 계정을 조회했습니다.", responseDtoList);
	}

	@Transactional
	public ApiResponse<Void> registerBanned(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(ErrorCode.NOT_FOUND);
		}
		User user = optionalUser.get();
		if (user.isAdmin()) {
			return ApiResponse.withError(ErrorCode.BAD_REQUEST);
		}
		if (user.isBanned()) {
			return ApiResponse.withError(ErrorCode.BAD_REQUEST);
		}
		user.addRole(Role.ROLE_BANNED);
		return ApiResponse.ok("차단 권한 등록에 성공했습니다.");
	}

	@Transactional
	public ApiResponse<Void> revokeBanned(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(ErrorCode.NOT_FOUND);
		}
		User user = optionalUser.get();
		if (!user.isBanned()) {
			return ApiResponse.withError(ErrorCode.BAD_REQUEST);
		}
		user.deleteRole(Role.ROLE_BANNED);
		return ApiResponse.ok("차단 권한 해지에 성공했습니다.");
	}

}
