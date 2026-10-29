package com.devflux.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "scheduler_mail")
public class SchedulerMailEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long					id;

	@ManyToOne
	@JoinColumn(name = "scheduler_template_id", referencedColumnName = "id", nullable = false)
	private SchedulerTemplateEntity	schedulerTemplate;

	@ManyToOne
	@JoinColumn(name = "mail_template_id", referencedColumnName = "id", nullable = false)
	private MailTemplateEntity		mailTemplate;

	@ManyToOne
	@JoinColumn(name = "created_by", referencedColumnName = "id")
	private UserEntity				createdBy;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime			createdAt		= LocalDateTime.now();

	@Column(name = "is_active", nullable = false)
	private boolean					isActive;

	@Column(name = "last_updated_at", nullable = false)
	private LocalDateTime			lastUpdatedAt	= LocalDateTime.now();

	@Column(name = "status", nullable = false)
	private String					status;

	@Column(name = "message")
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

	public void setMessage(String message)
	{
		this.message = message;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SchedulerMailEntity [id=");
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
