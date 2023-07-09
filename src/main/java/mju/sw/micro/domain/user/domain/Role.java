package mju.sw.micro.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

	GUEST("GUEST"), USER("USER"),
	PRESIDENT("PRESIDENT"), ADMIN("ADMIN");

	private final String key;
}
