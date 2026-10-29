package com.devflux.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "settings")
public class SettingsEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int		id;

	@Column(name = "property_name", nullable = false, unique = true, length = 150)
	private String	propertyName;

	@Column(name = "property_value", length = 500)
	private String	propertyValue;

	@Column(name = "is_encrypted", nullable = false)
	private boolean	encrypted;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getPropertyName()
	{
		return propertyName;
	}

	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}

	public String getPropertyValue()
	{
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue)
	{
		this.propertyValue = propertyValue;
	}

	public boolean isEncrypted()
	{
		return encrypted;
	}

	public void setEncrypted(boolean encrypted)
	{
		this.encrypted = encrypted;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SettingsEntity [id=");
		builder.append(id);
		builder.append(", propertyName=");
		builder.append(propertyName);
		builder.append(", propertyValue=");
		builder.append(propertyValue);
		builder.append(", encrypted=");
		builder.append(encrypted);
		builder.append("]");
		return builder.toString();
	}
}