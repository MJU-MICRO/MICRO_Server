package mju.sw.micro.domain.club.api;

import static mju.sw.micro.global.error.exception.ErrorCode.*;
import static org.mockito.Mockito.*;
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
import mju.sw.micro.global.error.exception.ErrorCode;

class ClubRecruitmentApiTest extends ApiTestSupporter {

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 등록한다.")
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
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value(expectedApiResponse.getMessage()));
	}

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 등록할 때, 요청으로 들어온 학생단체 식별자를 가지는 학생단체가 없을 경우 등록에 실패한다.")
	@Test
	void createClubRecruitmentWithWrongClubId() throws Exception {
		// Given
		ClubRecruitmentCreateRequest request = new ClubRecruitmentCreateRequest("title", "content", 1L);
		when(clubRecruitmentService.createClubRecruitment(any())).thenReturn(
			ApiResponse.withError(ErrorCode.INVALID_CLUB_ID));

		// when // then
		mockMvc.perform(
				post("/api/recruitment")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value(INVALID_CLUB_ID.getMessage()));
	}

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고을 등록할 때, 제목은 필수 값이다.")
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

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고을 등록할 때, 내용은 필수 값이다.")
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

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고을 등록할 때, 모집 공고를 게시하는 학생 단체의 식별자는 양수여야한다.")
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

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를  수정할 때, 요청으로 들어온 학생단체 식별자를 가지는 학생단체가 없을 경우 수정에 실패한다.")
	@Test
	void updateClubRecruitmentWithWrongClubId() throws Exception {
		// Given
		ClubRecruitmentUpdateRequest request = new ClubRecruitmentUpdateRequest("title", "content", 1L, 1L);
		when(clubRecruitmentService.updateClubRecruitment(any())).thenReturn(
			ApiResponse.withError(ErrorCode.INVALID_CLUB_ID));

		// when // then
		mockMvc.perform(
				patch("/api/recruitment")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value(INVALID_CLUB_ID.getMessage()));
	}

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 수정할 때, 요청으로 들어온 모집 공고 식별자를 가지는 모집 공고가 없을 경우 등록에 실패한다.")
	@Test
	void updateClubRecruitmentWithWrongClubRecruitmentId() throws Exception {
		// Given
		ClubRecruitmentUpdateRequest request = new ClubRecruitmentUpdateRequest("title", "content", 1L, 1L);
		when(clubRecruitmentService.updateClubRecruitment(any())).thenReturn(
			ApiResponse.withError(ErrorCode.INVALID_CLUB_RECRUITMENT_ID));

		// when // then
		mockMvc.perform(
				patch("/api/recruitment")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value(INVALID_CLUB_RECRUITMENT_ID.getMessage()));
	}
}