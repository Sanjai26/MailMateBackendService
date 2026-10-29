package com.devflux.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devflux.dto.CutomAttributeDefinitionDTO;
import com.devflux.service.CustomerAttributeService;

@RestController
@RequestMapping("/api")
public class CustomAttributeController
{
	private CustomerAttributeService attributeService;
	
	CustomAttributeController(@Autowired CustomerAttributeService attributeService)
	{
		this.attributeService = attributeService;
	}

	@GetMapping("/customAttributeDefinition")
	public List<CutomAttributeDefinitionDTO> getAllUserAttributes()
	{
		List<CutomAttributeDefinitionDTO> attributeDefinitionDTOs = attributeService.getAllCustomAttribute();
		return attributeDefinitionDTOs;
	}
	
	@GetMapping("/customAttributeDefinition/{id}")
	public CutomAttributeDefinitionDTO getUserAttributeById(@PathVariable long id)
	{
		CutomAttributeDefinitionDTO attributeDefinitionDTOs = attributeService.getCustomAttributeById(id);
		return attributeDefinitionDTOs;
	}
}
