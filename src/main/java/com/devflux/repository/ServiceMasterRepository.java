package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devflux.entity.ServiceMasterEntity;

@Repository
public interface ServiceMasterRepository extends JpaRepository<ServiceMasterEntity, Long>
{

}
