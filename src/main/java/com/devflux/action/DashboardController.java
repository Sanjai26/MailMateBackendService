package com.devflux.action;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devflux.service.DashBoardService;

@RequestMapping("/api")
@RestController
public class DashboardController
{

	private final DashBoardService dashboardService;

	DashboardController(DashBoardService dashboardService)
	{
		this.dashboardService = dashboardService;
	}
	
	@GetMapping("/dashboard")
	public ResponseEntity<Map<String, Object>> getDashboardService()
	{
		Map<String, Object> response = dashboardService.getDashboardData();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
