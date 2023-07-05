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
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.error.exception.ErrorCode;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubRecruitmentService {

	private final ClubRepository clubRepository;
	private final ClubRecruitmentRepository recruitmentRepository;

	// TODO : @AuthenticationPrincipal에 담겨 있는 사용자가 학생 단체(동아리/학회) 회장 권한을 가지고 있을 경우에만 공고 등록이 가능하도록 한다.
	@Transactional
	public ApiResponse<String> createClubRecruitment(ClubRecruitmentCreateServiceRequest request) {
		Optional<Club> optionalClub = clubRepository.findById(request.getClubId());

		if (optionalClub.isEmpty()) {
			return ApiResponse.withError(ErrorCode.INVALID_CLUB_ID);
		}

		Club club = optionalClub.get();
		ClubRecruitment recruitment = request.toEntity();
		club.addRecruitment(recruitment);

		recruitmentRepository.save(recruitment);

		return ApiResponse.ok("학생 단체(동아리/학회) 공고 등록에 성공했습니다.");
	}

}
