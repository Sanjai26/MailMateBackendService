package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devflux.entity.MailTemplateEntity;
import com.hazelcast.map.IMap;

@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplateEntity, Long> 
{

}
