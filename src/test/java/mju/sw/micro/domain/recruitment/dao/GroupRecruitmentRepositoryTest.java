// package mju.sw.micro.domain.recruitment.dao;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.time.LocalDateTime;
// import java.util.List;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
//
// import mju.sw.micro.RepositoryTestSupporter;
// import mju.sw.micro.domain.recruitment.domain.ActivityPeriod;
// import mju.sw.micro.domain.recruitment.domain.GroupRecruitment;
// import mju.sw.micro.domain.recruitment.domain.RecruitmentImage;
//
// class GroupRecruitmentRepositoryTest extends RepositoryTestSupporter {
//
// 	@DisplayName("모집 공고를 생성한다.")
// 	@Test
// 	void saveGroupRecruitment() {
// 		//given
// 		GroupRecruitment savedRecruitment = recruitmentRepository.save(createRecruitment());
//
// 		// when
// 		List<GroupRecruitment> recruitmentList = recruitmentRepository.findAll();
//
// 		// then
// 		assertThat(recruitmentList).hasSize(1);
// 		assertThat(recruitmentList.get(0).getRecruitmentContent())
// 			.extracting("title", "content", "fields")
// 			.containsExactlyInAnyOrder("공고 제목", "일주일에 한번씩 당근 먹어요", "체육/헬스, IT/공학");
//
// 		assertThat(recruitmentList.get(0).getRecruitmentPeriod())
// 			.extracting("startDateTime", "endDateTime")
// 			.containsExactlyInAnyOrder(LocalDateTime.of(2023, 8, 6, 11, 30), LocalDateTime.of(2023, 9, 21, 18, 00));
// 	}
//
// 	private GroupRecruitment createRecruitment() {
// 		List<RecruitmentImage> imageList = List.of(new RecruitmentImage("https://www.naver.com/", "이전 기수 OT 사진입니다."),
// 			new RecruitmentImage("https://www.naver.com/", "이전 기수 종강 총회 사진입니다."));
//
// 		RecruitmentContent recruitmentContent = RecruitmentContent.builder()
// 			.title("공고 제목")
// 			.description("신규 회원 모집합니다.")
// 			.content("일주일에 한번씩 당근 먹어요")
// 			.fields("체육/헬스, IT/공학")
// 			.activityPeriod(ActivityPeriod.SEMESTER)
// 			.build();
//
// 		RecruitmentPeriod recruitmentPeriod = RecruitmentPeriod.builder()
// 			.startDateTime(LocalDateTime.of(2023, 8, 6, 11, 30))
// 			.endDateTime(LocalDateTime.of(2023, 9, 21, 18, 00))
// 			.build();
//
// 		return GroupRecruitment.builder()
// 			.recruitmentContent(recruitmentContent)
// 			.recruitmentPeriod(recruitmentPeriod)
// 			.imageList(imageList)
// 			.build();
// 	}
//
// }
