package mju.sw.micro.domain.user.dao;

import java.util.Optional;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import mju.sw.micro.domain.user.domain.Token;

@EnableRedisRepositories
public interface TokenRedisRepository extends CrudRepository<Token, String> {
	Optional<Token> findByEmail(String email);
}
