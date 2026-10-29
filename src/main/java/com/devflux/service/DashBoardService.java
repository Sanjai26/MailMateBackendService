package com.devflux.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class DashBoardService
{
	private SchedulerTemplateService	schedulerTemplateService;

	private SchedulerMailService		schedulerMailService;

	private CustomerService				customerService;

	private MailTemplateService			mailTemplateService;
	
	DashBoardService(SchedulerTemplateService schedulerTemplateService, SchedulerMailService schedulerMailService, CustomerService customerService, MailTemplateService mailTemplateService)
	{
		this.schedulerTemplateService = schedulerTemplateService;
		this.schedulerMailService = schedulerMailService;
		this.customerService = customerService;
		this.mailTemplateService = mailTemplateService;
	}

	public Map<String, Object> getDashboardData()
	{
		Map<String, Object> dashboardData = new java.util.HashMap<>();
		dashboardData.put("totalSchedulerTemplates", schedulerTemplateService.getAllSchedulerTemplates().size());
		dashboardData.put("totalSchedulerMails", schedulerMailService.getAllSchedulerMails().size());
		dashboardData.put("totalMailTemplate", mailTemplateService.getAllMailTemplates().size());
		dashboardData.put("totalCustomers", customerService.getAllcustomersWithAttributes().size());
		dashboardData.put("totalActiveCustomers", customerService.getAllCustomer(true).size());
		dashboardData.put("totalInActiveCustomers", customerService.getAllCustomer(false).size());
		return dashboardData;
	}

}
