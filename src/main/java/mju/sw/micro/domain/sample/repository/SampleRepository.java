package mju.sw.micro.domain.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mju.sw.micro.domain.sample.entity.Sample;

public interface SampleRepository extends JpaRepository<Sample, Long> {
}
