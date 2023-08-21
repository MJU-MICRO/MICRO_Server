package mju.sw.micro.domain.group.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.group.application.StudentGroupService;
import mju.sw.micro.domain.group.dto.GroupSimpleResponseDto;
import mju.sw.micro.domain.group.dto.StudentGroupRequestDto;
import mju.sw.micro.domain.group.dto.StudentGroupResponseDto;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.security.CustomUserDetails;

@Tag(name = "학생단체 API", description = "학생단체 CRUD")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentGroupApi {
	private final StudentGroupService studentGroupService;

	@Operation(summary = "학생단체 등록 요청")
	@PostMapping("/group/apply")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<String> applyGroupInfo(@Valid @RequestPart("dto") StudentGroupRequestDto dto,
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestPart(value = "file", required = false) MultipartFile imageFile) {
		return studentGroupService.applyGroupInfo(dto, userDetails, imageFile);
	}

	@Operation(summary = "전체 학생단체 리스트 조회")
	@GetMapping("/admin/group")
	public ApiResponse<List<GroupSimpleResponseDto>> getAllGroupInfo() {
		return studentGroupService.getAllGroupInfo();
	}

	@Operation(summary = "승인된 학생단체 리스트 조회")
	@GetMapping("/group")
	public ApiResponse<List<GroupSimpleResponseDto>> getApprovedGroupInfo() {
		return studentGroupService.getApprovedGroupInfo();
	}

	@Operation(summary = "학생단체 상세조회")
	@GetMapping("/group/{groupId}")
	public ApiResponse<StudentGroupResponseDto> getDetailGroupInfo(@PathVariable Long groupId) {
		return studentGroupService.getDetailGroupInfo(groupId);
	}

	@Operation(summary = "학생단체 승인/거부")
	@PutMapping("/admin/group/approve/{groupId}")
	public ApiResponse<String> approveGroup(@PathVariable Long groupId) {
		return studentGroupService.approveGroup(groupId);
	}

	@Operation(summary = "학생단체 삭제")
	@DeleteMapping("/president/group/{groupId}")
	public ApiResponse<String> deleteGroup(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long groupId) {
		return studentGroupService.deleteGroup(userDetails, groupId);
	}

	@Operation(summary = "학생단체 수정")
	@PutMapping("/president/group/{groupId}")
	public ApiResponse<String> updateGroupInfo(@PathVariable Long groupId,
		@Valid @RequestPart("dto") StudentGroupRequestDto dto,
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestPart(value = "file", required = false) MultipartFile imageFile) {
		return studentGroupService.updateGroupInfo(groupId, dto, userDetails, imageFile);
	}

	@Operation(summary = "회장 위임")
	@PutMapping("/president/group/mandate/{groupId}/{userId}")
	public ApiResponse<String> mandateGroupPresident(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long groupId, @PathVariable Long userId) {
		return studentGroupService.mandateGroupPresident(userDetails, groupId, userId);
	}

	@Operation(summary = "학생단체 북마크 추가 또는 삭제")
	@PutMapping("/bookmark/{groupId}")
	public ApiResponse<String> toggleBookmark(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long groupId) {
		return studentGroupService.toggleBookmark(userDetails.getUserId(), groupId);
	}

	@Operation(summary = "북마크한 학생단체 리스트 조회")
	@GetMapping("/bookmark/groups")
	public ApiResponse<List<StudentGroupResponseDto>> getBookmarkedGroups(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		return studentGroupService.getBookmarkedGroups(userDetails.getUserId());
	}
}
