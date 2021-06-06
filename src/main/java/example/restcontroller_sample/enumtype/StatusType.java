package example.restcontroller_sample.enumtype;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusType {

	NOT_START(0, "開始前"), PROCESSING(1, "処理中"), COMPLETE(2, "完了");

	private Integer code;
	private String label;

	/**
	 * 数値型のステータスを表すコードを返却する。<br>
	 * {@code @JsonValue}アノテーションを指定しない場合、RestControllerで返却されるJSONの値が「NOT_START」などの文字列となってしまう。
	 * @return
	 */
	@JsonValue
	public Integer getCode() {
		return this.code;
	}

	/**
	 * 
	 * @return
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	   * コードに合致する enum 定数を返す。
	   */
	public static StatusType getByCode(Integer code) {
		for (StatusType value : StatusType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		return null;
	}
	
	public static StatusType getByCode(String code) {
		return getByCode(Integer.valueOf(code));
	}

	@Override
	public String toString() {
		return "[" + String.valueOf(this.code) + ":" + this.label + "]";
	}
}
