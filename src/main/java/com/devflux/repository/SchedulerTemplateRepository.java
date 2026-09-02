package com.devflux.repository;

import java.util.List;

import com.devflux.entity.SchedulerTemplateEntity;

public interface SchedulerTemplateRepository {

	public List<SchedulerTemplateEntity> getAllSchedulerTemplates();

	public SchedulerTemplateEntity createSchedulerTemplate(SchedulerTemplateEntity schedulerTemplateEntity);

	public SchedulerTemplateEntity updateSchedulerTemplate(SchedulerTemplateEntity schedulerTemplateEntity);

	public void deleteSchedulerTemplate(Integer schedulerTemplateId);

}
