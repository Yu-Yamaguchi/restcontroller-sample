package example.restcontroller_sample.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * JSON文字列からMapオブジェクトに変換します。
	 *
	 * @param jsonStr JSON文字列
	 * @return Mapオブジェクト
	 * @throws IOException 変換に失敗した場合
	 */
	public static Map<String, String> convertToMap(String jsonStr) throws IOException {
		// 引数チェック
		validateJsonStr(jsonStr);
		return JsonConverter.objectMapper.readValue(jsonStr, new TypeReference<Map<String, String>>() {
		});
	}

	/**
	 * JSON文字列のチェック.
	 *
	 * @param jsonStr JSON文字列
	 */
	private static void validateJsonStr(String jsonStr) {
		if (jsonStr == null) {
			throw new IllegalArgumentException("jsonStr is null.");
		}
		if (jsonStr.isEmpty()) {
			throw new IllegalArgumentException("jsonStr is empty.");
		}
	}
}
