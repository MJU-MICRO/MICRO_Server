package mju.sw.micro.domain.sample.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import mju.sw.micro.domain.sample.domain.Sample;

public interface SampleRepository extends JpaRepository<Sample, Long> {
}
