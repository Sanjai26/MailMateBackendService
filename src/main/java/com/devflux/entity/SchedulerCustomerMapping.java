package com.devflux.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "scheduler_Customer_mapping")
public class SchedulerCustomerMapping
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long				id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Customer_id", referencedColumnName = "id", nullable = false)
	private CustomerEntity		Customer;

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

	public CustomerEntity getCustomer()
	{
		return Customer;
	}

	public void setCustomer(CustomerEntity Customer)
	{
		this.Customer = Customer;
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
		builder.append("SchedulerCustomerMapping [id=");
		builder.append(id);
		builder.append(", Customer=");
		builder.append(Customer);
		builder.append(", schedulerReport=");
		builder.append(schedulerReport);
		builder.append("]");
		return builder.toString();
	}

}
