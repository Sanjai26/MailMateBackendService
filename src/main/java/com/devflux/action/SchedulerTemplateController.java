package com.devflux.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devflux.entity.SchedulerTemplateEntity;
import com.devflux.service.SchedulerTemplateService;
import com.devflux.vo.SchedulerTemplateVO;

@RestController
@RequestMapping("/api")
public class SchedulerTemplateController
{
	@Autowired
	private SchedulerTemplateService schedulerTemplateService;

	SchedulerTemplateController(SchedulerTemplateService schedulerTemplateService)
	{
		this.schedulerTemplateService = schedulerTemplateService;
	}

	@PostMapping("/schedulerTemplate")
	public ResponseEntity<Map<String, Object>> addSchedulerTemplate(@RequestBody SchedulerTemplateEntity schedulerTemplate)
	{
		boolean isActive = schedulerTemplate.getIsActive() != null ? schedulerTemplate.getIsActive() : true;
		Long timeZoneId = schedulerTemplate.getTimeZone() != null ? schedulerTemplate.getTimeZone().getId() : null;
		SchedulerTemplateEntity obj = schedulerTemplateService.addSchedulerTemplate(schedulerTemplate.getScheduleName(), schedulerTemplate.getFrequency(),
				schedulerTemplate.getScheduleTime(), schedulerTemplate.getDayOfWeek(), schedulerTemplate.getDayOfMonth(), isActive, timeZoneId);
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Scheduler template '" + obj.getScheduleName() + "' created successfully.");
		response.put("schedulerTemplate", obj);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/schedulerTemplate")
	public List<SchedulerTemplateVO> getAllSchedulerTemplatesVO()
	{
		return schedulerTemplateService.getSchedulerTemplateVOList();
	}

	@GetMapping("/schedulerTemplate/{id}")
	public ResponseEntity<SchedulerTemplateEntity> getSchedulerTemplateById(@PathVariable Long id)
	{
		SchedulerTemplateEntity obj = schedulerTemplateService.getSchedulerTemplateById(id);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@PutMapping("/schedulerTemplate/{id}")
	public ResponseEntity<Map<String, Object>> modifySchedulerTemplate(@PathVariable Long id, @RequestBody SchedulerTemplateEntity schedulerTemplate)
	{
		boolean isActive = schedulerTemplate.getIsActive() != null ? schedulerTemplate.getIsActive() : true;
		Long timeZoneId = schedulerTemplate.getTimeZone().getId() != null ? schedulerTemplate.getTimeZone().getId() : null;
		Map<String, Object> response = schedulerTemplateService.modifySchedulerTemplate(id, schedulerTemplate.getScheduleName(), schedulerTemplate.getFrequency(),
				schedulerTemplate.getScheduleTime(), schedulerTemplate.getDayOfWeek(), schedulerTemplate.getDayOfMonth(), isActive, timeZoneId);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/schedulerTemplate/{id}")
	public ResponseEntity<Map<String, Object>> deleteSchedulerTemplate(@PathVariable Long id)
	{
		Map<String, Object> response = schedulerTemplateService.deleteSchedulerTemplateById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}