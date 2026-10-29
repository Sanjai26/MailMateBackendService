package com.devflux.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devflux.entity.SettingsEntity;
import com.devflux.service.MailService;
import com.devflux.service.SettingsService;

@RestController
@RequestMapping("/api/settings")
public class SettingsController
{
	private final SettingsService			settingsService;

	private static final org.slf4j.Logger	OUT	= org.slf4j.LoggerFactory.getLogger(SettingsController.class);

	private final MailService				mailService;

	SettingsController(SettingsService settingsService, MailService mailService)
	{
		this.settingsService = settingsService;
		this.mailService = mailService;
	}

	@PostMapping
	public ResponseEntity<SettingsEntity> createSetting(@RequestBody SettingsEntity settingsEntity)
	{
		SettingsEntity created = settingsService.createSetting(settingsEntity);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@GetMapping
	public List<SettingsEntity> getAllSettings()
	{
		return settingsService.getAllSettings();
	}

	@GetMapping("/{id}")
	public ResponseEntity<SettingsEntity> getSettingById(@PathVariable Integer id)
	{
		SettingsEntity setting = settingsService.getSettingById(id);
		return new ResponseEntity<>(setting, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> updateSetting(@RequestBody List<SettingsEntity> settingsEntities)
	{
		SettingsEntity updated = null;
		for (SettingsEntity settingsEntity : settingsEntities)
		{
			updated = settingsService.updateSetting(settingsEntity);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Settings updated successfully");
		response.put("settingsEntity", updated.toString());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/testMail")
	public ResponseEntity<Map<String, Object>> testMail(@RequestBody String email)
	{
		boolean result = mailService.testSendMail(email);

		Map<String, Object> response = new HashMap<>();
		if (result)
		{
			response.put("message", "Test email sent successfully to " + email);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else
		{
			response.put("message", "Failed to send test email to " + email);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
