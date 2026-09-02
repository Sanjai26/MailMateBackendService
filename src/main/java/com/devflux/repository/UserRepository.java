package com.devflux.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devflux.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
//	public int saveUser(UserEntity user);
//
//	public List<UserEntity> getAllUsers();
//
//	public UserEntity updateUser(UserEntity user);
//
//	public void deleteUserById(Long id);
//
//	public List<UserEntity> saveAllUser(List<UserEntity> users);
}
