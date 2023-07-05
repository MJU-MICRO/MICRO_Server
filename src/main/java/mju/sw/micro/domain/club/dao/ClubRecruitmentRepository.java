package mju.sw.micro.domain.club.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mju.sw.micro.domain.club.domain.Club;
import mju.sw.micro.domain.club.domain.ClubRecruitment;

public interface ClubRecruitmentRepository extends JpaRepository<ClubRecruitment, Long> {
	List<ClubRecruitment> findByClub(Club savedClub);
}
