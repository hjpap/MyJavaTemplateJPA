/** 
 * @(#)LoginSuccessHandler.java 1.0.0 2014-10-8 上午11:07:42  
 *  
 * Copyright © 2014 善林金融.  All rights reserved.  
 */

package com.wei.zuba.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.wei.zuba.entity.User;
import com.wei.zuba.service.CustomerService;
import com.wei.zuba.utils.DeEnCodeUtil;



/**
 * 
 * 客户登录成功后，把当前登录信息保存到session中，重新封装便于获取
 * 
 * @author zhangyx
 * @version $Revision:1.0.0, $Date: 2014-10-8 上午11:07:42 $
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	public static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);
	
	
	@Autowired
	private CustomerService custInfoService;
	
	//@Value("${slb.sessionCookie.domain}")
	//private String cookieDomain;
	
    public LoginSuccessHandler(){
    }

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		String rememberMe = request.getParameter("rememberMe");
		
		WebUserDetails webUserDetail = (WebUserDetails) authentication.getPrincipal();
		
//		if(logger.isDebugEnabled()) {
//			List<SessionInformation> allSessions = sessionRegistry.getAllSessions(authentication.getPrincipal(), true);
//			for(SessionInformation si : allSessions) {
//				logger.debug(si.getSessionId());
//			}
//		}
		
		User custInfo = webUserDetail.getCustInfo();
		remember(request, response, rememberMe, custInfo);

		recordLoginLog(custInfo, getIpAddr(request));
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	/**
	 * 记住用户名
	 * @param response
	 * @param rememberMe
	 * @param custInfo
	 */
	private void remember(HttpServletRequest request, HttpServletResponse response, String rememberMe, User custInfo) {
		Cookie nickNameCookie = new Cookie("nickName", custInfo.getLoginName());
		nickNameCookie.setPath("/");
		nickNameCookie.setMaxAge(-1);
		//nickNameCookie.setDomain(cookieDomain);
		response.addCookie(nickNameCookie);
		if (null != rememberMe && rememberMe.toUpperCase().equals("ON")) {
			
			Cookie[] cookies = request.getCookies();
			for(Cookie c : cookies) {
				if(c.getName().equals("7d418d57421e622343fe607c6b08204e") && c.getValue().equals(custInfo.getLoginName())) {
					return;
				}
			}
			
			Cookie usernameC = new Cookie("7d418d57421e622343fe607c6b08204e", DeEnCodeUtil.encode(custInfo.getLoginName()));
			/**
			 * 记住密码15天
			 */
//			usernameC.setDomain(Constant.SERVER_DOMAIN);
			usernameC.setMaxAge(1296000);
		
//			usernameC.setSecure(true);
			try {
//				usernameC.setHttpOnly(true);
			} catch (Exception e) {
			}
			usernameC.setPath("/");
			response.addCookie(usernameC);
		} else {
			Cookie[] cookies = request.getCookies();
			for(Cookie c : cookies) {
				if(c.getName().equals("7d418d57421e622343fe607c6b08204e")) {
					c.setValue(null);
					c.setMaxAge(0);
					Cookie cookie = new Cookie("7d418d57421e622343fe607c6b08204e", null); 
					cookie.setMaxAge(0);
					cookie.setPath("/");
					try {
//						cookie.setHttpOnly(true);
					} catch (Exception e) {
					}
					response.addCookie(cookie); 
					return;
				}
			}
		}
	}
	
	/**
	 * 取得远程ip,不一定完全正确，尤其是通过了Apache,Squid等反向代理软件就不能获取到客户端的真实IP地址
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 记录登录日志，目前只是记录登录客户的ID以及登录的IP
	 * @param custInfo
	 * @param loginIp
	 */
	private void recordLoginLog(User custInfo, String loginIp) {
	
	}

}
