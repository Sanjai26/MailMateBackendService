package com.devflux.action;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devflux.dto.ClientDTO;
import com.devflux.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController
{
	private final ClientService clientService;

	public ClientController(ClientService clientService)
	{
		this.clientService = clientService;
	}
	
	@GetMapping
	public List<ClientDTO> getAllClients()
	{
		return clientService.getAllClientsWithAttributes();
	}
	
	@GetMapping("/{id}")
	public ClientDTO getClientById(@PathVariable Long id)
	{
		return clientService.getClientByIdWithAttributes(id);
	}
}
