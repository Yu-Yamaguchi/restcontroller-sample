package example.restcontroller_sample.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SpringBootMail {
	/** ログ */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private Environment env;

	@Autowired
	private JavaMailSender sender;

	public void send(String subject, String content) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setFrom(env.getProperty("mail-settings.from-address"));
		mailMessage.setReplyTo(env.getProperty("mail-settings.from-address"));
		mailMessage.setTo(env.getProperty("mail-settings.to-address"));
		mailMessage.setSubject(subject);
		mailMessage.setText(content);
		
		logger.debug(mailMessage.toString());
		
		// JUnitでのテスト実行時にGreenMailを意図的に起動した状態にしないと、send処理で例外が発生する。
		// SpringBootApplicationでGreenMailを起動する処理を入れているが、JUnit実行時は動作しない。
		// メールのテストを対象としない場合などで困ってしまうので、try catchしておく。
		try {
			sender.send(mailMessage);
		} catch (Exception e) {
			logger.error("メール送信処理に失敗");
		}
	}
}
