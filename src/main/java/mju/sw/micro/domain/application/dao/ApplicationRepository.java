package mju.sw.micro.domain.application.dao;

import mju.sw.micro.domain.application.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
