package mju.sw.micro.domain.user.dao;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import mju.sw.micro.domain.user.domain.EmailCode;

@EnableRedisRepositories
public interface EmailCodeRedisRepository extends CrudRepository<EmailCode, String> {
}
