package mju.sw.micro.domain.club.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.club.dao.ClubRecruitmentRepository;
import mju.sw.micro.domain.club.dao.ClubRepository;
import mju.sw.micro.domain.club.domain.Club;
import mju.sw.micro.domain.club.domain.ClubRecruitment;
import mju.sw.micro.domain.club.dto.request.ClubRecruitmentCreateServiceRequest;
import mju.sw.micro.domain.club.dto.request.ClubRecruitmentUpdateServiceRequest;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.error.exception.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubRecruitmentService {

	private final ClubRepository clubRepository;
	private final ClubRecruitmentRepository recruitmentRepository;

	// TODO: @AuthenticationPrincipal을 통해 꺼내온 User의 식별자와 Club에서 꺼내온 User의 식별자가 다르면 권한 x => 에러 반환 메소드(checkClubPresident) 구현

	@Transactional
	public ApiResponse<String> createClubRecruitment(ClubRecruitmentCreateServiceRequest request) {
		Optional<Club> optionalClub = clubRepository.findById(request.clubId());

		if (optionalClub.isEmpty()) {
			return ApiResponse.withError(ErrorCode.INVALID_CLUB_ID);
		}

		// TODO : checkClubPresident 메소드 호출

		Club club = optionalClub.get();
		ClubRecruitment recruitment = request.toEntity();
		club.addRecruitment(recruitment);

		recruitmentRepository.save(recruitment);

		return ApiResponse.ok("학생 단체(동아리/학회) 공고 등록에 성공했습니다.");
	}

	@Transactional
	public ApiResponse<String> updateClubRecruitment(ClubRecruitmentUpdateServiceRequest request) {
		Optional<ClubRecruitment> optionalRecruitment = recruitmentRepository.findById(request.recruitmentId());
		if (optionalRecruitment.isEmpty()) {
			return ApiResponse.withError(ErrorCode.INVALID_CLUB_RECRUITMENT_ID);
		}

		Optional<Club> optionalClub = clubRepository.findById(request.clubId());
		if (optionalClub.isEmpty()) {
			return ApiResponse.withError(ErrorCode.INVALID_CLUB_ID);
		}

		// TODO : checkClubPresident 메소드 호출
		ClubRecruitment recruitment = optionalRecruitment.get();
		recruitment.update(request);
		recruitmentRepository.save(recruitment);

		return ApiResponse.ok("학생 단체(동아리/학회) 공고 수정에 성공했습니다.");
	}
}
