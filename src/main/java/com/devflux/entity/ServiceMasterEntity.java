package com.devflux.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_master")
public class ServiceMasterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "client_id", nullable = false)
	private Long clientId;

	@Column(name = "service_name", nullable = false, length = 255)
	private String serviceName;

	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceMasterEntity [id=");
		builder.append(id);
		builder.append(", clientId=");
		builder.append(clientId);
		builder.append(", serviceName=");
		builder.append(serviceName);
		builder.append(", description=");
		builder.append(description);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append("]");
		return builder.toString();
	}

}
