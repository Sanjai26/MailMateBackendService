package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devflux.entity.SchedulerUserMapping;

@Repository
public interface SchedulerUserMappingRepository extends JpaRepository<SchedulerUserMapping, Long>
{

}
