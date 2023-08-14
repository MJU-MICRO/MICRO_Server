package mju.sw.micro.domain.sample.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.sample.application.SampleService;

@RestController
@RequestMapping("/api/sample")
@RequiredArgsConstructor
public class SampleApi {

	private final SampleService sampleService;

	@GetMapping("/hello")
	public String hello() {
		return "Hello Micro Server!";
	}

	@GetMapping("/admin/hello")
	public String helloAdmin() {
		return "Hello Admin!";
	}

	@GetMapping("/president/hello")
	public String helloPresident() {
		return "Hello President!";
	}
}
