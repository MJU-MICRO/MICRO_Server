package mju.sw.micro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import mju.sw.micro.domain.recruitment.dao.GroupRecruitmentRepository;

@DataJpaTest
@ActiveProfiles("test")
public abstract class RepositoryTestSupporter {
	@Autowired
	protected GroupRecruitmentRepository recruitmentRepository;
}
