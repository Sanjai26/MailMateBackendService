package com.devflux.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devflux.dto.CustomerAttributeDTO;
import com.devflux.dto.CustomerDTO;
import com.devflux.entity.CustomerAttributeDefinitionEntity;
import com.devflux.entity.CustomerEntity;
import com.devflux.hazelcast.cachemanager.CustomerCacheManager;
import com.devflux.repository.CustomAttributeDefinitionRepository;
import com.devflux.repository.CustomerRepository;

@Service
public class CustomerService
{
	private final CustomerRepository					customerRepository;

	private final CustomAttributeDefinitionRepository	attributeDefinitionRepository;

	private final CustomerCacheManager					customerCacheManager;

	CustomerService(CustomerRepository customerRepository, CustomAttributeDefinitionRepository attributeDefinitionRepository, CustomerCacheManager customerCacheManager)
	{
		this.customerRepository = customerRepository;
		this.attributeDefinitionRepository = attributeDefinitionRepository;
		this.customerCacheManager = customerCacheManager;
	}

	public List<CustomerDTO> getAllcustomersWithAttributes()
	{
		List<CustomerAttributeDefinitionEntity> definitions = attributeDefinitionRepository.findAll();
		return customerCacheManager.getAll().stream().map(Customer -> toDTO(Customer, definitions)).toList();
	}

	public CustomerDTO getCustomerByIdWithAttributes(long id)
	{
		CustomerEntity customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
		return toDTO(customer, attributeDefinitionRepository.findAll());
	}

	private CustomerDTO toDTO(CustomerEntity Customer, List<CustomerAttributeDefinitionEntity> definitions)
	{
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(Customer.getId());
		customerDTO.setFirstName(Customer.getFirstName());
		customerDTO.setLastName(Customer.getLastName());
		customerDTO.setCreatedAt(Customer.getCreatedAt());
		customerDTO.setActive(Customer.isActive());
		customerDTO.setMailAddress(Customer.getMailAddress());
		customerDTO.setPhoneNumber(Customer.getPhoneNumber());

		List<CustomerAttributeDTO> attributes = new ArrayList<>();
		for (CustomerAttributeDefinitionEntity definition : definitions)
		{
			String value = getAttributeValue(Customer, definition.getId());
			if (value != null && !value.isBlank())
			{
				attributes.add(new CustomerAttributeDTO(definition.getAttributeName(), value));
			}
			else
			{
				attributes.add(new CustomerAttributeDTO(definition.getAttributeName(), ""));
			}
		}
		customerDTO.setAttributes(attributes);
		return customerDTO;
	}

	private String getAttributeValue(CustomerEntity Customer, long attributeNumber)
	{

		if (1 == attributeNumber)
		{
			return Customer.getAttribute1();
		}
		if (2 == attributeNumber)
		{
			return Customer.getAttribute2();
		}
		if (3 == attributeNumber)
		{
			return Customer.getAttribute3();
		}
		if (4 == attributeNumber)
		{
			return Customer.getAttribute4();
		}
		if (5 == attributeNumber)
		{
			return Customer.getAttribute5();
		}
		return Customer.getAttribute1();
	}

	public CustomerEntity changeUserStatus(Long id, boolean status)
	{
		CustomerEntity customerEntity = customerCacheManager.get(id);
		if (status)
		{
			customerEntity.setActive(!status);
		}
		else
		{
			customerEntity.setActive(status);
		}
		customerCacheManager.update(customerEntity);
		customerRepository.save(customerEntity);
		return customerEntity;
	}

	public CustomerEntity changeUserDetails(CustomerEntity customer, Long id)
	{
		CustomerEntity customerEntity = customerCacheManager.get(id);
		customerEntity.setFirstName(customer.getFirstName());
		customerEntity.setLastName(customer.getLastName());
		customerEntity.setMailAddress(customer.getMailAddress());
		customerEntity.setPhoneNumber(customer.getPhoneNumber());
		customerEntity.setActive(customer.isActive());

		customerEntity.setAttribute1(customer.getAttribute1());
		customerEntity.setAttribute2(customer.getAttribute2());
		customerEntity.setAttribute3(customer.getAttribute3());
		customerEntity.setAttribute4(customer.getAttribute4());
		customerEntity.setAttribute5(customer.getAttribute5());

		customerCacheManager.update(customerEntity);
		customerRepository.save(customerEntity);
		return customerEntity;
	}

	public CustomerEntity createNewCustomer(CustomerEntity customer)
	{
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setFirstName(customer.getFirstName());
		customerEntity.setLastName(customer.getLastName());
		customerEntity.setMailAddress(customer.getMailAddress());
		customerEntity.setPhoneNumber(customer.getPhoneNumber());
		customerEntity.setActive(customer.isActive());

		customerEntity.setAttribute1(customer.getAttribute1());
		customerEntity.setAttribute2(customer.getAttribute2());
		customerEntity.setAttribute3(customer.getAttribute3());
		customerEntity.setAttribute4(customer.getAttribute4());
		customerEntity.setAttribute5(customer.getAttribute5());

		customerRepository.save(customerEntity);
		customerCacheManager.add(customerEntity);
		return customerEntity;
	}

	public CustomerEntity deleteCustomer(Long id)
	{
		CustomerEntity customerEntity = customerCacheManager.get(id);
		customerCacheManager.delete(id);
		customerRepository.delete(customerEntity);
		return customerEntity;
	}

	public List<CustomerEntity> getAllCustomer(boolean isActive)
	{
		List<CustomerEntity> activeCustomers = new ArrayList<>();
		activeCustomers.addAll(customerCacheManager.getAll().stream().filter(customer -> {
			if (isActive)
			{
				return customer.isActive();
			}
			else
			{
				return !customer.isActive();
			}
		}).toList());
		return activeCustomers;
	}
}
