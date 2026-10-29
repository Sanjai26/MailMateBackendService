package com.devflux.hazelcast.util;

public enum CacheEnum
{

	MAIL_TEMPLATE("mail-template"), USER("user"), Customer("Customer"), SCHEDULER_MAIL_TEMPLATE("scheduler-mail-template"), 
	SCHEDULER_MAIL("scheduler-mail"), SCHEDULER_MAIL_Customer_MAPPING("scheduler_Customer_mapping"), TIMEZONE("customer-timezone"), SETTINGS("settings");

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