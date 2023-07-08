package mju.sw.micro.domain.user.dao;

import java.util.Optional;
import mju.sw.micro.domain.user.domain.Token;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
@EnableRedisRepositories
public interface TokenRedisRepository extends CrudRepository<Token, String> {
	Optional<Token> findByEmail(String email);
}
