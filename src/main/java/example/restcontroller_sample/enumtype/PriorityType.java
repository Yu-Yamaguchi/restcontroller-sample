package example.restcontroller_sample.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PriorityType {
	LOW(0, "低"),
	MIDDLE(1, "中"),
	IMPORTANT(2, "重要"),
	EMERGENCY(3, "緊急");
	
	private Integer code;
	private String label;
	
	/**
	   * コードに合致する enum 定数を返す。
	   */
	public static PriorityType getByCode(Integer code) {
		for (PriorityType value : PriorityType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		return null;
	}
	
	public static PriorityType getByCode(String code) {
		return getByCode(Integer.valueOf(code));
	}
	
	@Override
	public String toString() {
		return "[" + String.valueOf(this.code) + ":" + this.label + "]";
	}
}
