package mju.sw.micro.domain.club.application;

import mju.sw.micro.domain.club.dao.ClubRepository;
import mju.sw.micro.domain.club.dto.request.ClubRequestDto;
import mju.sw.micro.domain.club.domain.BasicClub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubService {
	private final ClubRepository clubDao;

	@Autowired
	public ClubService(ClubRepository clubDao) {
		this.clubDao = clubDao;
	}

	public void saveClubInfo(ClubRequestDto requestDto) {
		if (requestDto.getClubName() == null || requestDto.getClassification() == null ||
			requestDto.getRelatedTag() == null || requestDto.getIntroduction() == null ||
			requestDto.getCampus() == null) {
			throw new IllegalArgumentException("clubName, classification, relatedTag, introduction, and campus are required.");
		}

		BasicClub club = new BasicClub(requestDto);

		clubDao.save(club);
	}
}
