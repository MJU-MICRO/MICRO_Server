package mju.sw.micro.domain.recruitment.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.group.dao.StudentGroupRepository;
import mju.sw.micro.domain.group.domain.StudentGroup;

@Service
@RequiredArgsConstructor
public class GroupRecruitmentScheduler {
	private final StudentGroupRepository studentGroupRepository;

	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void updateRecruitmentStatus() {
		List<StudentGroup> groups = studentGroupRepository.findByIsRecruitTrue();
		LocalDateTime now = LocalDateTime.now();

		for (StudentGroup group : groups) {
			boolean isExpired = group.getRecruitmentList().stream()
				.allMatch(recruitment -> recruitment.getEndDateTime().isBefore(now));

			if (isExpired) {
				group.updateIsRecruit(false);
			}
		}
	}
}
