package mju.sw.micro.global.constants;

public final class EmailConstants {

	public static final class VerifyEmailConstants {

		public static final String EMAIL_TITLE = "Micro Sign-up Verification Code";
		public static final String EMAIL_CONTENT_HTML = "<div>" +
			"Thank you for signing up for Micro.<br>" +
			"Please enter the verification code below to complete the sign-up.<br>" +
			"CODE : <strong> %s </strong>" +
			"<div>";
		public static final Long EMAIL_TOKEN_EXPIRATION_TIME = 60 * 5L;
	}

}
