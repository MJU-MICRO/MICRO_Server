package mju.sw.micro.domain.user.domain;

import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "token")
public class Token implements Serializable {
	@Id
	private String id;
	@Indexed
	private String email;
	private String verificationCode;
	@TimeToLive
	private Long expiration;

	public static Token of(String email,String verificationCode,Long expiration) {
		Token token = new Token();
		token.email = email;
		token.verificationCode = verificationCode;
		token.expiration = expiration;
		return token;
	}
}