package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devflux.entity.MasterUserEntity;

public interface MasterUserRepository  extends JpaRepository<MasterUserEntity, Long>{

}
