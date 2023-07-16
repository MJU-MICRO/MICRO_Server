package mju.sw.micro.domain.user.domain;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@RedisHash(value = "token")
public class Token implements Serializable {
	@Id
	private String id;
	@Indexed
	private String email;
	private String verificationCode;
	@Indexed
	private String refreshToken;
	@TimeToLive(unit = TimeUnit.MILLISECONDS)
	private Long expiration;
	private String expirationDate;

	public static Token of(String email, String verificationCode, Long expiration, String expirationDate) {
		Token token = new Token();
		token.email = email;
		token.verificationCode = verificationCode;
		token.expiration = expiration;
		token.expirationDate = expirationDate;
		return token;
	}

	public static Token of(String email, Long expiration, String refreshToken, String expirationDate) {
		Token token = new Token();
		token.email = email;
		token.refreshToken = refreshToken;
		token.expiration = expiration;
		token.expirationDate = expirationDate;
		return token;
	}
}
