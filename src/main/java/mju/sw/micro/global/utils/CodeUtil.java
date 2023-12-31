package mju.sw.micro.global.utils;

import java.security.SecureRandom;

public class CodeUtil {

	private CodeUtil() {
	}

	public static String generateRandomCode() {
		SecureRandom random = new SecureRandom();
		StringBuilder key = new StringBuilder();

		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(4);

			switch (index) {
				case 0 -> key.append((char)(random.nextInt(26) + 97));
				case 1 -> key.append((char)(random.nextInt(26) + 65));
				default -> key.append(random.nextInt(9));
			}
		}
		return key.toString();
	}
}
