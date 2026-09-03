package com.devflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devflux.entity.MailTemplateEntity;

public interface MailTemplateRepository extends JpaRepository<MailTemplateEntity, Long> 
{

}
