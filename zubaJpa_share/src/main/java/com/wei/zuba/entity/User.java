/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wei.zuba.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户Entity
 * @author ThinkGem
 * @version 2013-12-05
 */

@Table(name="w_user")
@Entity
@Getter
@Setter
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="LOGIN_NAME")
	private String loginName;// 登录名
	@Column(name="PASSWORD")
	private String password;// 密码
	@Column(name="USER_NAME")
	private String userName;
	@Column(name="EMAIL")
	private String email;	// 邮箱
	@Column(name="STATUS")
	private Boolean enableStatus;
	

}