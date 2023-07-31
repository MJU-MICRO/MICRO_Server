package mju.sw.micro.domain.group.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mju.sw.micro.domain.group.application.StudentGroupService;
import mju.sw.micro.domain.group.dto.StudentGroupRequestDto;
import mju.sw.micro.global.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "학생단체 API", description = "학생단체 CRUD")
@RestController
@RequestMapping("/api")
public class StudentGroupApi {
	private final StudentGroupService studentGroupService;

	@Autowired
	public StudentGroupApi(StudentGroupService studentGroupService) {
		this.studentGroupService = studentGroupService;
	}

	@Operation(summary = "학생단체 등록 요청")
	@PostMapping("/group/apply")
	public ResponseEntity<String> saveGroupInfo(@Valid @RequestBody StudentGroupRequestDto requestDto,
											   @AuthenticationPrincipal CustomUserDetails userDetails) {
		studentGroupService.saveGroupInfo(requestDto, userDetails);
		return ResponseEntity.status(HttpStatus.CREATED).body("SUCCESS");
	}
}
