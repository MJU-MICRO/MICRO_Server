package mju.sw.micro.global.constants;

public final class EmailConstants {
	private EmailConstants() {
	}

	public static final String EMAIL_SIGN_UP_TITLE = "Micro Sign-up Verification Code";
	public static final String EMAIL_SIGN_UP_CONTENT_HTML = "<div>" + "Thank you for signing up for 놀명뭐하니.<br>"
		+ "Please enter the verification code below to complete the sign-up.<br>" + "CODE : <strong> %s </strong>"
		+ "<div>";
	public static final String EMAIL_WITHDRAWAL_TITLE = "Micro Withdrawal Announcement";
	public static final String EMAIL_WITHDRAWAL_CONTENT_HTML = "<div>" + "Thank you for using our services.<br>"
		+ "Your membership withdrawal is complete. <br>" + "Your Withdrawal Email : <strong> %s </strong>"
		+ "<div>";
	public static final Long EMAIL_TOKEN_EXPIRATION_TIME = 60 * 5L * 1000;

}
