package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devflux.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>
{

}
