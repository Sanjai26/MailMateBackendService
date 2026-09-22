package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devflux.entity.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, Long>
{

}
