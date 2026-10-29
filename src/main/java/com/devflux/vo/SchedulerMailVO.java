package com.devflux.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.devflux.entity.MailTemplateEntity;

public class SchedulerMailVO
{
	private long				id;

	private MailTemplateEntity	mailTemplateEntity;

	private String				scheduledBy;

	private List<String>		recipients	= new ArrayList<>();

	private LocalDateTime		lastUpdatedAt;

	private boolean				isActive;

	private String				message;

	private String				status;

	public long getId()
	{
		return id;
	}

	public MailTemplateEntity getMailTemplateEntity()
	{
		return mailTemplateEntity;
	}

	public void setMailTemplateEntity(MailTemplateEntity mailTemplateEntity)
	{
		this.mailTemplateEntity = mailTemplateEntity;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getScheduledBy()
	{
		return scheduledBy;
	}

	public void setScheduledBy(String scheduledBy)
	{
		this.scheduledBy = scheduledBy;
	}

	public List<String> getRecipients()
	{
		return recipients;
	}

	public void setRecipients(List<String> recipients)
	{
		this.recipients = recipients;
	}

	public LocalDateTime getLastUpdatedAt()
	{
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(LocalDateTime lastUpdatedAt)
	{
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SchedulerMailVO [id=");
		builder.append(id);
		builder.append(", mailTemplateEntity=");
		builder.append(mailTemplateEntity);
		builder.append(", scheduledBy=");
		builder.append(scheduledBy);
		builder.append(", recipients=");
		builder.append(recipients);
		builder.append(", lastUpdatedAt=");
		builder.append(lastUpdatedAt);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", message=");
		builder.append(message);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
