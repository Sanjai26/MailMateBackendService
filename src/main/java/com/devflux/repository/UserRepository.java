package com.devflux.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devflux.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{

	List<UserEntity> findByCountry(String country);
}
