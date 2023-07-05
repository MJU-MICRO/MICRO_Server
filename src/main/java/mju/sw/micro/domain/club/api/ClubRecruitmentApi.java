package mju.sw.micro.domain.club.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.club.application.ClubRecruitmentService;
import mju.sw.micro.domain.club.dto.request.ClubRecruitmentCreateRequest;
import mju.sw.micro.global.common.response.ApiResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClubRecruitmentApi {

	private final ClubRecruitmentService recruitmentService;

	// TODO : @AuthenticationPrincipal을 통해 꺼내온 User 객체를 매개 변수에 추가한다.
	@PostMapping("/recruitment")
	public ApiResponse<String> createClubRecruitment(@Valid @RequestBody ClubRecruitmentCreateRequest request) {
		return recruitmentService.createClubRecruitment(request.toServiceRequest());
	}
}
