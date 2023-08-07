package mju.sw.micro.domain.club.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import mju.sw.micro.domain.club.dto.request.ClubRequestDto;
import mju.sw.micro.domain.club.application.ClubService;
import mju.sw.micro.global.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClubController {
	private final ClubService clubService;

	@Autowired
	public ClubController(ClubService clubService) {
		this.clubService = clubService;
	}

	@Operation(summary = "학생단체 등록 요청")
	@PostMapping("/clubs/create")
	public ResponseEntity<String> saveClubInfo(@Valid @RequestBody ClubRequestDto requestDto) {
		clubService.saveClubInfo(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Club information saved successfully.");
	}

}
