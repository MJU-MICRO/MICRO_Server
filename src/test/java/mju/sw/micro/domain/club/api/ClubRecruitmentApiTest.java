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
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.error.exception.ErrorCode;

class ClubRecruitmentApiTest extends ApiTestSupporter {

	@DisplayName("신규 학생단체(동아리/학회) 모집 공고를 등록한다.")
	@Test
	void clubRecruitmentApi() throws Exception {
		// Given
		ClubRecruitmentCreateServiceRequest request = ClubRecruitmentCreateServiceRequest.of("title", "content", 1L);
		ApiResponse expectedApiResponse = ApiResponse.ok("학생 단체(동아리/학회) 공고 등록에 성공했습니다.");

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
	void clubRecruitmentApiWithWrongClubId() throws Exception {
		// Given
		ClubRecruitmentCreateRequest request = ClubRecruitmentCreateRequest.of("title", "content", 1L);
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
	void clubRecruitmentApiWithEmptyTitle() throws Exception {
		// Given
		ClubRecruitmentCreateRequest request = ClubRecruitmentCreateRequest.of("", "content", 1L);

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
	void clubRecruitmentApiWithEmptyContent() throws Exception {
		// Given
		ClubRecruitmentCreateRequest request = ClubRecruitmentCreateRequest.of("title", "", 1L);

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
	void clubRecruitmentApiWithNegativeClubId() throws Exception {
		// Given
		ClubRecruitmentCreateRequest request = ClubRecruitmentCreateRequest.of("title", "content", 0L);

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
}
