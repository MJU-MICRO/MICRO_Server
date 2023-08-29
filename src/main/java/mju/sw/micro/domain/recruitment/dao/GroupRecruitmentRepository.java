package mju.sw.micro.domain.recruitment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mju.sw.micro.domain.recruitment.domain.GroupRecruitment;

public interface GroupRecruitmentRepository extends JpaRepository<GroupRecruitment, Long> {
	List<GroupRecruitment> findByGroupId(Long groupId);

	List<GroupRecruitment> findAllByOrderByCreatedDateTimeDesc();
}
