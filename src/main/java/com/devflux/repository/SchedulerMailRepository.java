package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devflux.entity.SchedulerMailEntity;

public interface SchedulerMailRepository  extends JpaRepository<SchedulerMailEntity, Long>
{
	
}
