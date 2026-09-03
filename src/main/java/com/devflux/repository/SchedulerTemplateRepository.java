package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devflux.entity.SchedulerTemplateEntity;

public interface SchedulerTemplateRepository extends JpaRepository<SchedulerTemplateEntity, Long> 
{
	
}
