package mju.sw.micro.domain.club.dao;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mju.sw.micro.RepositoryTestSupporter;
import mju.sw.micro.domain.club.domain.Club;
import mju.sw.micro.domain.club.domain.ClubClassification;
import mju.sw.micro.domain.club.domain.ClubRecruitment;

class ClubRecruitmentRepositoryTest extends RepositoryTestSupporter {

	@DisplayName("club 엔티티를 삭제할 경우, club과 연관 관계를 맺고 있는 ClubRecruitment도 함께 삭제한다.")
	@Test
	void test() {
		//given
		Club club = clubRepository.save(createClub());
		ClubRecruitment recruitment = recruitmentRepository.save(createRecruitment());

		club.addRecruitment(recruitment);
		clubRepository.flush();

		// when
		club.clearRecruitments();
		clubRepository.delete(club);

		// then
		assertThat(club.getRecruitmentList()).isEmpty();
		assertThat(clubRepository.findAll()).isEmpty();
		assertThat(recruitmentRepository.findAll()).isEmpty();
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

	private ClubRecruitment createRecruitment() {
		return ClubRecruitment.builder()
			.title("제목")
			.content("내용")
			.build();
	}
}
