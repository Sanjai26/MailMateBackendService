package com.devflux.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devflux.entity.SchedulerMailEntity;

@Repository
public interface SchedulerMailRepository extends JpaRepository<SchedulerMailEntity, Long>
{
	@Query("SELECT sm FROM SchedulerMailEntity sm JOIN FETCH sm.createdBy JOIN FETCH sm.schedulerTemplate JOIN FETCH sm.mailTemplate")
	List<SchedulerMailEntity> findAllWithDetails();
}
