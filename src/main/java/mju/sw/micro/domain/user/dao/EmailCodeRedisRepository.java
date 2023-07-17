package mju.sw.micro.domain.user.dao;

import java.util.Optional;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import mju.sw.micro.domain.user.domain.EmailCode;

@EnableRedisRepositories
public interface EmailCodeRedisRepository extends CrudRepository<EmailCode, String> {
	Optional<EmailCode> findByEmail(String email);
}
