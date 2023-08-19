// package mju.sw.micro.domain.recruitment.application;
//
// import static mju.sw.micro.global.error.exception.ErrorCode.*;
// import static org.assertj.core.api.Assertions.*;
//
// import java.util.List;
// import java.util.Optional;
//
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
//
// import mju.sw.micro.IntegrationTestSupporter;
// import mju.sw.micro.domain.recruitment.domain.GroupRecruitment;
// import mju.sw.micro.domain.recruitment.dto.request.ClubRecruitmentCreateServiceRequest;
// import mju.sw.micro.domain.recruitment.dto.request.ClubRecruitmentDeleteServiceRequest;
// import mju.sw.micro.global.common.response.ApiResponse;
//
// class GroupRecruitmentDeleteServiceTest extends IntegrationTestSupporter {
//
// 	@AfterEach
// 	void tearDown() {
// 		recruitmentRepository.deleteAllInBatch();
// 		clubRepository.deleteAllInBatch();
// 	}
//
// 	@DisplayName("club 식별자와 모집 공고 식별자를 받아 학생 단체 모집 공고를 삭제한다.")
// 	@Test
// 	void deleteClubRecruitment() {
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
// 		ClubRecruitmentDeleteServiceRequest deleteServiceRequest = new ClubRecruitmentDeleteServiceRequest(clubId,
// 			savedRecruitment.getId());
//
// 		// when
// 		ApiResponse<String> response = clubRecruitmentdeleteService.deleteClubRecruitment(deleteServiceRequest);
// 		Optional<GroupRecruitment> optionalRecruitment = recruitmentRepository.findById(savedRecruitment.getId());
// 		Optional<Club> optionalClub = clubRepository.findById(savedClub.getId());
//
// 		// then
// 		assertThat(optionalRecruitment).isEmpty();
// 		assertThat(optionalClub.get().getRecruitmentList()).isEmpty();
// 		assertThat(response.getMessage()).isEqualTo("학생 단체(동아리/학회) 공고 삭제에 성공했습니다.");
// 	}
//
// 	@DisplayName("모집 공고 식별자가 유효하지 않은 경우, 학생 단체 모집 공고 삭제에 실패한다.")
// 	@Test
// 	void deleteClubRecruitmentWithWrongClubRecruitmentId() {
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
// 		Long invalidRecruitmentId = 0L;
// 		ClubRecruitmentDeleteServiceRequest deleteServiceRequest = new ClubRecruitmentDeleteServiceRequest(clubId,
// 			invalidRecruitmentId);
//
// 		// when
// 		ApiResponse<String> response = clubRecruitmentdeleteService.deleteClubRecruitment(deleteServiceRequest);
// 		List<GroupRecruitment> recruitmentList = recruitmentRepository.findAll();
// 		Optional<Club> optionalClub = clubRepository.findById(savedClub.getId());
//
// 		// then
// 		assertThat(recruitmentList).hasSize(1)
// 			.extracting("title", "content")
// 			.containsExactlyInAnyOrder(tuple("공고 제목", "공고 내용"));
// 		assertThat(
// 			optionalClub.get()
// 				.getRecruitmentList()
// 				.stream()
// 				.anyMatch(r -> r.getId().equals(savedRecruitment.getId()))).isTrue();
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
// 		ClubRecruitmentDeleteServiceRequest deleteServiceRequest = new ClubRecruitmentDeleteServiceRequest(
// 			invalidClubId,
// 			savedRecruitment.getId());
//
// 		// when
// 		ApiResponse<String> response = clubRecruitmentdeleteService.deleteClubRecruitment(deleteServiceRequest);
// 		List<GroupRecruitment> recruitmentList = recruitmentRepository.findAll();
// 		Optional<Club> optionalClub = clubRepository.findById(savedClub.getId());
//
// 		// then
// 		assertThat(recruitmentList).hasSize(1)
// 			.extracting("title", "content")
// 			.containsExactlyInAnyOrder(tuple("공고 제목", "공고 내용"));
// 		assertThat(
// 			optionalClub.get()
// 				.getRecruitmentList()
// 				.stream()
// 				.anyMatch(r -> r.getId().equals(savedRecruitment.getId()))).isTrue();
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
// 			.build();
// 	}
// }
