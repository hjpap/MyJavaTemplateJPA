package com.wei.zuba.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "/login";
	}
	
	@RequestMapping(value="/loginPost",method=RequestMethod.POST)
	public ModelAndView loginPost() {
		return new ModelAndView("admin", "messages", "hello World@aaaaa");
	}
	
	@RequestMapping(value="/account",method=RequestMethod.GET)
	public ModelAndView admin() {
		return new ModelAndView("admin", "messages", "hello World@aaaaa");
	}
	
	
	
}
