package mju.sw.micro.domain.group.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mju.sw.micro.domain.group.domain.StudentGroup;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {
	List<StudentGroup> findAllByPresidentId(Long userId);

	List<StudentGroup> findByIsRecruitTrue();
}
