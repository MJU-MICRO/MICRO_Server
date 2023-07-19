package mju.sw.micro.domain.user.domain;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.Getter;

@Getter
@RedisHash("emailCode")
public class EmailCode implements Serializable {
	@Id
	private String email;
	private String verificationCode;
	@TimeToLive(unit = TimeUnit.MILLISECONDS)
	private Long expiration;
	private String expirationDate;

	public static EmailCode of(String email, String verificationCode, Long expiration, String expirationDate) {
		EmailCode emailCode = new EmailCode();
		emailCode.email = email;
		emailCode.verificationCode = verificationCode;
		emailCode.expiration = expiration;
		emailCode.expirationDate = expirationDate;
		return emailCode;
	}
}
