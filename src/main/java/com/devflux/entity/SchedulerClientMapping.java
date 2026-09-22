package com.devflux.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "scheduler_client_mapping")
public class SchedulerClientMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
	private ClientEntity client;

	@ManyToOne
	@JoinColumn(name = "scheduler_id", referencedColumnName = "id")
	private SchedulerMailEntity schedulerReport;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUserId() {
		return userId;
	}

	public void setUserId(UserEntity userId) {
		this.userId = userId;
	}

	public SchedulerMailEntity getSchedulerReport() {
		return schedulerReport;
	}

	public void setSchedulerReport(SchedulerMailEntity schedulerReport) {
		this.schedulerReport = schedulerReport;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SchedulerClientMapping [id=");
		builder.append(id);
		builder.append(", client=");
		builder.append(client);
		builder.append(", schedulerReport=");
		builder.append(schedulerReport);
		builder.append("]");
		return builder.toString();
	}

}
