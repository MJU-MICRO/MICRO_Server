package mju.sw.micro.domain.club.api;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import mju.sw.micro.ApiTestSupporter;
import mju.sw.micro.domain.club.dto.request.ClubRecruitmentCreateRequest;
import mju.sw.micro.domain.club.dto.request.ClubRecruitmentCreateServiceRequest;
import mju.sw.micro.domain.club.dto.request.ClubRecruitmentUpdateRequest;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.security.WithMockCustomUser;

class ClubRecruitmentApiTest extends ApiTestSupporter {

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 등록한다.")
	@WithMockCustomUser
	@Test
	void createClubRecruitment() throws Exception {
		// Given
		ClubRecruitmentCreateServiceRequest request = new ClubRecruitmentCreateServiceRequest("title", "content", 1L);
		ApiResponse<String> expectedApiResponse = ApiResponse.ok("학생 단체(동아리/학회) 공고 등록에 성공했습니다.");

		// when
		when(clubRecruitmentService.createClubRecruitment(any())).thenReturn(expectedApiResponse);

		// then
		mockMvc.perform(
				post("/api/recruitment")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
					.with(csrf())
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value(expectedApiResponse.getMessage()));
	}

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고을 등록할 때, 제목은 필수 값이다.")
	@WithMockCustomUser
	@Test
	void createClubRecruitmentWithEmptyTitle() throws Exception {
		// Given
		ClubRecruitmentCreateRequest request = new ClubRecruitmentCreateRequest("", "content", 1L);

		//when // then
		mockMvc.perform(
				post("/api/recruitment")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("Request Body를 통해 전달된 값이 유효하지 않습니다."))
			.andExpect(jsonPath("$.data").value(" [title] -> 모집 공고의 제목은 필수 값입니다. 입력된 값: [] "));
	}

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 등록할 때, 내용은 필수 값이다.")
	@WithMockCustomUser
	@Test
	void createClubRecruitmentWithEmptyContent() throws Exception {
		// Given
		ClubRecruitmentCreateRequest request = new ClubRecruitmentCreateRequest("title", "", 1L);

		// when // then
		mockMvc.perform(
				post("/api/recruitment")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("Request Body를 통해 전달된 값이 유효하지 않습니다."))
			.andExpect(jsonPath("$.data").value(" [content] -> 모집 공고의 내용은 필수 값입니다. 입력된 값: [] "));
	}

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 등록할 때, 모집 공고를 게시하는 학생 단체의 식별자는 양수여야한다.")
	@WithMockCustomUser
	@Test
	void createClubRecruitmentWithNegativeClubId() throws Exception {
		// Given
		ClubRecruitmentCreateRequest request = new ClubRecruitmentCreateRequest("title", "content", 0L);

		// when // then
		mockMvc.perform(
				post("/api/recruitment")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("Request Body를 통해 전달된 값이 유효하지 않습니다."))
			.andExpect(jsonPath("$.data").value(" [clubId] -> 모집 공고를 게시하는 학생 단체의 식별자는 양수여야 합니다. 입력된 값: [0] "));
	}

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 수정한다.")
	@WithMockCustomUser
	@Test
	void updateClubRecruitment() throws Exception {
		// Given
		ClubRecruitmentUpdateRequest request = new ClubRecruitmentUpdateRequest("title", "content", 1L,
			1L);
		ApiResponse<String> expectedApiResponse = ApiResponse.ok("학생 단체(동아리/학회) 공고 수정에 성공했습니다.");

		// when
		when(clubRecruitmentService.updateClubRecruitment(any())).thenReturn(expectedApiResponse);

		// then
		mockMvc.perform(
				patch("/api/recruitment")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value(expectedApiResponse.getMessage()));
	}

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 수정할 때, 제목은 필수 값이다.")
	@WithMockCustomUser
	@Test
	void updateClubRecruitmentWithEmptyTitle() throws Exception {
		// Given
		ClubRecruitmentUpdateRequest request = new ClubRecruitmentUpdateRequest("", "content", 1L, 1L);

		//when // then
		mockMvc.perform(
				patch("/api/recruitment")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("Request Body를 통해 전달된 값이 유효하지 않습니다."))
			.andExpect(jsonPath("$.data").value(" [title] -> 모집 공고의 제목은 필수 값입니다. 입력된 값: [] "));
	}

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고을 수정할 때, 내용은 필수 값이다.")
	@WithMockCustomUser
	@Test
	void updateClubRecruitmentWithEmptyContent() throws Exception {
		// Given
		ClubRecruitmentUpdateRequest request = new ClubRecruitmentUpdateRequest("title", "", 1L, 1L);

		// when // then
		mockMvc.perform(
				patch("/api/recruitment")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("Request Body를 통해 전달된 값이 유효하지 않습니다."))
			.andExpect(jsonPath("$.data").value(" [content] -> 모집 공고의 내용은 필수 값입니다. 입력된 값: [] "));
	}

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 수정할 때, 모집 공고를 게시하는 학생 단체의 식별자는 양수여야한다.")
	@WithMockCustomUser
	@Test
	void updateClubRecruitmentWithNegativeClubId() throws Exception {
		// Given
		ClubRecruitmentUpdateRequest request = new ClubRecruitmentUpdateRequest("title", "content", 0L, 1L);

		// when // then
		mockMvc.perform(
				patch("/api/recruitment")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("Request Body를 통해 전달된 값이 유효하지 않습니다."))
			.andExpect(jsonPath("$.data").value(" [clubId] -> 모집 공고를 게시하는 학생 단체의 식별자는 양수여야 합니다. 입력된 값: [0] "));
	}

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 수정할 때, 모집 공고의 식별자는 양수여야한다.")
	@WithMockCustomUser
	@Test
	void deleteClubRecruitmentWithNegativeClubId() throws Exception {
		// Given
		ClubRecruitmentUpdateRequest request = new ClubRecruitmentUpdateRequest("title", "content", 1L, 0L);

		// when // then
		mockMvc.perform(
				patch("/api/recruitment")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("Request Body를 통해 전달된 값이 유효하지 않습니다."))
			.andExpect(jsonPath("$.data").value(" [recruitmentId] -> 모집 공고의 식별자는 양수여야 합니다. 입력된 값: [0] "));
	}

}
