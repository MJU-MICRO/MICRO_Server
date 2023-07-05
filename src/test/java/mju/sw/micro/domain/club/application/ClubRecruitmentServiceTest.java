package mju.sw.micro.domain.club.application;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mju.sw.micro.IntegrationTestSupporter;
import mju.sw.micro.domain.club.domain.Club;
import mju.sw.micro.domain.club.domain.ClubClassification;
import mju.sw.micro.domain.club.domain.ClubRecruitment;
import mju.sw.micro.domain.club.dto.request.ClubRecruitmentCreateServiceRequest;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.error.exception.ErrorCode;

class ClubRecruitmentServiceTest extends IntegrationTestSupporter {

	@AfterEach
	void tearDown() {
		recruitmentRepository.deleteAllInBatch();
		clubRepository.deleteAllInBatch();
	}

	@DisplayName("club 식별자와 제목, 내용을 받아 학생 단체 모집 공고를 생성한다.")
	@Test
	void createClubRecruitment() {
		//given
		Club savedClub = clubRepository.save(createClub());
		Long clubId = savedClub.getId();
		ClubRecruitmentCreateServiceRequest request = ClubRecruitmentCreateServiceRequest.of("공고 제목", "공고 내용", clubId);

		// when
		ApiResponse response = clubRecruitmentService.createClubRecruitment(request);
		List<ClubRecruitment> recruitmentList = recruitmentRepository.findAll();

		// then
		assertThat(recruitmentList).hasSize(1)
			.extracting("title", "content")
			.containsExactlyInAnyOrder(tuple("공고 제목", "공고 내용"));
		assertThat(recruitmentList.get(0).getClub().getId()).isEqualTo(clubId);
		assertThat(response.getMessage()).isEqualTo("학생 단체(동아리/학회) 공고 등록에 성공했습니다.");
	}

	@DisplayName("club 식별자가 유효하지 않은 경우, 학생 단체 모집 공고 생성에 실패한다.")
	@Test
	void createClubRecruitmentWithWrongClubId() {
		//given
		Long clubId = -1L;
		ClubRecruitmentCreateServiceRequest request = ClubRecruitmentCreateServiceRequest.of("공고 제목", "공고 내용", clubId);

		// when
		ApiResponse response = clubRecruitmentService.createClubRecruitment(request);
		List<ClubRecruitment> recruitmentList = recruitmentRepository.findAll();

		// then
		assertThat(recruitmentList).isEmpty();
		assertThat(response.getMessage()).isEqualTo(ErrorCode.INVALID_CLUB_ID.getMessage());
	}

	private Club createClub() {
		return Club.builder()
			.classification(ClubClassification.CIRCLE)
			.numberOfMember(10)
			.content("설명입니다.")
			.description("한 줄 소개입니다.")
			.establishedYear("2023")
			.interests("학술")
			.relatedMajors("응용 소프트웨어")
			.build();
	}

}
