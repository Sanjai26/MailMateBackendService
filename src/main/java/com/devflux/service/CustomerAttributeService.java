package com.devflux.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.devflux.dto.CutomAttributeDefinitionDTO;
import com.devflux.entity.CustomerAttributeDefinitionEntity;
import com.devflux.entity.CustomerAttributeOptionEntity;
import com.devflux.repository.CustomAttributeDefinitionRepository;
import com.devflux.repository.CustomerAttributeOptionRepository;

@Service
public class CustomerAttributeService
{

	private CustomAttributeDefinitionRepository	attributeDefinitionRepository;

	private CustomerAttributeOptionRepository	attributeOptionRepository;
	
	CustomerAttributeService( CustomAttributeDefinitionRepository attributeDefinitionRepository, CustomerAttributeOptionRepository attributeOptionRepository)
	{
		this.attributeDefinitionRepository = attributeDefinitionRepository;
		this.attributeOptionRepository = attributeOptionRepository;
	}

	/**
	 * @return
	 */
	public List<CutomAttributeDefinitionDTO> getAllCustomAttribute()
	{
		List<CustomerAttributeDefinitionEntity> definitionEntities = attributeDefinitionRepository.findAll();
		List<CustomerAttributeOptionEntity> optionEntities = attributeOptionRepository.findAll();

		Map<Long, List<String>> optionsMap = new HashMap<>();
		for (CustomerAttributeOptionEntity optionEntity : optionEntities)
		{
			Long definitionId = optionEntity.getAttributeDefinition().getId();
			optionsMap.computeIfAbsent(definitionId, key -> new ArrayList<>()).add(optionEntity.getOptionValue());
		}
		List<CutomAttributeDefinitionDTO> attributeDefinitionDTOs = new ArrayList<>();
		for (CustomerAttributeDefinitionEntity entity : definitionEntities)
		{

			CutomAttributeDefinitionDTO attributeDefinitionDTO = new CutomAttributeDefinitionDTO();
			attributeDefinitionDTO.setId(entity.getId());
			attributeDefinitionDTO.setAttributeName(entity.getAttributeName());
			attributeDefinitionDTO.setAttributeType(entity.getAttributeType());
			attributeDefinitionDTO.setOrder(entity.getDisplayOrder());
			attributeDefinitionDTO.setStatus(entity.isStatus());
			List<String> values = optionsMap.getOrDefault(entity.getId(), new ArrayList<>());
			attributeDefinitionDTO.setValues(values);
			attributeDefinitionDTOs.add(attributeDefinitionDTO);
		}
		return attributeDefinitionDTOs;
	}

	/**
	 * @param id
	 * @return
	 */
	public CutomAttributeDefinitionDTO getCustomAttributeById(long id)
	{
		CutomAttributeDefinitionDTO attributeDefinitionDTO = new CutomAttributeDefinitionDTO();
		List<CustomerAttributeDefinitionEntity> definitionEntities = attributeDefinitionRepository.findAll();
		List<CustomerAttributeOptionEntity> optionEntities = attributeOptionRepository.findAll();

		Map<Long, List<String>> optionsMap = new HashMap<>();
		for (CustomerAttributeOptionEntity optionEntity : optionEntities)
		{
			Long definitionId = optionEntity.getAttributeDefinition().getId();
			optionsMap.computeIfAbsent(definitionId, key -> new ArrayList<>()).add(optionEntity.getOptionValue());
		}

		for (CustomerAttributeDefinitionEntity entity : definitionEntities)
		{
			if (id != entity.getId())
			{
				continue;
			}

			attributeDefinitionDTO.setId(entity.getId());
			attributeDefinitionDTO.setAttributeName(entity.getAttributeName());
			attributeDefinitionDTO.setAttributeType(entity.getAttributeType());
			attributeDefinitionDTO.setOrder(entity.getDisplayOrder());
			attributeDefinitionDTO.setStatus(entity.isStatus());
			List<String> values = optionsMap.getOrDefault(entity.getId(), new ArrayList<>());
			attributeDefinitionDTO.setValues(values);
			return attributeDefinitionDTO;
		}
		return attributeDefinitionDTO;
	}
}