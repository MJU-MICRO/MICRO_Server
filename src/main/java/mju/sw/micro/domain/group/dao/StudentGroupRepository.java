package mju.sw.micro.domain.group.dao;

import mju.sw.micro.domain.group.domain.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {
	Optional<StudentGroup> findByPresidentId(Long userId);
}
