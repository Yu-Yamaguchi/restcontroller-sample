package example.restcontroller_sample.vo;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TodoParam {
	
	private String taskId;
	
	@NotNull(message = "taskNameが指定されていません。")
	private String taskName;
	
	@NotEmpty(message = "statusが指定されていません。")
	@Pattern(regexp = "^[0,1,2]{1}$", message = "statusは'0'または'1'または'2'を指定して下さい。")
	private String status;
	
	@NotEmpty(message = "priorityが指定されていません。")
	@Pattern(regexp = "^[0-3]{1}$", message = "priorityは'0'〜'3'を指定して下さい。")
	private String priority;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date timelimit;
}
