package mju.sw.micro.global.adapter;

public interface MailService {

	void sendMessage(String recipientEmail, String title, String content, String extra);
}
