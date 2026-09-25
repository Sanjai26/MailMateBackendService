package com.devflux.dto;

import java.time.LocalDateTime;

public class SchedulerMailDTO {

    private Long id;

    private Long schedulerTemplateId;

    private Long mailTemplateId;

    private Long createdById;

    private LocalDateTime lastRun;

    private LocalDateTime nextRun;

    private LocalDateTime createdAt;

    private boolean active;
    

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getSchedulerTemplateId()
	{
		return schedulerTemplateId;
	}

	public void setSchedulerTemplateId(Long schedulerTemplateId)
	{
		this.schedulerTemplateId = schedulerTemplateId;
	}

	public Long getMailTemplateId()
	{
		return mailTemplateId;
	}

	public void setMailTemplateId(Long mailTemplateId)
	{
		this.mailTemplateId = mailTemplateId;
	}

	public Long getCreatedById()
	{
		return createdById;
	}

	public void setCreatedById(Long createdById)
	{
		this.createdById = createdById;
	}

	public LocalDateTime getLastRun()
	{
		return lastRun;
	}

	public void setLastRun(LocalDateTime lastRun)
	{
		this.lastRun = lastRun;
	}

	public LocalDateTime getNextRun()
	{
		return nextRun;
	}

	public void setNextRun(LocalDateTime nextRun)
	{
		this.nextRun = nextRun;
	}

	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt)
	{
		this.createdAt = createdAt;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SchedulerMailCache [id=");
		builder.append(id);
		builder.append(", schedulerTemplateId=");
		builder.append(schedulerTemplateId);
		builder.append(", mailTemplateId=");
		builder.append(mailTemplateId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastRun=");
		builder.append(lastRun);
		builder.append(", nextRun=");
		builder.append(nextRun);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", active=");
		builder.append(active);
		builder.append("]");
		return builder.toString();
	}

}