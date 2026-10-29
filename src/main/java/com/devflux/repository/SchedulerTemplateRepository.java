package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devflux.entity.SchedulerTemplateEntity;

@Repository
public interface SchedulerTemplateRepository extends JpaRepository<SchedulerTemplateEntity, Long> 
{

}
