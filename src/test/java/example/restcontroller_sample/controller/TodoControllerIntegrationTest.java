package example.restcontroller_sample.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.restcontroller_sample.util.JsonConverter;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=local")
public class TodoControllerIntegrationTest {

	/** RestController用のリクエスト処理クラス */
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void タスクを新規で登録する() throws IOException {
		var jsonMap = new HashMap<String, String>();
		jsonMap.put("taskName", "タスク１");
		jsonMap.put("status", "0");
		jsonMap.put("priority", "0");

		var json = new ObjectMapper().writeValueAsString(jsonMap);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<String> response = testRestTemplate.exchange("/todo", HttpMethod.POST, new HttpEntity<>(json, headers), String.class);

		String responseBody = response.getBody().toString();
		assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());

		var map = JsonConverter.convertToMap(responseBody);

		assertThat(map.get("taskName")).isEqualTo("タスク１");
		assertThat(map.get("status")).isEqualTo("0");
		assertThat(map.get("priority")).isEqualTo("LOW");
		assertThat(map.get("timelimit")).isNull();
	}

	@Test
	public void タスクを新規で登録する_期限付きタスク() throws IOException {
		var jsonMap = new HashMap<String, String>();
		jsonMap.put("taskName", "タスク２");
		jsonMap.put("status", "0");
		jsonMap.put("priority", "0");
		jsonMap.put("timelimit", "2022-07-03");

		var json = new ObjectMapper().writeValueAsString(jsonMap);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<String> response = testRestTemplate.exchange("/todo", HttpMethod.POST, new HttpEntity<>(json, headers), String.class);

		String responseBody = response.getBody().toString();
		assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());

		var map = JsonConverter.convertToMap(responseBody);

		assertThat(map.get("taskName")).isEqualTo("タスク２");
		assertThat(map.get("status")).isEqualTo("0");
		assertThat(map.get("priority")).isEqualTo("LOW");
		assertTrue(map.get("timelimit").startsWith("2022-07-03"));
	}

	@Test
	public void パラメータチェック_期限のフォーマット誤り() throws IOException, MessagingException {
		var jsonMap = new HashMap<String, String>();
		jsonMap.put("taskName", "パラメータチェック_期限のフォーマット誤り");
		jsonMap.put("status", "0");
		jsonMap.put("priority", "0");
		jsonMap.put("timelimit", "2022/07/03");

		var json = new ObjectMapper().writeValueAsString(jsonMap);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<String> response = testRestTemplate.exchange("/todo", HttpMethod.POST, new HttpEntity<>(json, headers), String.class);

		String responseBody = response.getBody().toString();
		assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());

		var map = JsonConverter.convertToMap(responseBody);

		assertThat(map.get("errorCode")).isEqualTo("920");
		assertThat(map.get("message")).contains("Cannot deserialize value of type `java.util.Date`");
		assertThat(map.get("message")).contains("yyyy-MM-dd");

	}
}
