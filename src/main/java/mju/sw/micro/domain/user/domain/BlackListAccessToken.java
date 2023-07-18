package mju.sw.micro.domain.user.domain;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@RedisHash(value = "blacklistAccessToken")
public class BlackListAccessToken implements Serializable {
	@Id
	private String id;
	@Indexed
	private String blackListAccessToken;
	@TimeToLive(unit = TimeUnit.MILLISECONDS)
	private Long expiration;
	private String expirationDate;

	public static BlackListAccessToken of(Long expiration, String blackListAccessToken, String expirationDate) {
		BlackListAccessToken token = new BlackListAccessToken();
		token.blackListAccessToken = blackListAccessToken;
		token.expiration = expiration;
		token.expirationDate = expirationDate;
		return token;
	}
}
