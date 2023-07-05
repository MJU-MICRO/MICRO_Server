package mju.sw.micro.domain.club.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import mju.sw.micro.domain.club.domain.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
