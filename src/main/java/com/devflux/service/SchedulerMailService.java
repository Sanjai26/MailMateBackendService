package com.devflux.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.devflux.dto.SchedulerMailRequestDTO;
import com.devflux.entity.CustomerEntity;
import com.devflux.entity.MailTemplateEntity;
import com.devflux.entity.SchedulerCustomerMapping;
import com.devflux.entity.SchedulerMailEntity;
import com.devflux.entity.SchedulerTemplateEntity;
import com.devflux.entity.UserEntity;
import com.devflux.enums.MailSchedulerEnum;
import com.devflux.hazelcast.cachemanager.SchedulerCustomerMappingCacheManager;
import com.devflux.hazelcast.cachemanager.SchedulerMailCacheManager;
import com.devflux.hazelcast.cachemanager.SchedulerTemplateCacheManager;
import com.devflux.repository.CustomerRepository;
import com.devflux.repository.MailTemplateRepository;
import com.devflux.repository.SchedulerCustomerMappingRepository;
import com.devflux.repository.SchedulerMailRepository;
import com.devflux.repository.UserRepository;
import com.devflux.vo.SchedulerMailVO;

@Service
public class SchedulerMailService
{

	private final SchedulerMailRepository				schedulerMailRepository;

	private final SchedulerTemplateCacheManager			schedulerTemplateCacheManager;

	private final MailTemplateRepository				mailTemplateRepository;

	private final SchedulerMailCacheManager				schedulerMailCacheManager;

	private final UserRepository						userRepository;

	private final SchedulerCustomerMappingCacheManager	schedulerCustomerMappingCacheManager;

	private final SchedulerCustomerMappingRepository		schedulerCustomerMappingRepository;

	private final UserService	userService;
	
	private final CustomerRepository		customerRepository;

	public SchedulerMailService(SchedulerMailRepository schedulerMailRepository, SchedulerTemplateCacheManager schedulerTemplateCacheManager,
			MailTemplateRepository mailTemplateRepository, UserRepository userRepository, SchedulerMailCacheManager schedulerMailCacheManager,
			SchedulerCustomerMappingCacheManager schedulerCustomerMappingCacheManager,
			SchedulerCustomerMappingRepository schedulerCustomerMappingRepository, UserService userService, CustomerRepository customerRepository)
	{
		this.schedulerMailRepository = schedulerMailRepository;
		this.schedulerTemplateCacheManager = schedulerTemplateCacheManager;
		this.mailTemplateRepository = mailTemplateRepository;
		this.userRepository = userRepository;
		this.schedulerMailCacheManager = schedulerMailCacheManager;
		this.schedulerCustomerMappingCacheManager = schedulerCustomerMappingCacheManager;
		this.schedulerCustomerMappingRepository = schedulerCustomerMappingRepository;
		this.userService = userService;
		this.customerRepository = customerRepository;
	}

	public SchedulerMailEntity addSchedulerMail(long schedulerTemplateId, long mailTemplateId, long createdById, boolean isActive)
	{
		SchedulerMailEntity schedulerMail = new SchedulerMailEntity();
		schedulerMail.setSchedulerTemplate(getSchedulerTemplate(schedulerTemplateId));
		schedulerMail.setMailTemplate(getMailTemplate(mailTemplateId));
		schedulerMail.setCreatedBy(getMasterUser(createdById));
		schedulerMail.setCreatedAt(LocalDateTime.now());
		schedulerMail.setLastUpdatedAt(LocalDateTime.now());
		schedulerMail.setActive(isActive);
		schedulerMail.setStatus(MailSchedulerEnum.PENDING.name());
		schedulerMailCacheManager.add(schedulerMail); // Add to cache
		return schedulerMailRepository.save(schedulerMail);
	}

	public List<SchedulerMailEntity> getAllSchedulerMails()
	{
		return schedulerMailRepository.findAll();
	}

	public List<SchedulerMailVO> getAllSchedulerMailsByTemplate(long templateId)
	{

		List<SchedulerMailVO> voList = new ArrayList<>();

		List<SchedulerMailEntity> entities = schedulerMailCacheManager.getAll().stream().toList();

		List<SchedulerCustomerMapping> CustomerMappings = schedulerCustomerMappingCacheManager.getAll().stream().toList();

		for (SchedulerMailEntity entity : entities)
		{

			// Filter by scheduler template ID
			if (entity.getSchedulerTemplate() == null || entity.getSchedulerTemplate().getId() != templateId)
			{
				continue;
			}

			SchedulerMailVO vo = new SchedulerMailVO();

			vo.setId(entity.getId());
			vo.setMailTemplateEntity(entity.getMailTemplate());

			vo.setScheduledBy(entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);

			vo.setLastUpdatedAt(entity.getLastUpdatedAt());
			vo.setActive(entity.isActive());
			vo.setMessage(entity.getMessage());
			vo.setStatus(entity.getStatus());

			// Add recipient names
			CustomerMappings.stream().filter(mapping -> mapping.getSchedulerReport() != null && mapping.getSchedulerReport().getId().equals(entity.getId()))
					.filter(mapping -> mapping.getCustomer() != null).forEach(mapping -> {
						String firstName = mapping.getCustomer().getFirstName();
						String lastName = mapping.getCustomer().getLastName();

						vo.getRecipients().add(((firstName != null) ? firstName : "") + " " + ((lastName != null) ? lastName : "").trim());
					});

			voList.add(vo);
		}

		return voList;
	}

	public SchedulerMailEntity getSchedulerMailById(long id)
	{
		return schedulerMailRepository.findById(id).orElseThrow(() -> new RuntimeException("Scheduler mail not found with id: " + id));
	}

	public SchedulerMailEntity modifySchedulerMail(long id, long schedulerTemplateId, long mailTemplateId, long createdById, boolean isActive)
	{
		SchedulerMailEntity schedulerMail = getSchedulerMailById(id);
		schedulerMail.setSchedulerTemplate(getSchedulerTemplate(schedulerTemplateId));
		schedulerMail.setMailTemplate(getMailTemplate(mailTemplateId));
		schedulerMail.setCreatedBy(userService.getUserIdFromAuthentication());
		schedulerMail.setActive(isActive);
		schedulerMail.setLastUpdatedAt(LocalDateTime.now());
		schedulerMailCacheManager.update(schedulerMail); // Update cache
		return schedulerMailRepository.save(schedulerMail);
	}

	@Transactional
	public void deleteSchedulerMailById(long id)
	{
		if (!schedulerMailRepository.existsById(id))
		{
			throw new RuntimeException("Scheduler mail not found with id: " + id);
		}

		List<Long> mappingIds = schedulerCustomerMappingCacheManager.getAll().stream()
				.filter(mapping -> mapping.getSchedulerReport() != null && mapping.getSchedulerReport().getId() != null
						&& mapping.getSchedulerReport().getId().equals(id))
				.map(SchedulerCustomerMapping::getId)
				.toList();

		schedulerCustomerMappingRepository.deleteBySchedulerReportId(id);
		mappingIds.forEach(schedulerCustomerMappingCacheManager::delete);

		schedulerMailRepository.deleteById(id);
		schedulerMailCacheManager.delete(id);
	}

	private SchedulerTemplateEntity getSchedulerTemplate(long id)
	{
		return schedulerTemplateCacheManager.get(id);
	}

	private MailTemplateEntity getMailTemplate(long id)
	{
		return mailTemplateRepository.findById(id).orElseThrow(() -> new RuntimeException("Mail template not found with id: " + id));
	}

	private UserEntity getMasterUser(long id)
	{
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Master user not found with id: " + id));
	}

	public SchedulerMailEntity create(SchedulerMailRequestDTO requestDto)
	{
		if (requestDto == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
		}

		if (requestDto.getSchedulerTemplateId() == null || requestDto.getMailTemplateId() == null || requestDto.getActive() == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"schedulerTemplateId, mailTemplateId and active are required");
		}

		SchedulerMailEntity schedulerMail = new SchedulerMailEntity();
		schedulerMail.setSchedulerTemplate(getSchedulerTemplate(requestDto.getSchedulerTemplateId()));
		schedulerMail.setMailTemplate(getMailTemplate(requestDto.getMailTemplateId()));
		schedulerMail.setCreatedBy(userService.getUserIdFromAuthentication());
		schedulerMail.setCreatedAt(LocalDateTime.now());
		schedulerMail.setLastUpdatedAt(LocalDateTime.now());
		schedulerMail.setActive(requestDto.getActive());
		schedulerMail.setStatus(MailSchedulerEnum.PENDING.name());

		SchedulerMailEntity saved = schedulerMailRepository.save(schedulerMail);
		schedulerMailCacheManager.add(saved);

		replaceRecipientMappings(saved, requestDto.getCustomerIds());
		return saved;
	}

	@Transactional
	public SchedulerMailEntity update(Long id, SchedulerMailRequestDTO requestDto)
	{
		if (id == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Scheduler mail id is required");
		}

		if (requestDto == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
		}

		SchedulerMailEntity schedulerMail = getSchedulerMailById(id);

		if (requestDto.getSchedulerTemplateId() != null)
		{
			schedulerMail.setSchedulerTemplate(getSchedulerTemplate(requestDto.getSchedulerTemplateId()));
		}

		if (requestDto.getMailTemplateId() != null)
		{
			schedulerMail.setMailTemplate(getMailTemplate(requestDto.getMailTemplateId()));
		}

		if (requestDto.getActive() != null)
		{
			schedulerMail.setActive(requestDto.getActive());
		}

		schedulerMail.setCreatedBy(userService.getUserIdFromAuthentication());
		schedulerMail.setLastUpdatedAt(LocalDateTime.now());

		SchedulerMailEntity updated = schedulerMailRepository.save(schedulerMail);
		schedulerMailCacheManager.update(updated);

		// Null customerIds means status-only/template-only update and keeps recipients unchanged.
		if (requestDto.getCustomerIds() != null)
		{
			replaceRecipientMappings(updated, requestDto.getCustomerIds());
		}

		return updated;
	}

	private void replaceRecipientMappings(SchedulerMailEntity schedulerMail, List<Long> customerIds)
	{
		if (schedulerMail == null || schedulerMail.getId() == null)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valid scheduler mail is required");
		}

		List<Long> existingMappingIds = schedulerCustomerMappingCacheManager.getAll().stream()
				.filter(mapping -> mapping.getSchedulerReport() != null && schedulerMail.getId().equals(mapping.getSchedulerReport().getId()))
				.map(SchedulerCustomerMapping::getId).toList();

		schedulerCustomerMappingRepository.deleteBySchedulerReportId(schedulerMail.getId());
		existingMappingIds.forEach(schedulerCustomerMappingCacheManager::delete);

		if (customerIds == null || customerIds.isEmpty())
		{
			return;
		}

		Set<Long> uniqueCustomerIds = new HashSet<>(customerIds);
		List<CustomerEntity> customers = customerRepository.findAllById(uniqueCustomerIds);
		if (customers.size() != uniqueCustomerIds.size())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more customerIds are invalid");
		}

		for (CustomerEntity customer : customers)
		{
			SchedulerCustomerMapping mapping = new SchedulerCustomerMapping();
			mapping.setSchedulerReport(schedulerMail);
			mapping.setCustomer(customer);
			SchedulerCustomerMapping savedMapping = schedulerCustomerMappingRepository.save(mapping);
			schedulerCustomerMappingCacheManager.add(toCacheSafeMapping(savedMapping));
		}
	}

	private SchedulerCustomerMapping toCacheSafeMapping(SchedulerCustomerMapping mapping)
	{
		SchedulerCustomerMapping safeMapping = new SchedulerCustomerMapping();
		safeMapping.setId(mapping.getId());

		SchedulerMailEntity schedulerRef = new SchedulerMailEntity();
		schedulerRef.setId(mapping.getSchedulerReport().getId());
		safeMapping.setSchedulerReport(schedulerRef);

		CustomerEntity customerRef = new CustomerEntity();
		customerRef.setId(mapping.getCustomer().getId());
		customerRef.setFirstName(mapping.getCustomer().getFirstName());
		customerRef.setLastName(mapping.getCustomer().getLastName());
		safeMapping.setCustomer(customerRef);

		return safeMapping;
	}
}