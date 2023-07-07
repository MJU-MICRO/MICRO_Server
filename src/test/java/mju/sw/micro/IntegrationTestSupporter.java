package mju.sw.micro;

import mju.sw.micro.domain.user.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import mju.sw.micro.domain.club.application.ClubRecruitmentService;
import mju.sw.micro.domain.club.dao.ClubRecruitmentRepository;
import mju.sw.micro.domain.club.dao.ClubRepository;

@SpringBootTest
@ActiveProfiles("test")
public abstract class IntegrationTestSupporter {

	@Autowired
	protected ClubRecruitmentService clubRecruitmentService;

	@Autowired
	protected ClubRepository clubRepository;

	@Autowired
	protected ClubRecruitmentRepository recruitmentRepository;
	@Autowired
	protected UserRepository userRepository;

}
