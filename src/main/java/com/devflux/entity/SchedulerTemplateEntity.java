package com.devflux.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "scheduler_template")
public class SchedulerTemplateEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long					id;

	@Column(name = "schedule_name", nullable = false, length = 100)
	private String					scheduleName;

	// DAILY, WEEKLY, MONTHLY
	@Column(name = "frequency", nullable = false, length = 100)
	private String					frequency;

	// Example: 10:30:00
	@Column(name = "schedule_time")
	private LocalTime				scheduleTime;

	// Example: MONDAY, WEDNESDAY, SUNDAY
	// Used for WEEKLY schedules
	@Column(name = "day_of_week", length = 10)
	private String					dayOfWeek;

	// Example: 1, 5, 15, 28
	// Used for MONTHLY schedules
	@Column(name = "day_of_month")
	private Integer					dayOfMonth;

	@Column(name = "is_active", nullable = false)
	private Boolean					isActive		= true;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime			createdAt		= LocalDateTime.now();

	// Keeping your existing database column spelling
	@Column(name = "last_upadted_at", nullable = false)
	private LocalDateTime			lastUpdatedAt	= LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "time_zone_id", nullable = false)
	private CustomerTimeZoneEntity	timeZone;

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

	public LocalTime getScheduleTime()
	{
		return scheduleTime;
	}

	public void setScheduleTime(LocalTime scheduleTime)
	{
		this.scheduleTime = scheduleTime;
	}

	public String getDayOfWeek()
	{
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek)
	{
		this.dayOfWeek = dayOfWeek;
	}

	public Integer getDayOfMonth()
	{
		return dayOfMonth;
	}

	public void setDayOfMonth(Integer dayOfMonth)
	{
		this.dayOfMonth = dayOfMonth;
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

	public LocalDateTime getLastUpdatedAt()
	{
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(LocalDateTime lastUpdatedAt)
	{
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public CustomerTimeZoneEntity getTimeZone()
	{
		return timeZone;
	}

	public void setTimeZone(CustomerTimeZoneEntity timeZone)
	{
		this.timeZone = timeZone;
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
		builder.append(", dayOfWeek=");
		builder.append(dayOfWeek);
		builder.append(", dayOfMonth=");
		builder.append(dayOfMonth);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", lastUpdatedAt=");
		builder.append(lastUpdatedAt);
		builder.append(", timeZone=");
		builder.append(timeZone);
		builder.append("]");
		return builder.toString();
	}

}
