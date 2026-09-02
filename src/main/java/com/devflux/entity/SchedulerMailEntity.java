package com.devflux.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

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
	private MasterUserEntity		createdBy;

	@Column(name = "last_run")
	private LocalDateTime			lastRun;

	@Column(name = "next_run")
	private LocalDateTime			nextRun;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime			createdAt	= LocalDateTime.now();

	@Column(name = "is_active", nullable = false)
	private boolean					isAtive;

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

	public MasterUserEntity getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(MasterUserEntity createdBy)
	{
		this.createdBy = createdBy;
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

	public boolean isAtive()
	{
		return isAtive;
	}

	public void setAtive(boolean isAtive)
	{
		this.isAtive = isAtive;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SchedulerReport [id=");
		builder.append(id);
		builder.append(", schedulerTemplate=");
		builder.append(schedulerTemplate);
		builder.append(", mailTemplate=");
		builder.append(mailTemplate);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastRun=");
		builder.append(lastRun);
		builder.append(", nextRun=");
		builder.append(nextRun);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", isAtive=");
		builder.append(isAtive);
		builder.append("]");
		return builder.toString();
	}

}
