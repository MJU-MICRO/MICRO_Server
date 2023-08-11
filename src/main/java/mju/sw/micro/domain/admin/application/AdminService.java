package mju.sw.micro.domain.admin.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
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
		user.addRole(Role.ROLE_ADMIN);
		return ApiResponse.ok("관리자 권한 등록에 성공했습니다.");
	}

	@Transactional
	public ApiResponse<Void> revokeAdmin(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(ErrorCode.NOT_FOUND);
		}
		User user = optionalUser.get();
		user.deleteRole(Role.ROLE_ADMIN);
		return ApiResponse.ok("관리자 권한 해지에 성공했습니다.");
	}

	@Transactional
	public ApiResponse<Void> deleteUserByAdmin(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(ErrorCode.NOT_FOUND);
		}
		User user = optionalUser.get();
		userRepository.delete(user);
		mailService.sendMessage(user.getEmail(), EmailConstants.EMAIL_WITHDRAWAL_TITLE,
			EmailConstants.ADMIN_EMAIL_WITHDRAWAL_CONTENT_HTML, user.getEmail());
		return ApiResponse.ok("관리자가 회원을 삭제했습니다.");
	}
}
