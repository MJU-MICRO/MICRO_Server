package mju.sw.micro.domain.recruitment.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.recruitment.application.GroupRecruitmentRetrieveService;
import mju.sw.micro.domain.recruitment.domain.GroupRecruitment;
import mju.sw.micro.domain.recruitment.domain.RecruitmentImage;
import mju.sw.micro.domain.recruitment.dto.response.DetailGroupRecruitmentResponse;
import mju.sw.micro.domain.recruitment.dto.response.SimpleGroupRecruitmentResponse;
import mju.sw.micro.global.common.response.ApiResponse;

@Tag(name = "동아리 / 학회 모집공고 조회 API", description = "동아리 및 학회의 모집 공고를 조회한다.")
@RestController
@RequiredArgsConstructor
public class GroupRecruitmentRetrieveApi {

	private final GroupRecruitmentRetrieveService groupRecruitmentRetrieveService;

	@Operation(summary = "동아리 / 학회 모집 공고 전체 목록 조회 요청")
	@GetMapping("/api/recruitment")
	public ApiResponse
		<List<SimpleGroupRecruitmentResponse>> retrieveAllRecruitments() {
		return ApiResponse.ok("학생 단체(동아리/학회) 공고 전체 목록 조회에 성공했습니다.",
			groupRecruitmentRetrieveService.retrieveAllRecruitments());
	}

	@Operation(summary = "특정 동아리 / 학회 모집 공고 상세 조회 요청")
	@GetMapping("/api/recruitment/{recruitmentId}")
	public ApiResponse<DetailGroupRecruitmentResponse> retrieveAllRecruitments(@PathVariable Long recruitmentId) {
		return ApiResponse.ok("학생 단체(동아리/학회) 공고 상세 조회에 성공했습니다.",
			groupRecruitmentRetrieveService.retrieveRecruitment(recruitmentId));
	}

	@Operation(summary = "동아리 / 학회 모집 공고 삭제 요청")
	@GetMapping("/recruitments/images")
	public ApiResponse<List<RecruitmentImage>> getRecruitmentImages() {
		return ApiResponse.ok(groupRecruitmentRetrieveService.getRecruitmentImages());
	}

	@Operation(summary = "동아리 / 학회 모집 공고 전체 목록 조회(ERD)")
	@GetMapping("/recruitments")
	public ApiResponse<List<GroupRecruitment>> getAllRecruitment() {
		return ApiResponse.ok(groupRecruitmentRetrieveService.getAllRecruitment());
	}

}
