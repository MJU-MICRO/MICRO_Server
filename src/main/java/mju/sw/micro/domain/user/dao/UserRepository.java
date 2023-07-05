package mju.sw.micro.domain.user.dao;

import mju.sw.micro.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
