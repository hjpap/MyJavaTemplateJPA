package com.wei.zuba.security;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wei.zuba.entity.User;
import com.wei.zuba.service.CustomerService;


/**
 * 用户详情服务
 * @author Ric.W
 *
 */
@Service("webUserDetailsService")
public class WebUserDetailsService implements UserDetailsService {
	public static final Logger logger = LoggerFactory.getLogger(WebUserDetailsService.class);

	@Autowired
	private CustomerService customerService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginName", username);
		User custInfo = null;
		try {
			custInfo = customerService.findCustInfo(map);
		} catch (Exception e) {
			logger.error(username+"--〉 登录失败 " + e);
			throw new BadCredentialsException("系统异常，请稍后重试！");
		}
		
		if(custInfo!=null) {
			boolean enabled = custInfo.getEnableStatus();
			if(enabled) {
				throw new BadCredentialsException("该用户已被冻结，请联系管理员！");
			}
			
			UserDetails user = new WebUserDetails(custInfo);
			
			return user;
		}
		else {
			throw new BadCredentialsException("账号或密码有误，请重新输入！");
		}
	}
}
