package mju.sw.micro.domain.application.application;

import mju.sw.micro.domain.application.dao.ApplicationRepository;
import mju.sw.micro.domain.application.dto.ApplicationRequestDto;
import mju.sw.micro.domain.user.dao.UserRepository;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
	private final ApplicationRepository studentGroupDao;
	private final UserRepository userRepository;

	@Autowired
	public ApplicationService(ApplicationRepository studentGroupDao, UserRepository userRepository) {
		this.studentGroupDao = studentGroupDao;
		this.userRepository = userRepository;
	}

	public ApiResponse<String> saveApplication(ApplicationRequestDto dto, CustomUserDetails userDetails) {
		return ApiResponse.ok("OK");
	}

	public ApiResponse<String> saveDraftApplication(ApplicationRequestDto dto, CustomUserDetails userDetails) {
		return ApiResponse.ok("OK");
	}
}
