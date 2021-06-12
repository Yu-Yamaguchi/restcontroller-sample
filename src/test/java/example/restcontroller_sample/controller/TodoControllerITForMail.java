package example.restcontroller_sample.controller;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=local")
public class TodoControllerITForMail {

	/** RestController用のリクエスト処理クラス */
	@Autowired
	private TestRestTemplate testRestTemplate;

	/**
	 * GreenMailを使ったメール送信のテスト用オブジェクト。
	 */
	@RegisterExtension
	static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
			.withConfiguration(GreenMailConfiguration.aConfig().withUser("test", "springboot"))
			.withPerMethodLifecycle(false);

	@Test
	public void 期限のフォーマット誤りで送信されるメール() throws IOException, MessagingException {
		var jsonMap = new HashMap<String, String>();
		jsonMap.put("taskName", "パラメータチェック_期限のフォーマット誤り");
		jsonMap.put("status", "0");
		jsonMap.put("priority", "0");
		jsonMap.put("timelimit", "2022/07/03");

		var json = new ObjectMapper().writeValueAsString(jsonMap);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		testRestTemplate.exchange("/todo", HttpMethod.POST, new HttpEntity<>(json, headers), String.class);

		MimeMessage msg = greenMail.getReceivedMessages()[0];
		var mailBody = GreenMailUtil.getBody(msg);
		
		assertThat(msg.getFrom()[0].toString()).isEqualTo("hoge-from@example.com");
		assertThat(msg.getReplyTo()[0].toString()).isEqualTo("hoge-from@example.com");
		
		assertThat(msg.getAllRecipients()[0].toString()).isEqualTo("hoge-to@example.com");

		assertThat(msg.getSubject()).isEqualTo("フォーマット検証エラー");
		
		assertThat(mailBody).contains("Cannot deserialize value of type `java.util.Date`");
		assertThat(mailBody).contains("yyyy-MM-dd");
	}
}
