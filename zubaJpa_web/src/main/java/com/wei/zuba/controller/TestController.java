package com.wei.zuba.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wei.zuba.entity.User;
import com.wei.zuba.service.CustomerService;


@Controller
@RequestMapping("/")
public class TestController {
	@Autowired
	CustomerService service;

	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("home", "messages", "hello World@aaaaa");
	}
	
	@RequestMapping(value="/getUser",method=RequestMethod.GET)
	public ModelAndView list() {
		List<User> list = service.getUser();
		return new ModelAndView("home", "messages", list);
	}
	
	@RequestMapping(value="/setAaa",method=RequestMethod.GET)
	public ModelAndView aaa(HttpServletRequest request) {
		request.getSession().setAttribute("aaa","aaa");
		return new ModelAndView("home", "messages", request.getSession().getAttribute("aaa"));
	}
	
	@RequestMapping(value="/getAaa",method=RequestMethod.GET)
	public ModelAndView getAaa(HttpServletRequest request) {
		return new ModelAndView("home", "messages", request.getSession().getAttribute("aaa"));
	}
}
