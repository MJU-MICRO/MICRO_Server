package mju.sw.micro.domain.group.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Campus {

	SEOUL("서울"),
	YONGIN("용인");

	private final String campus;
}
