package com.devflux.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody
public class HomeController
{
	public String getHome()
	{
		return "Welcome to home";
	}
	
	@GetMapping("/dashboard")
	public String showDashBoard()
	{
		return "This is my DashBoard";
	}

}
