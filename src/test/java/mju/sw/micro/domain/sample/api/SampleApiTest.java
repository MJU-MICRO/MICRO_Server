// package mju.sw.micro.domain.sample.api;
//
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.http.MediaType;
//
// import mju.sw.micro.ApiTestSupporter;
// import mju.sw.micro.domain.sample.dto.request.SampleCreateRequest;
//
// class SampleApiTest extends ApiTestSupporter {
//
// 	@DisplayName("신규 샘플을 등록한다.")
// 	@Test
// 	void createSample() throws Exception {
// 		//given
// 		SampleCreateRequest request = SampleCreateRequest.of("이름", "내용", 5000);
//
// 		// when // then
// 		mockMvc.perform(
// 				post("/api/sample")
// 					.content(objectMapper.writeValueAsString(request))
// 					.contentType(MediaType.APPLICATION_JSON)
// 			)
// 			.andDo(print())
// 			.andExpect(status().isOk());
// 	}
//
// 	@DisplayName("신규 샘플을 등록할 때 샘플 이름은 빈 값일 수 없다.")
// 	@Test
// 	void createSampleWithBlankTitle() throws Exception {
// 		//given
// 		SampleCreateRequest request = SampleCreateRequest.of("", "내용", 5000);
//
// 		// when // then
// 		mockMvc.perform(
// 				post("/api/sample")
// 					.content(objectMapper.writeValueAsString(request))
// 					.contentType(MediaType.APPLICATION_JSON)
// 			)
// 			.andDo(print())
// 			.andExpect(status().isBadRequest())
// 			.andExpect(jsonPath("$.message").value("Request Body를 통해 전달된 값이 유효하지 않습니다."))
// 			.andExpect(jsonPath("$.data").value(" [title] -> 샘플 이름은 필수 값입니다. 입력된 값: [] "));
// 	}
//
// 	@DisplayName("신규 샘플을 등록할 때 샘플 내용은 빈 값일 수 없다.")
// 	@Test
// 	void createSampleWithBlankContent() throws Exception {
// 		//given
// 		SampleCreateRequest request = SampleCreateRequest.of("제목", "", 5000);
//
// 		// when // then
// 		mockMvc.perform(
// 				post("/api/sample")
// 					.content(objectMapper.writeValueAsString(request))
// 					.contentType(MediaType.APPLICATION_JSON)
// 			)
// 			.andDo(print())
// 			.andExpect(status().isBadRequest())
// 			.andExpect(jsonPath("$.data").value(" [content] -> 샘플 내용은 필수 값입니다. 입력된 값: [] "))
// 			.andExpect(jsonPath("$.message").value("Request Body를 통해 전달된 값이 유효하지 않습니다."));
// 	}
//
// 	@DisplayName("신규 샘플을 등록할 때 샘플 가격은 양수여야 한다.")
// 	@Test
// 	void createSampleWithNegativePrice() throws Exception {
// 		//given
// 		SampleCreateRequest request = SampleCreateRequest.of("제목", "내용", 0);
//
// 		// when // then
// 		mockMvc.perform(
// 				post("/api/sample")
// 					.content(objectMapper.writeValueAsString(request))
// 					.contentType(MediaType.APPLICATION_JSON)
// 			)
// 			.andDo(print())
// 			.andExpect(jsonPath("$.data").value(" [price] -> 샘플 가격은 양수여야 합니다. 입력된 값: [0] "))
// 			.andExpect(jsonPath("$.message").value("Request Body를 통해 전달된 값이 유효하지 않습니다."));
// 	}
//
// }
