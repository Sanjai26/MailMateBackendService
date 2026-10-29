package com.devflux.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devflux.entity.SchedulerCustomerMapping;


@Repository
public interface SchedulerCustomerMappingRepository extends JpaRepository<SchedulerCustomerMapping, Long>
{
	@Query("SELECT scm FROM SchedulerCustomerMapping scm JOIN FETCH scm.Customer JOIN FETCH scm.schedulerReport")
	List<SchedulerCustomerMapping> getAllSchedulerCustomerMappingDetails();

	void deleteBySchedulerReportId(Long schedulerId);
}