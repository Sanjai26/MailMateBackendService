package com.devflux.dto;

public class ClientAttributeDTO
{
	private String	name;

	private String	value;

	public ClientAttributeDTO(String name, String value)
	{
		this.name = name;
		this.value = value;
	}

	public String getName()
	{
		return name;
	}

	public String getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("ClientAttributeDTO [name=");
		builder.append(name);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}
