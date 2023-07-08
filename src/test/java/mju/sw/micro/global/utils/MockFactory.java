package mju.sw.micro.global.utils;

import static mju.sw.micro.global.utils.MockConstants.MOCK_PHONE_NUMBER;
import static mju.sw.micro.global.utils.MockConstants.MOCK_USER_EMAIL;
import static mju.sw.micro.global.utils.MockConstants.MOCK_USER_NAME;
import static mju.sw.micro.global.utils.MockConstants.MOCK_USER_PASSWORD;
import static mju.sw.micro.global.utils.MockConstants.MOCK_INTEREST;
import static mju.sw.micro.global.utils.MockConstants.MOCK_INTRODUCTION;
import static mju.sw.micro.global.utils.MockConstants.MOCK_USER_NICKNAME;
import static mju.sw.micro.global.utils.MockConstants.MOCK_STUDENT_ID;
import static mju.sw.micro.global.utils.MockConstants.MOCK_MAJOR;

import mju.sw.micro.domain.user.domain.User;

public class MockFactory {
	public static User createMockUser(){
		return User.createUser(MOCK_USER_NAME,MOCK_USER_EMAIL,MOCK_PHONE_NUMBER,
			MOCK_INTEREST,MOCK_INTRODUCTION,MOCK_USER_NICKNAME, MOCK_STUDENT_ID,MOCK_MAJOR,
			MOCK_USER_PASSWORD,false,false);
	}
}
