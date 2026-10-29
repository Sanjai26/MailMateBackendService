package com.devflux.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devflux.entity.CustomerAttributeOptionEntity;


@Repository
public interface CustomerAttributeOptionRepository extends JpaRepository<CustomerAttributeOptionEntity, Long>
{
	List<CustomerAttributeOptionEntity> findByAttributeDefinitionId(Long attributeDefinitionId);
}