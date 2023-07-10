package mju.sw.micro.domain.club.api;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.club.application.ClubRecruitmentService;
import mju.sw.micro.domain.club.dto.request.ClubRecruitmentCreateRequest;
import mju.sw.micro.domain.club.dto.request.ClubRecruitmentUpdateRequest;
import mju.sw.micro.global.common.response.ApiResponse;

@Tag(name = "동아리 / 학회 모집공고 API", description = "동아리 및 학회의 모집공고를 등록, 수정")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClubRecruitmentApi {

	private final ClubRecruitmentService recruitmentService;

	// TODO : @AuthenticationPrincipal을 통해 꺼내온 User 객체를 매개 변수에 추가한다.
	@Operation(summary = "동아리 / 학회 모집 공고 등록 요청")
	@PostMapping("/recruitment")
	public ApiResponse<String> createClubRecruitment(@Valid @RequestBody ClubRecruitmentCreateRequest request) {
		return recruitmentService.createClubRecruitment(request.toServiceRequest());
	}

	@Operation(summary = "동아리 / 학회 모집 공고 수정 요청")
	@PatchMapping("/recruitment")
	public ApiResponse<String> updateClubRecruitment(@Valid @RequestBody ClubRecruitmentUpdateRequest request) {
		return recruitmentService.updateClubRecruitment(request.toServiceRequest());
	}
}
