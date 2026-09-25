package com.devflux.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "scheduler_client_mapping")
public class SchedulerClientMapping
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long				id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
	private CustomerEntity		client;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scheduler_id", referencedColumnName = "id")
	private SchedulerMailEntity	schedulerReport;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public CustomerEntity getClient()
	{
		return client;
	}

	public void setClient(CustomerEntity client)
	{
		this.client = client;
	}

	public SchedulerMailEntity getSchedulerReport()
	{
		return schedulerReport;
	}

	public void setSchedulerReport(SchedulerMailEntity schedulerReport)
	{
		this.schedulerReport = schedulerReport;
	}

	@Override
	public String toString()
	{
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
