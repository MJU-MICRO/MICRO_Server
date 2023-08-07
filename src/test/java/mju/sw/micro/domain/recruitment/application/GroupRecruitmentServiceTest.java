// package mju.sw.micro.domain.recruitment.application;
//
// import static mju.sw.micro.global.error.exception.ErrorCode.*;
// import static org.assertj.core.api.Assertions.*;
//
// import java.util.List;
//
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.security.test.context.support.WithMockUser;
//
// import mju.sw.micro.IntegrationTestSupporter;
// import mju.sw.micro.domain.recruitment.domain.GroupRecruitment;
// import mju.sw.micro.domain.recruitment.dto.request.ClubRecruitmentCreateServiceRequest;
// import mju.sw.micro.domain.recruitment.dto.request.ClubRecruitmentUpdateServiceRequest;
// import mju.sw.micro.global.common.response.ApiResponse;
//
// class GroupRecruitmentServiceTest extends IntegrationTestSupporter {
//
// 	@AfterEach
// 	void tearDown() {
// 		recruitmentRepository.deleteAllInBatch();
// 		clubRepository.deleteAllInBatch();
// 	}
//
// 	@DisplayName("club 식별자와 제목, 내용을 받아 학생 단체 모집 공고를 생성한다.")
// 	@Test
// 	@WithMockUser(roles = {"PRESIDENT"})
// 	void createClubRecruitment() {
// 		//given
// 		Club savedClub = clubRepository.save(createClub());
// 		Long clubId = savedClub.getId();
// 		ClubRecruitmentCreateServiceRequest request = new ClubRecruitmentCreateServiceRequest("공고 제목", "공고 내용", clubId);
//
// 		// when
// 		ApiResponse<String> response = clubRecruitmentService.createClubRecruitment("president@gmail.com", request);
// 		List<GroupRecruitment> recruitmentList = recruitmentRepository.findAll();
//
// 		// then
// 		assertThat(recruitmentList).hasSize(1)
// 			.extracting("title", "content")
// 			.containsExactlyInAnyOrder(tuple("공고 제목", "공고 내용"));
// 		assertThat(recruitmentList.get(0).getClub().getId()).isEqualTo(clubId);
// 		assertThat(response.getMessage()).isEqualTo("학생 단체(동아리/학회) 공고 등록에 성공했습니다.");
// 	}
//
// 	@DisplayName("동아리 회장이 아닌 경우, 해당 동아리의 모집 공고를 등록하는데 실패한다.")
// 	@Test
// 	@WithMockUser(roles = {"PRESIDENT"})
// 	void createClubRecruitmentWithUnMatchPresidentEmail() {
// 		//given
// 		Club savedClub = clubRepository.save(createClub());
// 		Long clubId = savedClub.getId();
// 		ClubRecruitmentCreateServiceRequest request = new ClubRecruitmentCreateServiceRequest("공고 제목", "공고 내용", clubId);
//
// 		// when
// 		ApiResponse<String> response = clubRecruitmentService.createClubRecruitment("unmatch@gmail.com", request);
// 		List<GroupRecruitment> recruitmentList = recruitmentRepository.findAll();
//
// 		// then
// 		assertThat(recruitmentList).isEmpty();
// 		assertThat(response.getMessage()).isEqualTo(UNMATCH_PRESIDENT_EMAIL.getMessage());
// 	}
//
// 	@DisplayName("club 식별자가 유효하지 않은 경우, 학생 단체 모집 공고 생성에 실패한다.")
// 	@Test
// 	void createClubRecruitmentWithWrongClubId() {
// 		//given
// 		Long clubId = -1L;
// 		ClubRecruitmentCreateServiceRequest request = new ClubRecruitmentCreateServiceRequest("공고 제목", "공고 내용", clubId);
//
// 		// when
// 		ApiResponse<String> response = clubRecruitmentService.createClubRecruitment("president@gmail.com", request);
// 		List<GroupRecruitment> recruitmentList = recruitmentRepository.findAll();
//
// 		// then
// 		assertThat(recruitmentList).isEmpty();
// 		assertThat(response.getMessage()).isEqualTo(INVALID_CLUB_ID.getMessage());
// 	}
//
// 	@DisplayName("club 식별자와 모집 공고 식별자, 제목, 내용을 받아 학생 단체 모집 공고를 수정한다.")
// 	@Test
// 	void updateClubRecruitment() {
// 		//given
// 		Club savedClub = clubRepository.save(createClub());
// 		Long clubId = savedClub.getId();
//
// 		ClubRecruitmentCreateServiceRequest createRequest = new ClubRecruitmentCreateServiceRequest("공고 제목", "공고 내용",
// 			clubId);
// 		GroupRecruitment recruitment = createRequest.toEntity();
// 		savedClub.addRecruitment(recruitment);
// 		GroupRecruitment savedRecruitment = recruitmentRepository.save(recruitment);
//
// 		ClubRecruitmentUpdateServiceRequest updateRequest = new ClubRecruitmentUpdateServiceRequest("수정된 공고 제목",
// 			"수정된 공고 내용",
// 			clubId, savedRecruitment.getId());
//
// 		// when
// 		ApiResponse<String> response = clubRecruitmentService.updateClubRecruitment(updateRequest);
// 		List<GroupRecruitment> recruitmentList = recruitmentRepository.findAll();
//
// 		// then
// 		assertThat(recruitmentList).hasSize(1)
// 			.extracting("title", "content")
// 			.containsExactlyInAnyOrder(tuple("수정된 공고 제목", "수정된 공고 내용"));
// 		assertThat(response.getMessage()).isEqualTo("학생 단체(동아리/학회) 공고 수정에 성공했습니다.");
// 	}
//
// 	@DisplayName("모집 공고 식별자가 유효하지 않은 경우, 학생 단체 모집 공고 수정에 실패한다.")
// 	@Test
// 	void updateClubRecruitmentWithWrongClubRecruitmentId() {
// 		//given
// 		Club savedClub = clubRepository.save(createClub());
// 		Long clubId = savedClub.getId();
//
// 		ClubRecruitmentCreateServiceRequest createRequest = new ClubRecruitmentCreateServiceRequest("공고 제목", "공고 내용",
// 			clubId);
// 		GroupRecruitment recruitment = createRequest.toEntity();
// 		savedClub.addRecruitment(recruitment);
// 		recruitmentRepository.save(recruitment);
//
// 		Long invalidRecruitmentId = 0L;
// 		ClubRecruitmentUpdateServiceRequest updateRequest = new ClubRecruitmentUpdateServiceRequest("수정된 공고 제목",
// 			"수정된 공고 내용",
// 			clubId, invalidRecruitmentId);
//
// 		// when
// 		ApiResponse<String> response = clubRecruitmentService.updateClubRecruitment(updateRequest);
// 		List<GroupRecruitment> recruitmentList = recruitmentRepository.findAll();
//
// 		// then
// 		assertThat(recruitmentList).hasSize(1)
// 			.extracting("title", "content")
// 			.containsExactlyInAnyOrder(tuple("공고 제목", "공고 내용"));
// 		assertThat(response.getMessage()).isEqualTo(INVALID_CLUB_RECRUITMENT_ID.getMessage());
// 	}
//
// 	@DisplayName("club 식별자가 유효하지 않은 경우, 학생 단체 모집 공고 수정에 실패한다.")
// 	@Test
// 	void updateClubRecruitmentWithWrongClubId() {
// 		//given
// 		Club savedClub = clubRepository.save(createClub());
// 		Long clubId = savedClub.getId();
//
// 		ClubRecruitmentCreateServiceRequest createRequest = new ClubRecruitmentCreateServiceRequest("공고 제목", "공고 내용",
// 			clubId);
// 		GroupRecruitment recruitment = createRequest.toEntity();
// 		savedClub.addRecruitment(recruitment);
// 		GroupRecruitment savedRecruitment = recruitmentRepository.save(recruitment);
//
// 		Long invalidClubId = 0L;
// 		ClubRecruitmentUpdateServiceRequest updateRequest = new ClubRecruitmentUpdateServiceRequest("수정된 공고 제목",
// 			"수정된 공고 내용",
// 			invalidClubId, savedRecruitment.getId());
//
// 		// when
// 		ApiResponse<String> response = clubRecruitmentService.updateClubRecruitment(updateRequest);
// 		List<GroupRecruitment> recruitmentList = recruitmentRepository.findAll();
//
// 		// then
// 		assertThat(recruitmentList).hasSize(1)
// 			.extracting("title", "content")
// 			.containsExactlyInAnyOrder(tuple("공고 제목", "공고 내용"));
// 		assertThat(response.getMessage()).isEqualTo(INVALID_CLUB_ID.getMessage());
// 	}
//
// 	private Club createClub() {
// 		return Club.builder()
// 			.classification(ClubClassification.CIRCLE)
// 			.numberOfMember(10)
// 			.content("설명입니다.")
// 			.description("한 줄 소개입니다.")
// 			.establishedYear("2023")
// 			.interests("학술")
// 			.relatedMajors("응용 소프트웨어")
// 			.presidentEmail("president@gmail.com")
// 			.build();
// 	}
//
// }
