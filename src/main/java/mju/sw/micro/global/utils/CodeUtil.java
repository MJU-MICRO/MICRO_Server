package mju.sw.micro.global.utils;

import java.util.Random;

public class CodeUtil {

	public static String generateRandomCode() {
		Random random = new Random();
		StringBuilder key = new StringBuilder();

		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(4);

			switch (index) {
				case 0 -> key.append((char) (random.nextInt(26) + 97));
				case 1 -> key.append((char) (random.nextInt(26) + 65));
				default -> key.append(random.nextInt(9));
			}
		}
		return key.toString();
	}
}
