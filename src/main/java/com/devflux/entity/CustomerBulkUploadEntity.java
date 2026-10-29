package com.devflux.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_bulk_upload")
public class CustomerBulkUploadEntity
{
	public enum Status
	{
		PENDING, PROCESSING, COMPLETED, FAILED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long			id;

	@Column(name = "original_name", nullable = false)
	private String			originalName;

	@Column(name = "input_file_name")
	private String			inputFileName;

	@Column(name = "result_file_name")
	private String			resultFileName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Status			status	= Status.PENDING;

	@Column(name = "created_by", nullable = false, length = 100)
	private String			createdBy;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime	createdAt;

	@Column(name = "completed_at")
	private LocalDateTime	completedAt;

	@PrePersist
	void onCreate()
	{
		if (createdAt == null)
		{
			createdAt = LocalDateTime.now();
		}
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getOriginalName()
	{
		return originalName;
	}

	public void setOriginalName(String originalName)
	{
		this.originalName = originalName;
	}

	public String getInputFileName()
	{
		return inputFileName;
	}

	public void setInputFileName(String inputFileName)
	{
		this.inputFileName = inputFileName;
	}

	public String getResultFileName()
	{
		return resultFileName;
	}

	public void setResultFileName(String resultFileName)
	{
		this.resultFileName = resultFileName;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
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

	public LocalDateTime getCompletedAt()
	{
		return completedAt;
	}

	public void setCompletedAt(LocalDateTime completedAt)
	{
		this.completedAt = completedAt;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerBulkUploadEntity [id=");
		builder.append(id);
		builder.append(", originalName=");
		builder.append(originalName);
		builder.append(", inputFileName=");
		builder.append(inputFileName);
		builder.append(", resultFileName=");
		builder.append(resultFileName);
		builder.append(", status=");
		builder.append(status);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", completedAt=");
		builder.append(completedAt);
		builder.append("]");
		return builder.toString();
	}
	
}