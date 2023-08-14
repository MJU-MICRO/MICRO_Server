package mju.sw.micro.global.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.error.exception.ErrorCode;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

	@Value("${spring.mail.username}")
	private String senderEmail;
	private final JavaMailSender emailSender;

	@Override
	public void sendMessage(String recipientEmail, String title, String content, String extra) {
		ApiResponse<MimeMessage> message = createMessage(recipientEmail, title, content, extra);
		emailSender.send(message.getData());
	}

	private ApiResponse<MimeMessage> createMessage(String recipientEmail, String title, String content,
		String extra) {
		MimeMessage message = emailSender.createMimeMessage();
		try {
			String contents = String.format(content, extra);
			message.addRecipients(RecipientType.TO, recipientEmail); //받는 사람
			message.setSubject(title); //제목
			message.setText(contents, "UTF-8", "html"); //내용
			message.setFrom(new InternetAddress(senderEmail)); //보내는 사람
			return ApiResponse.ok(message);
		} catch (MessagingException e) {
			log.info("fail");
			return ApiResponse.withError(ErrorCode.BAD_REQUEST);
		}
	}

}
