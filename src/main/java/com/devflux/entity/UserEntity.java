package com.devflux.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	id;

	@Column(name = "username", nullable = false, length = 100)
	private String	username;

	@Column(name = "email", nullable = false, length = 100)
	private String	email;

	@Column(name = "password", nullable = false, length = 255)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String	password;

	@Column(name = "lastUpdatedAt", length = 100)
	private String	lastUpdatedAt;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getLastUpdatedAt()
	{
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(String lastUpdatedAt)
	{
		this.lastUpdatedAt = lastUpdatedAt;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("UserEntity [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", email=");
		builder.append(email);
		builder.append(", lastUpdatedAt=");
		builder.append(lastUpdatedAt);
		builder.append("]");
		return builder.toString();
	}
}
