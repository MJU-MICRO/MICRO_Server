package mju.sw.micro.domain.recruitment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityPeriod {
	SEMESTER("한 학기"),
	YEAR("1년"),
	OVER_YEAR("1년 이상");

	private final String description;
}
