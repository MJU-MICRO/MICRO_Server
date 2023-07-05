package mju.sw.micro.domain.club.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClubClassification {

	CIRCLE("동아리"),
	ACADEMY("학회");

	private final String classification;
}
