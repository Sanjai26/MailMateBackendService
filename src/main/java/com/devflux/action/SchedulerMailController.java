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

import com.devflux.dto.SchedulerMailRequestDTO;
import com.devflux.entity.SchedulerMailEntity;
import com.devflux.service.SchedulerMailService;
import com.devflux.vo.SchedulerMailVO;

@RestController
@RequestMapping("/api")
public class SchedulerMailController
{
	private final SchedulerMailService schedulerMailService;

	SchedulerMailController(SchedulerMailService schedulerMailService)
	{
		this.schedulerMailService = schedulerMailService;
	}

	@GetMapping("/schedulerMail")
	public List<SchedulerMailEntity> getAllSchedulerMails()
	{
		return schedulerMailService.getAllSchedulerMails();
	}

	@GetMapping("/schedulerMail/{templateId}")
	public ResponseEntity<List<SchedulerMailVO>> getSchedulerMailById(@PathVariable Long templateId)
	{
		List<SchedulerMailVO> obj = schedulerMailService.getAllSchedulerMailsByTemplate(templateId);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/schedulerMail/{id}")
	public ResponseEntity<Void> deleteSchedulerMail(@PathVariable Long id)
	{
		schedulerMailService.deleteSchedulerMailById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/schedulerMail")
    public SchedulerMailEntity create(@RequestBody SchedulerMailRequestDTO requestDto)
    {
        return schedulerMailService.create(requestDto);
    }

    @PutMapping("/schedulerMail/{id}")
    public SchedulerMailEntity update(@PathVariable Long id, @RequestBody SchedulerMailRequestDTO requestDto)
    {
        return schedulerMailService.update(id, requestDto);
    }

}
