package com.devflux.hazelcast.util;

public enum CacheEnum
{

	MAIL_TEMPLATE("mail-template"), USER("user"), CLIENT("client"), SCHEDULER_MAIL_TEMPLATE("scheduler-mail-template"), 
	SCHEDULER_MAIL("scheduler-mail"), SCHEDULER_MAIL_CLIENT_MAPPING("scheduler_client_mapping");

	private final String value;

	CacheEnum(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
