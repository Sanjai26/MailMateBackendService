package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devflux.entity.SettingsEntity;

@Repository
public interface SettingsRepository extends JpaRepository<SettingsEntity, Integer>
{

}
