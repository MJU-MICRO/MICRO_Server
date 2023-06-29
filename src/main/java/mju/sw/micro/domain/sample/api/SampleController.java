package mju.sw.micro.domain.sample.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.sample.application.SampleService;
import mju.sw.micro.domain.sample.domain.Sample;
import mju.sw.micro.domain.sample.dto.request.SampleCreateRequest;
import mju.sw.micro.global.common.response.ApiResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SampleController {

	private final SampleService sampleService;

	@PostMapping("/sample")
	public ApiResponse createSample(@Valid @RequestBody SampleCreateRequest request) {
		Sample savedSample = sampleService.createSample(request.toServiceRequest());
		return ApiResponse.ok(savedSample);
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello Micro Server!";
	}

}
