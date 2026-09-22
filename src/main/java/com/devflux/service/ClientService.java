package com.devflux.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devflux.dto.ClientAttributeDTO;
import com.devflux.dto.ClientDTO;
import com.devflux.entity.ClientAttributeDefinitionEntity;
import com.devflux.entity.ClientEntity;
import com.devflux.repository.ClientAttributeDefinitionRepository;
import com.devflux.repository.ClientRepository;

@Service
public class ClientService
{
	private final ClientRepository clientRepository;
	private final ClientAttributeDefinitionRepository attributeDefinitionRepository;

	public ClientService(
			ClientRepository clientRepository,
			ClientAttributeDefinitionRepository attributeDefinitionRepository)
	{
		this.clientRepository = clientRepository;
		this.attributeDefinitionRepository = attributeDefinitionRepository;
	}

	public List<ClientDTO> getAllClientsWithAttributes()
	{
		List<ClientAttributeDefinitionEntity> definitions = attributeDefinitionRepository.findAll();
		return clientRepository.findAll().stream()
				.map(client -> toDTO(client, definitions))
				.toList();
	}

	public ClientDTO getClientByIdWithAttributes(long id)
	{
		ClientEntity client = clientRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
		return toDTO(client, attributeDefinitionRepository.findAll());
	}

	private ClientDTO toDTO(ClientEntity client, List<ClientAttributeDefinitionEntity> definitions)
	{
		ClientDTO clientDTO = new ClientDTO();
		clientDTO.setId(client.getId());
		clientDTO.setFirstName(client.getFirstName());
		clientDTO.setLastName(client.getLastName());
		clientDTO.setCreatedAt(client.getCreatedAt());
		clientDTO.setActive(client.isActive());
		clientDTO.setMailAddress(client.getMailAddress());
		clientDTO.setPhoneNumber(client.getPhoneNumber());

		List<ClientAttributeDTO> attributes = new ArrayList<>();
		for (ClientAttributeDefinitionEntity definition : definitions)
		{
			String value = getAttributeValue(client, definition.getAttributeNumber());
			if (value != null && !value.isBlank())
			{
				attributes.add(new ClientAttributeDTO(definition.getAttributeName(), value));
			}
		}
		clientDTO.setAttributes(attributes);
		return clientDTO;
	}

	private String getAttributeValue(ClientEntity client, Integer attributeNumber)
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
