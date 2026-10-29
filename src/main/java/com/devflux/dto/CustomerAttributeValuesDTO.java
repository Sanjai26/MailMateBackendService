package com.devflux.dto;

public class CustomerAttributeValuesDTO
{
	private long	id;

	private long	attributeDefinitionId;

	private String	values;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getAttributeDefinitionId()
	{
		return attributeDefinitionId;
	}

	public void setAttributeDefinitionId(long attributeDefinitionId)
	{
		this.attributeDefinitionId = attributeDefinitionId;
	}

	public String getValues()
	{
		return values;
	}

	public void setValues(String values)
	{
		this.values = values;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerAttributeValuesDTO [id=");
		builder.append(id);
		builder.append(", attributeDefinitionId=");
		builder.append(attributeDefinitionId);
		builder.append(", values=");
		builder.append(values);
		builder.append("]");
		return builder.toString();
	}

}
