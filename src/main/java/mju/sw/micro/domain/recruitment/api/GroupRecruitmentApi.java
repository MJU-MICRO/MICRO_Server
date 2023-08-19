package mju.sw.micro.domain.recruitment.api;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.recruitment.application.GroupRecruitmentService;
import mju.sw.micro.domain.recruitment.dto.request.GroupRecruitmentCreateRequest;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.security.CustomUserDetails;

@Tag(name = "동아리 / 학회 모집 공고 API", description = "동아리 및 학회의 모집 공고를 등록 및 삭제 한다")
@RestController
@RequestMapping("/api/president")
@RequiredArgsConstructor
public class GroupRecruitmentApi {

	private final GroupRecruitmentService groupRecruitmentService;

	@Operation(summary = "동아리 / 학회 모집 공고 등록 요청")
	@PostMapping("/recruitment")
	public ApiResponse<String> createClubRecruitment(@AuthenticationPrincipal CustomUserDetails user,
		@Valid @RequestPart("request") GroupRecruitmentCreateRequest request,
		@RequestPart(value = "file", required = false) List<MultipartFile> files) {
		return groupRecruitmentService.createGroupRecruitment(user.getUserId(), request.toServiceRequest(), files);
	}

	@Operation(summary = "동아리 / 학회 모집 공고 삭제 요청")
	@DeleteMapping("/recruitment/{groupId}/{recruitmentId}")
	public ApiResponse<String> deleteClubRecruitment(@AuthenticationPrincipal CustomUserDetails user,
		@PathVariable Long groupId, @PathVariable Long recruitmentId) {
		return groupRecruitmentService.deleteGroupRecruitment(user.getUserId(), groupId, recruitmentId);
	}
}
