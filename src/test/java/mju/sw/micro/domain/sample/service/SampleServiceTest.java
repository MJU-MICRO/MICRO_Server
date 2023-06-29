package mju.sw.micro.domain.sample.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mju.sw.micro.IntegrationTestSupporter;
import mju.sw.micro.domain.sample.dao.SampleRepository;
import mju.sw.micro.domain.sample.domain.Sample;
import mju.sw.micro.domain.sample.dto.request.SampleCreateServiceRequest;

class SampleServiceTest extends IntegrationTestSupporter {

	@Autowired
	private SampleService sampleService;

	@Autowired
	private SampleRepository sampleRepository;

	@AfterEach
	void tearDown() {
		sampleRepository.deleteAllInBatch();
	}

	@DisplayName("신규 샘플을 등록한다.")
	@Test
	void createSample() {
		//given
		SampleCreateServiceRequest request = SampleCreateServiceRequest.of("이름", "내용", 5000);

		// when
		Sample savedSample = sampleService.createSample(request);

		// then
		assertThat(savedSample).isNotNull();
		assertThat(savedSample).extracting("title", "content", "price")
			.contains("이름", "내용", 5000);
	}
}
