package com.devflux.dto;

import java.time.LocalDateTime;

import com.devflux.entity.MailTemplateEntity;
import com.devflux.entity.SchedulerTemplateEntity;
import com.devflux.entity.UserEntity;

public class SchedulerMailDTO
{
	private Long					id;

	private SchedulerTemplateEntity	schedulerTemplate;

	private MailTemplateEntity		mailTemplate;

	private UserEntity				createdBy;

	private LocalDateTime			createdAt		= LocalDateTime.now();

	private boolean					isActive;

	private LocalDateTime			lastUpdatedAt	= LocalDateTime.now();

	private String					status;
	
	private String					message;

	public LocalDateTime getLastUpdatedAt()
	{
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(LocalDateTime lastUpdatedAt)
	{
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public SchedulerTemplateEntity getSchedulerTemplate()
	{
		return schedulerTemplate;
	}

	public void setSchedulerTemplate(SchedulerTemplateEntity schedulerTemplate)
	{
		this.schedulerTemplate = schedulerTemplate;
	}

	public MailTemplateEntity getMailTemplate()
	{
		return mailTemplate;
	}

	public void setMailTemplate(MailTemplateEntity mailTemplate)
	{
		this.mailTemplate = mailTemplate;
	}

	public UserEntity getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(UserEntity createdBy)
	{
		this.createdBy = createdBy;
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
		return isActive;
	}

	public void setActive(boolean isAtive)
	{
		this.isActive = isAtive;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public String getMessage()
	{
		return message;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SchedulerMailDTO [id=");
		builder.append(id);
		builder.append(", schedulerTemplate=");
		builder.append(schedulerTemplate);
		builder.append(", mailTemplate=");
		builder.append(mailTemplate);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", lastUpdatedAt=");
		builder.append(lastUpdatedAt);
		builder.append(", status=");
		builder.append(status);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
	
	

}