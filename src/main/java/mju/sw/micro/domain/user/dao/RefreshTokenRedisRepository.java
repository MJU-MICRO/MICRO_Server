package mju.sw.micro.domain.user.dao;

import java.util.Optional;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import mju.sw.micro.domain.user.domain.RefreshToken;

@EnableRedisRepositories
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
	Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
