package com.devflux.dto;

import java.util.List;

public class SchedulerMailRequestDTO
{
	private Long		schedulerTemplateId;

	private Long		mailTemplateId;

	private List<Long>	customerIds;		// null on a status toggle, so
											// recipients stay unchanged

	private Boolean		active;

	public Long getSchedulerTemplateId()
	{
		return schedulerTemplateId;
	}

	public void setSchedulerTemplateId(Long schedulerTemplateId)
	{
		this.schedulerTemplateId = schedulerTemplateId;
	}

	public Long getMailTemplateId()
	{
		return mailTemplateId;
	}

	public void setMailTemplateId(Long mailTemplateId)
	{
		this.mailTemplateId = mailTemplateId;
	}

	public List<Long> getCustomerIds()
	{
		return customerIds;
	}

	public void setCustomerIds(List<Long> customerIds)
	{
		this.customerIds = customerIds;
	}

	public Boolean getActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SchedulerMailRequestDTO [schedulerTemplateId=");
		builder.append(schedulerTemplateId);
		builder.append(", mailTemplateId=");
		builder.append(mailTemplateId);
		builder.append(", customerIds=");
		builder.append(customerIds);
		builder.append(", active=");
		builder.append(active);
		builder.append("]");
		return builder.toString();
	}

}
