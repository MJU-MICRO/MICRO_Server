package mju.sw.micro.global.constants;

public final class JwtConstants {

	public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	public static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
	public static final String HEADER = "Authorization";
	public static final long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 10L * 1000;
	public static final long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60L * 24 * 30 * 1000;
	public static final String CLAIM_EMAIL = "email";
	public static final String PREFIX_BEARER = "Bearer ";

}
