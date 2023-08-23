package mju.sw.micro.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {
	private Long id;
	private String email;
	private String profileImageUrl;
	private String name;
	private String nickName;
	private String major;
	private String introduction;
}



