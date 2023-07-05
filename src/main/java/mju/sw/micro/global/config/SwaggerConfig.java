package mju.sw.micro.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openApi() {
		Info info = new Info()
			.version("v1.0.0")
			.title("MICRO API")
			.description("교내 동아리 / 학회 / 학생회 신청 및 통합 관리 서비스 API 명세서");

		return new OpenAPI()
			.info(info);
	}
}
