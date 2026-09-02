package com.devflux.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mail_template")
public class MailTemplateEntity
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long				id;

	@Column(name = "template_name", nullable = false, length = 100)
	private String				templateName;

	@Column(name = "subject", nullable = false, length = 200)
	private String				subject;

	@Column(name = "body", nullable = false, columnDefinition = "TEXT")
	private String				body;

	@Column(name = "is_html", nullable = false)
	private Boolean				isHtml		= true;

	@ManyToOne
	@JoinColumn(name = "created_by", referencedColumnName = "id")
	private MasterUserEntity	createdBy;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime		createdAt	= LocalDateTime.now();

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTemplateName()
	{
		return templateName;
	}

	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public Boolean getIsHtml()
	{
		return isHtml;
	}

	public void setIsHtml(Boolean isHtml)
	{
		this.isHtml = isHtml;
	}

	public MasterUserEntity getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(MasterUserEntity createdBy)
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

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("MailTemplate [id=");
		builder.append(id);
		builder.append(", templateName=");
		builder.append(templateName);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", body=");
		builder.append(body);
		builder.append(", isHtml=");
		builder.append(isHtml);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append("]");
		return builder.toString();
	}
}