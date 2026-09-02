package com.devflux.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "scheduler_user_mapping")
public class SchedulerUserMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private UserEntity userId;

	@ManyToOne
	@JoinColumn(name = "scheduler_id", referencedColumnName = "id")
	private SchedulerMailEntity schedulerReport;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SchedulerUserMapping [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", schedulerReport=");
		builder.append(schedulerReport);
		builder.append("]");
		return builder.toString();
	}

}
