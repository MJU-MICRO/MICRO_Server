// package mju.sw.micro.domain.recruitment.api;
//
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.http.MediaType;
//
// import mju.sw.micro.ApiTestSupporter;
// import mju.sw.micro.domain.recruitment.dto.request.ClubRecruitmentDeleteRequest;
// import mju.sw.micro.domain.recruitment.dto.request.ClubRecruitmentDeleteServiceRequest;
// import mju.sw.micro.global.common.response.ApiResponse;
//
// class GroupRecruitmentDeleteApiTest extends ApiTestSupporter {
//
// 	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 삭제한다.")
// 	@Test
// 	void deleteClubRecruitment() throws Exception {
// 		// Given
// 		ClubRecruitmentDeleteServiceRequest request = new ClubRecruitmentDeleteServiceRequest(1L, 1L);
// 		ApiResponse<String> expectedApiResponse = ApiResponse.ok("학생 단체(동아리/학회) 공고 삭제에 성공했습니다.");
//
// 		// when
// 		when(clubRecruitmentDeleteService.deleteClubRecruitment(any())).thenReturn(expectedApiResponse);
//
// 		// then
// 		mockMvc.perform(
// 				delete("/api/recruitment")
// 					.content(objectMapper.writeValueAsString(request))
// 					.contentType(MediaType.APPLICATION_JSON)
// 			)
// 			.andDo(print())
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$.message").value(expectedApiResponse.getMessage()));
// 	}
//
// 	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 삭제할 때, 요청으로 들어온 학생단체 식별자는 양수여야 한다.")
// 	@Test
// 	void deleteClubRecruitmentWithNegativeId() throws Exception {
// 		// Given
// 		ClubRecruitmentDeleteRequest request = new ClubRecruitmentDeleteRequest(0L, 1L);
//
// 		// when // then
// 		mockMvc.perform(
// 				delete("/api/recruitment")
// 					.content(objectMapper.writeValueAsString(request))
// 					.contentType(MediaType.APPLICATION_JSON)
// 			)
// 			.andDo(print())
// 			.andExpect(status().isBadRequest())
// 			.andExpect(jsonPath("$.message").value("Request Body를 통해 전달된 값이 유효하지 않습니다."))
// 			.andExpect(jsonPath("$.data").value(" [clubId] -> 모집 공고를 게시하는 학생 단체의 식별자는 양수여야 합니다. 입력된 값: [0] "));
// 	}
//
// 	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 삭제할 때, 모집 공고의 식별자는 양수여야한다.")
// 	@Test
// 	void deleteClubRecruitmentWithNegativeClubId() throws Exception {
// 		// Given
// 		ClubRecruitmentDeleteRequest request = new ClubRecruitmentDeleteRequest(1L, 0L);
//
// 		// when // then
// 		mockMvc.perform(
// 				delete("/api/recruitment")
// 					.content(objectMapper.writeValueAsString(request))
// 					.contentType(MediaType.APPLICATION_JSON)
// 			)
// 			.andDo(print())
// 			.andExpect(status().isBadRequest())
// 			.andExpect(jsonPath("$.message").value("Request Body를 통해 전달된 값이 유효하지 않습니다."))
// 			.andExpect(jsonPath("$.data").value(" [recruitmentId] -> 모집 공고의 식별자는 양수여야 합니다. 입력된 값: [0] "));
// 	}
//
// }
