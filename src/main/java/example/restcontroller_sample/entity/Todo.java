package example.restcontroller_sample.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import example.restcontroller_sample.enumtype.PriorityType;
import example.restcontroller_sample.enumtype.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
@Entity
@Table(name="todo")
public class Todo extends AbstractEntity{
	@Id
	@TableGenerator( name = "sequence", table = "sequence", pkColumnName = "seqname", pkColumnValue = "TASK_ID", valueColumnName = "current_val", initialValue = 1, allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.TABLE, generator="sequence")
	@Column(name = "taskid")
	private Long taskId;
	
	@Column(name = "taskname", nullable = false)
	private String taskName;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private StatusType status;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private PriorityType priority;
	
	@Temporal(TemporalType.DATE)
	private Date timelimit;
}
