package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devflux.entity.CustomerAttributeDefinitionEntity;

public interface CustomerAttributeDefinitionRepository extends JpaRepository<CustomerAttributeDefinitionEntity, Long>
{
	
}
