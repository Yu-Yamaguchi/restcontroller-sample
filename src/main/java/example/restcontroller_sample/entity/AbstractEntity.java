package example.restcontroller_sample.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class AbstractEntity {

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date created;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date updated;
	
	@PrePersist
    public void setPrePersist() {
		var dt = new Date();
		this.created = dt;
		this.updated = dt;
    }

    @PreUpdate
    public void setPreUpdate() {
        this.updated = new Date();
    }
}
