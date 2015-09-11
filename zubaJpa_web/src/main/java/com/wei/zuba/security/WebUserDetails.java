package com.wei.zuba.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.wei.zuba.entity.User;

/**
 * 用户详细信息，携带权限信息等等
 * 
 * @author Ric.W
 * @version 
 * 
 */
public class WebUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2814588798042739108L;

	private final User custInfo;

	public WebUserDetails() {
		this.custInfo = null;
	}

	
	public WebUserDetails(User custInfo) {
		this.custInfo = custInfo;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return custInfo.getPassword();
	}

	@Override
	public String getUsername() {
		return custInfo.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public User getCustInfo() {
		return custInfo;
	}

}
