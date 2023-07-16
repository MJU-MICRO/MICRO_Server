package mju.sw.micro.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

	ROLE_GUEST("GUEST"), ROLE_USER("USER"), ROLE_BANNED("BANNED"),
	ROLE_PRESIDENT("PRESIDENT"), ROLE_ADMIN("ADMIN");

	private final String key;
}
