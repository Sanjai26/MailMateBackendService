package com.devflux.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scheduler_template")
public class SchedulerTemplateEntity
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long			id;

	@Column(name = "schedule_name", nullable = false, length = 100)
	private String			scheduleName;

	@Column(name = "frequency", nullable = false, length = 100)
	private String			frequency;							// e.g.,// "Daily",// "Weekly",// "Monthly"

	@Column(name = "scheduleDate", length = 100)
	private String			scheduleDate;		
	// Type: Daily/Weekly/Monthly
	// Date:
	// Time:
	// Date and time in string format // daily -> 10:30// Weekly -> Monday 10:30// Monthly - > 1st 10:30

	@Column(name = "is_active", nullable = false)
	private Boolean			isActive	= true;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime	createdAt	= LocalDateTime.now();

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getScheduleName()
	{
		return scheduleName;
	}

	public void setScheduleName(String scheduleName)
	{
		this.scheduleName = scheduleName;
	}

	public String getFrequency()
	{
		return frequency;
	}

	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}

	public String getScheduleDate()
	{
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate)
	{
		this.scheduleDate = scheduleDate;
	}

	public Boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive(Boolean isActive)
	{
		this.isActive = isActive;
	}

	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt)
	{
		this.createdAt = createdAt;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SchedulerTemplateEntity [id=");
		builder.append(id);
		builder.append(", scheduleName=");
		builder.append(scheduleName);
		builder.append(", frequency=");
		builder.append(frequency);
		builder.append(", scheduleDate=");
		builder.append(scheduleDate);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append("]");
		return builder.toString();
	}

}
