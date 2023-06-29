package mju.sw.micro.domain.sample;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
@ActiveProfiles("prod")
public class ConfigEnvironmentTest {

	@Value("${spring.config.activate.on-profile}")
	String profile;

	@DisplayName("profile을 prod로 실행했을 때 application-prod.yml이 불러와지는지 확인한다.")
	@Test
	void test() {
		assertThat(profile).isEqualTo("prod");
	}
}
