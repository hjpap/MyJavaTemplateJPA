package com.wei.zuba.test.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wei.zuba.entity.User;
import com.wei.zuba.test.service.UserService;


@RestController
@RequestMapping(value = "")
public class TestController {
	
//	@Autowired
//	private UserService userService;
//	
//	@RequestMapping(value={"/insert"},method = RequestMethod.GET)
//	public String index(Model model){
//		User user = new User();
//		user.setId(44);
//		user.setLoginName("user 1");
//		user.setEmail("user1xqq.com");
//
//		userService.saveUser(user);
//		model.addAttribute("message","index");
//		return "success";
//	}
//	
//	@RequestMapping(value={"/find"},method = RequestMethod.GET)
//	public List<User> find(Model model){
//		User user = new User();
//		user.setId(44);
//		user.setLoginName("user 1");
//		user.setEmail("user1@qq.com");
//		List<User> list = userService.findUser(user);
//		//model.addAttribute("message",list);
//		return list;
//	}
//	
//	@RequestMapping("login")  
//	public User login(User user) {  
//       
//        return user;  
//    }  
}
