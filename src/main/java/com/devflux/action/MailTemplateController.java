package com.devflux.action;

import java.util.List;

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

import com.devflux.entity.MailTemplateEntity;
import com.devflux.service.MailTemplateService;

@RestController
@RequestMapping("/api")
public class MailTemplateController
{
	private final MailTemplateService mailTemplateService;

	MailTemplateController(MailTemplateService mailTemplateService) {
		this.mailTemplateService = mailTemplateService;
	}

	@PostMapping("/mailTemplate")
	public ResponseEntity<MailTemplateEntity> addMailTemplate(@RequestBody MailTemplateEntity mailTemplate)
	{
		MailTemplateEntity obj = mailTemplateService.addMailTemplate(mailTemplate);
		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	@PutMapping("/mailTemplate/{id}")
	public ResponseEntity<MailTemplateEntity> modifyMailTemplate(@PathVariable Long id, @RequestBody MailTemplateEntity mailTemplate)
	{
		mailTemplate.setId(id);
		MailTemplateEntity obj = mailTemplateService.modifyMailTemplate(mailTemplate);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@GetMapping("/mailTemplate")
	public List<MailTemplateEntity> getAllMailTemplates()
	{
		return mailTemplateService.getAllMailTemplates();
	}

	@GetMapping("/mailTemplate/{id}")
	public ResponseEntity<MailTemplateEntity> getMailTemplateById(@PathVariable Long id)
	{
		MailTemplateEntity obj = mailTemplateService.getMailTemplateById(id);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/mailTemplate/{id}")
	public ResponseEntity<Void> deleteMailTemplate(@PathVariable Long id)
	{
		mailTemplateService.deleteMailTemplateById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
