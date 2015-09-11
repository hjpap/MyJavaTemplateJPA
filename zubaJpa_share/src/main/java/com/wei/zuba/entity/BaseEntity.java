package com.wei.zuba.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Ric.W
 * @version 2015-07-09
 */
@Data
public abstract class BaseEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 实体编号
	 */
	protected String id;

}
