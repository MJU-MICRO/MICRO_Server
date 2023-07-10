package mju.sw.micro.domain.club.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.club.application.ClubRecruitmentDeleteService;
import mju.sw.micro.domain.club.dto.request.ClubRecruitmentDeleteRequest;
import mju.sw.micro.global.common.response.ApiResponse;

@Tag(name = "동아리 / 학회 모집공고 API", description = "동아리 및 학회의 모집공고를 삭제합니다.")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClubRecruitmentDeleteApi {

	private final ClubRecruitmentDeleteService recruitmentDeleteService;

	@Operation(summary = "동아리 / 학회 모집 공고 삭제 요청")
	@DeleteMapping("/recruitment")
	public ApiResponse<String> deleteClubRecruitment(@Valid @RequestBody ClubRecruitmentDeleteRequest request) {
		return recruitmentDeleteService.deleteClubRecruitment(request.toServiceRequest());
	}
}
