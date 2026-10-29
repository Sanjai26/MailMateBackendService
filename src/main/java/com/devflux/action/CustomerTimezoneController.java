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

import com.devflux.entity.CustomerTimeZoneEntity;
import com.devflux.service.CustomerTimezoneService;

@RestController
@RequestMapping("/api/cutomerTimezone")
public class CustomerTimezoneController
{
	private final CustomerTimezoneService customerTimezoneService;

	public CustomerTimezoneController(CustomerTimezoneService customerTimezoneService)
	{
		this.customerTimezoneService = customerTimezoneService;
	}

	@GetMapping
	public List<CustomerTimeZoneEntity> getAllCustomerTimezones()
	{
		return customerTimezoneService.getAllCustomerTimezones();
	}

	@GetMapping("/{id}")
	public CustomerTimeZoneEntity getCustomerTimezoneById(@PathVariable Long id)
	{
		return customerTimezoneService.getCustomerTimezoneById(id);
	}

	@PostMapping
	public CustomerTimeZoneEntity createCustomerTimezone(@RequestBody CustomerTimeZoneEntity request)
	{
		return customerTimezoneService.createCustomerTimezone(request);
	}

	@PutMapping("/{id}")
	public CustomerTimeZoneEntity updateCustomerTimezone(@PathVariable Long id, @RequestBody CustomerTimeZoneEntity request)
	{
		return customerTimezoneService.updateCustomerTimezone(id, request);
	}

	@PatchMapping("/{id}/status")
	public CustomerTimeZoneEntity changeStatus(@PathVariable Long id, @RequestParam boolean active)
	{
		return customerTimezoneService.changeStatus(id, active);
	}

	@DeleteMapping("/{id}")
	public CustomerTimeZoneEntity deleteCustomerTimezone(@PathVariable Long id)
	{
		return customerTimezoneService.deleteCustomerTimezone(id);
	}
}