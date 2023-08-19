package mju.sw.micro.domain.application.dao;

import mju.sw.micro.domain.application.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	boolean existsByUserIdAndRecruitmentId(Long userId, Long recruitmentId);

	List<Application> findByUserId(Long userId);

	List<Application> findByRecruitmentId(Long recruitmentId);
}
