package com.devflux.vo;

public class SchedulerTemplateVO
{

	private Long	id;

	private String	scheduleName;

	private String	frequency;			// e.g.,// "Daily",// "Weekly",//
										// "Monthly"

	private String	scheduleDate;
	// Type: Daily/Weekly/Monthly
	// Date:
	// Time:
	// Date and time in string format // daily -> 10:30// Weekly -> Monday
	// 10:30// Monthly - > 1st 10:30

	private Boolean	isActive	= true;

	private Long	timeZoneId;

	private String	timeZoneName;

	private int		schedulerCount;
	
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

	public Long getTimeZoneId()
	{
		return timeZoneId;
	}

	public void setTimeZoneId(Long timeZoneId)
	{
		this.timeZoneId = timeZoneId;
	}

	public String getTimeZoneName()
	{
		return timeZoneName;
	}

	public void setTimeZoneName(String timeZoneName)
	{
		this.timeZoneName = timeZoneName;
	}

	public int getSchedulerCount()
	{
		return schedulerCount;
	}

	public void setSchedulerCount(int schedulerCount)
	{
		this.schedulerCount = schedulerCount;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SchedulerTemplateVO [id=");
		builder.append(id);
		builder.append(", scheduleName=");
		builder.append(scheduleName);
		builder.append(", frequency=");
		builder.append(frequency);
		builder.append(", scheduleDate=");
		builder.append(scheduleDate);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", timeZoneId=");
		builder.append(timeZoneId);
		builder.append(", timeZoneName=");
		builder.append(timeZoneName);
		builder.append(", schedulerCount=");
		builder.append(schedulerCount);
		return builder.toString();
	}

}