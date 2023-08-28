package mju.sw.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MicroServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServerApplication.class, args);
	}

}
