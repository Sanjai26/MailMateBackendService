package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devflux.entity.CustomerAttributeDefinitionEntity;

@Repository
public interface CustomAttributeDefinitionRepository extends JpaRepository<CustomerAttributeDefinitionEntity, Long>
{
	
}
