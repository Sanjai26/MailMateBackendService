package com.devflux.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devflux.entity.CustomerTimeZoneEntity;


@Repository
public interface CustomerTimezoneRepository extends JpaRepository<CustomerTimeZoneEntity, Long>
{
	Optional<CustomerTimeZoneEntity> findByZoneIdIgnoreCase(String zoneId);

	boolean existsByZoneIdIgnoreCase(String zoneId);

	boolean existsByZoneIdIgnoreCaseAndIdNot(String zoneId, Long id);
}