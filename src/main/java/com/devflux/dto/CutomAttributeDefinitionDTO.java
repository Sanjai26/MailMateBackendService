package com.devflux.dto;

import java.util.List;

public class CutomAttributeDefinitionDTO
{
	private long			id;

	private String			attributeName;

	private String			attributeType;

	private boolean			status;

	private int				order;

	private List<String>	values;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getAttributeName()
	{
		return attributeName;
	}

	public void setAttributeName(String attributeName)
	{
		this.attributeName = attributeName;
	}

	public String getAttributeType()
	{
		return attributeType;
	}

	public void setAttributeType(String attributeType)
	{
		this.attributeType = attributeType;
	}

	public boolean isStatus()
	{
		return status;
	}

	public void setStatus(boolean status)
	{
		this.status = status;
	}

	public int getOrder()
	{
		return order;
	}

	public void setOrder(int order)
	{
		this.order = order;
	}

	public List<String> getValues()
	{
		return values;
	}

	public void setValues(List<String> values)
	{
		this.values = values;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("CutomAttributeDefinitionDTO [id=");
		builder.append(id);
		builder.append(", attributeName=");
		builder.append(attributeName);
		builder.append(", attributeType=");
		builder.append(attributeType);
		builder.append(", status=");
		builder.append(status);
		builder.append(", order=");
		builder.append(order);
		builder.append("]");
		return builder.toString();
	}
}
