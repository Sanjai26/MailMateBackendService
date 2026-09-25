package com.devflux.action;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devflux.dto.CustomerDTO;
import com.devflux.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController
{
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService)
	{
		this.customerService = customerService;
	}
	
	@GetMapping
	public List<CustomerDTO> getAllcustomers()
	{
		return customerService.getAllcustomersWithAttributes();
	}
	
	@GetMapping("/{id}")
	public CustomerDTO getClientById(@PathVariable Long id)
	{
		return customerService.getClientByIdWithAttributes(id);
	}
}
