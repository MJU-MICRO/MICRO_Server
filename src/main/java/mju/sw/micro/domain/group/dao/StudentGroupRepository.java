package mju.sw.micro.domain.group.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mju.sw.micro.domain.group.domain.StudentGroup;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {
	Optional<StudentGroup> findByPresidentId(Long userId);

	List<StudentGroup> findByIsRecruitTrue();
}
