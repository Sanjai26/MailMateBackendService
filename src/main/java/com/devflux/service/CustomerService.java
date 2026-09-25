package com.devflux.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devflux.dto.CustomerAttributeDTO;
import com.devflux.dto.CustomerDTO;
import com.devflux.entity.CustomerAttributeDefinitionEntity;
import com.devflux.entity.CustomerEntity;
import com.devflux.hazelcast.cachemanager.CustomerCacheManager;
import com.devflux.repository.CustomerAttributeDefinitionRepository;
import com.devflux.repository.CustomerRepository;

@Service
public class CustomerService
{
	@Autowired
	private CustomerRepository						clientRepository;

	@Autowired
	private CustomerAttributeDefinitionRepository	attributeDefinitionRepository;

	@Autowired
	private CustomerCacheManager					customerCacheManager;

	public List<CustomerDTO> getAllcustomersWithAttributes()
	{
		List<CustomerAttributeDefinitionEntity> definitions = attributeDefinitionRepository.findAll();
		return customerCacheManager.getAll().stream().map(client -> toDTO(client, definitions)).toList();
	}

	public CustomerDTO getClientByIdWithAttributes(long id)
	{
		CustomerEntity customer = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
		return toDTO(customer, attributeDefinitionRepository.findAll());
	}

	private CustomerDTO toDTO(CustomerEntity client, List<CustomerAttributeDefinitionEntity> definitions)
	{
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(client.getId());
		customerDTO.setFirstName(client.getFirstName());
		customerDTO.setLastName(client.getLastName());
		customerDTO.setCreatedAt(client.getCreatedAt());
		customerDTO.setActive(client.isActive());
		customerDTO.setMailAddress(client.getMailAddress());
		customerDTO.setPhoneNumber(client.getPhoneNumber());

		List<CustomerAttributeDTO> attributes = new ArrayList<>();
		for (CustomerAttributeDefinitionEntity definition : definitions)
		{
			String value = getAttributeValue(client, definition.getAttributeNumber());
			if (value != null && !value.isBlank())
			{
				attributes.add(new CustomerAttributeDTO(definition.getAttributeName(), value));
			}
		}
		customerDTO.setAttributes(attributes);
		return customerDTO;
	}

	private String getAttributeValue(CustomerEntity client, Integer attributeNumber)
	{
		return switch (attributeNumber)
		{
		case 1 -> client.getAttribute1();
		case 2 -> client.getAttribute2();
		case 3 -> client.getAttribute3();
		case 4 -> client.getAttribute4();
		case 5 -> client.getAttribute5();
		default -> null;
		};
	}
}
