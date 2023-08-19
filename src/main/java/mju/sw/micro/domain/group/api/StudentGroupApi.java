package mju.sw.micro.domain.group.api;

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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
	@PutMapping("/group/approve/{groupId}")
//	@PutMapping("/admin/group/approve/{groupId}")
	public ApiResponse<String> approveGroup(@PathVariable Long groupId) {
		return studentGroupService.approveGroup(groupId);
	}

	@Operation(summary = "학생단체 삭제")
	@DeleteMapping("/president/group/{groupId}")
	public ApiResponse<String> deleteGroup(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long groupId) {
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
	public ApiResponse<String> mandateGroupPresident(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long groupId, @PathVariable Long userId) {
		return studentGroupService.mandateGroupPresident(userDetails, groupId, userId);
	}
}
