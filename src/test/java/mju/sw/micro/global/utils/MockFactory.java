package mju.sw.micro.global.utils;

import static mju.sw.micro.global.utils.MockConstants.*;

import mju.sw.micro.domain.user.domain.User;

public class MockFactory {
	public static User createMockUser() {
		return User.createUser(MOCK_USER_NAME, MOCK_USER_EMAIL, MOCK_PHONE_NUMBER,
			MOCK_INTRODUCTION, MOCK_STUDENT_ID, MOCK_MAJOR,
			MOCK_USER_PASSWORD, false);
	}

	public static User createMockAdminUser() {
		return User.createUser(MOCK_USER_NAME, MOCK_ADMIN_USER_EMAIL, MOCK_ADMIN_PHONE_NUMBER,
			MOCK_INTRODUCTION, MOCK_ADMIN_STUDENT_ID, MOCK_MAJOR,
			MOCK_USER_PASSWORD, false);
	}
}
