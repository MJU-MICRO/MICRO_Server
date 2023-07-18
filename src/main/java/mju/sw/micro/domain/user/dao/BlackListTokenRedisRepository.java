package mju.sw.micro.domain.user.dao;

import java.util.Optional;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import mju.sw.micro.domain.user.domain.BlackListAccessToken;

@EnableRedisRepositories
public interface BlackListTokenRedisRepository extends CrudRepository<BlackListAccessToken, String> {
	Optional<BlackListAccessToken> findByBlackListAccessToken(String blacklistAccessToken);
}
