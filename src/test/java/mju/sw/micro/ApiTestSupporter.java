package mju.sw.micro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import mju.sw.micro.domain.club.api.ClubRecruitmentApi;
import mju.sw.micro.domain.club.application.ClubRecruitmentService;
import mju.sw.micro.domain.sample.api.SampleApi;
import mju.sw.micro.domain.sample.application.SampleService;

@WebMvcTest(controllers = {
	SampleApi.class,
	ClubRecruitmentApi.class
})
@ActiveProfiles("test")
public abstract class ApiTestSupporter {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected SampleService sampleService;

	@MockBean
	protected ClubRecruitmentService clubRecruitmentService;
}
