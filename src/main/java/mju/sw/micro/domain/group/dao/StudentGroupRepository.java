package mju.sw.micro.domain.group.dao;

import mju.sw.micro.domain.group.domain.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {
}
