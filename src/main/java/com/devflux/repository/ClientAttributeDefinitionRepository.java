package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devflux.entity.ClientAttributeDefinitionEntity;

public interface ClientAttributeDefinitionRepository extends JpaRepository<ClientAttributeDefinitionEntity, Long>
{
	
}
