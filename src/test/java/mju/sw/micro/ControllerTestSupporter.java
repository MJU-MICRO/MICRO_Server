package mju.sw.micro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import mju.sw.micro.domain.sample.api.SampleController;
import mju.sw.micro.domain.sample.application.SampleService;

@WebMvcTest(controllers = {SampleController.class
})
@ActiveProfiles("test")
public abstract class ControllerTestSupporter {

	@MockBean
	protected SampleService sampleService;

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

}
