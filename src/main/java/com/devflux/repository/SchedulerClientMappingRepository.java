package com.devflux.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devflux.entity.SchedulerClientMapping;

@Repository
public interface SchedulerClientMappingRepository extends JpaRepository<SchedulerClientMapping, Long>
{
	@Query("SELECT scm FROM SchedulerClientMapping scm JOIN FETCH scm.client JOIN FETCH scm.schedulerReport")
	List<SchedulerClientMapping> getAllSchedulerClientMappingDetails();
}
