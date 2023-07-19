package mju.sw.micro.domain.user.domain;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;

@Getter
@RedisHash("refreshToken")
public class RefreshToken implements Serializable {
	@Id
	private String email;
	@Indexed
	private String refreshToken;
	@TimeToLive(unit = TimeUnit.MILLISECONDS)
	private Long expiration;
	private String expirationDate;

	public static RefreshToken of(String email, Long expiration, String refreshToken, String expirationDate) {
		RefreshToken token = new RefreshToken();
		token.email = email;
		token.refreshToken = refreshToken;
		token.expiration = expiration;
		token.expirationDate = expirationDate;
		return token;
	}
}
