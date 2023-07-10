package mju.sw.micro.domain.club.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.club.dao.ClubRecruitmentRepository;
import mju.sw.micro.domain.club.dao.ClubRepository;
import mju.sw.micro.domain.club.domain.Club;
import mju.sw.micro.domain.club.domain.ClubRecruitment;
import mju.sw.micro.domain.club.dto.request.ClubRecruitmentDeleteServiceRequest;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.error.exception.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubRecruitmentDeleteService {

	private final ClubRepository clubRepository;
	private final ClubRecruitmentRepository recruitmentRepository;

	@Transactional
	public ApiResponse<String> deleteClubRecruitment(ClubRecruitmentDeleteServiceRequest request) {
		Optional<ClubRecruitment> optionalRecruitment = recruitmentRepository.findById(request.recruitmentId());
		if (optionalRecruitment.isEmpty()) {
			return ApiResponse.withError(ErrorCode.INVALID_CLUB_RECRUITMENT_ID);
		}

		Optional<Club> optionalClub = clubRepository.findById(request.clubId());
		if (optionalClub.isEmpty()) {
			return ApiResponse.withError(ErrorCode.INVALID_CLUB_ID);
		}

		ClubRecruitment recruitment = optionalRecruitment.get();
		Club club = optionalClub.get();

		// TODO : checkClubPresident 메소드 호출
		club.clearRecruitments(recruitment);
		recruitmentRepository.deleteById(recruitment.getId());

		return ApiResponse.ok("학생 단체(동아리/학회) 공고 삭제에 성공했습니다.");
	}

}
