package mju.sw.micro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import mju.sw.micro.domain.recruitment.dao.GroupRecruitmentRepository;
import mju.sw.micro.domain.admin.application.AdminService;
import mju.sw.micro.domain.user.application.AuthService;
import mju.sw.micro.domain.user.application.UserService;
import mju.sw.micro.domain.user.dao.EmailCodeRedisRepository;
import mju.sw.micro.domain.user.dao.UserRepository;
import mju.sw.micro.global.adapter.MailService;

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
	protected UserService userService;
	@Autowired
	protected AuthService authService;
	@Autowired
	protected AdminService adminService;
	@Autowired
	protected EmailCodeRedisRepository emailCodeRedisRepository;
	@Autowired
	protected PasswordEncoder encoder;
	@MockBean
	protected MailService mailService;

}
