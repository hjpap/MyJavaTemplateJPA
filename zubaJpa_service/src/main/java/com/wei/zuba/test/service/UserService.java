package com.wei.zuba.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wei.zuba.common.service.BaseService;
import com.wei.zuba.entity.User;
import com.wei.zuba.test.repository.UserRepository;

@Service
@Transactional
public class UserService extends BaseService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	@Transactional
	public List<User> findUser(){
		//List<User> list = userDao.findList(user);
		return userRepository.findAll();
	}
}
