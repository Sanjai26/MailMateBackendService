package com.devflux.action;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devflux.dto.CustomerDTO;
import com.devflux.entity.CustomerEntity;
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
	
	@PostMapping
	public CustomerEntity addNewCustomer(@RequestBody CustomerEntity customer)
	{
		return customerService.createNewCustomer(customer);
	}

	@GetMapping("/{id}")
	public CustomerDTO getCustomerById(@PathVariable Long id)
	{
		return customerService.getCustomerByIdWithAttributes(id);
	}

	@PatchMapping("/{id}/status")
	public CustomerEntity changeUserStatus(@PathVariable Long id, @RequestParam boolean status)
	{
		return customerService.changeUserStatus(id, status);
	}
	
	@PutMapping("/{id}")
	public CustomerEntity changeUserDetails(@RequestBody CustomerEntity customer, @PathVariable Long id)
	{
		return customerService.changeUserDetails(customer, id);
	}
	
	@DeleteMapping("/{id}")
	public CustomerEntity deleteCustomer(@PathVariable Long id)
	{
		return customerService.deleteCustomer(id);
	}
}
