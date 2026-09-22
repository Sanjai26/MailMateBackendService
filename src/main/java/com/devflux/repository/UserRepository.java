package com.devflux.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devflux.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
	Optional<UserEntity> findByUsername(String username);
}
