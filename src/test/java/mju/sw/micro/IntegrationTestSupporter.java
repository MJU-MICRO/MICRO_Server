package mju.sw.micro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import mju.sw.micro.domain.recruitment.dao.GroupRecruitmentRepository;
import mju.sw.micro.domain.user.application.AuthService;
import mju.sw.micro.domain.user.dao.EmailCodeRedisRepository;
import mju.sw.micro.domain.user.dao.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
public abstract class IntegrationTestSupporter {

	/*@Autowired
	protected ClubRecruitmentService clubRecruitmentService;

	@Autowired
	protected ClubRecruitmentDeleteService clubRecruitmentdeleteService;

	@Autowired
	protected ClubRepository clubRepository;*/

	@Autowired
	protected GroupRecruitmentRepository recruitmentRepository;
	@Autowired
	protected UserRepository userRepository;
	@Autowired
	protected AuthService authService;
	@Autowired
	protected EmailCodeRedisRepository emailCodeRedisRepository;

}
