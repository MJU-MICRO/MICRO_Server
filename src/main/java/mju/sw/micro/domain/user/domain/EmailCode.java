package mju.sw.micro.domain.user.domain;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@RedisHash(value = "emailCode")
public class EmailCode implements Serializable {
	@Id
	private String id;
	@Indexed
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
