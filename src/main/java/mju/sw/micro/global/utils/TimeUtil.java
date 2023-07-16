package mju.sw.micro.global.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	public static String generateExpiration(long seconds) {
		long currentTimeMillis = System.currentTimeMillis();
		long expirationMillis = currentTimeMillis + (seconds);
		Date expiration = new Date(expirationMillis);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(expiration);
	}
}
