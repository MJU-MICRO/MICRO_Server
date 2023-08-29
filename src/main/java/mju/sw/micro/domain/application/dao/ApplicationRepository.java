package mju.sw.micro.domain.application.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mju.sw.micro.domain.application.domain.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	List<Application> findByUserId(Long userId);

	List<Application> findByRecruitmentId(Long recruitmentId);

	Optional<Application> findByUserIdAndRecruitmentId(Long userId, Long recruitmentId);
}
