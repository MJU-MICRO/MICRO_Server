package mju.sw.micro.global.security;

import java.io.Serializable;

public interface MicroUserDetails extends Serializable {
	String getEmail();

	String getMajor();

	String getNickName();

	String getInterest();

	String getIntroduction();

	String getPhoneNumber();
}