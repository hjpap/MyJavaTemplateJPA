package com.wei.zuba.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 把参数封装为hashMap 的快捷方式，可用链式编程 eg.
 * MapBuilder.with("k1","v1").add("k2","v2").build()
 * 
 * @author Ric.W
 * @version Revision:1.0.0
 */
public final class MapBuilder {

	private Map<String, Object> parameter;

	private MapBuilder(String key, Object value) {
		parameter = new HashMap<String, Object>();
		parameter.put(key, value);
	}

	public static MapBuilder with(String key, Object value) {
		return new MapBuilder(key, value);
	}

	public MapBuilder add(String key, Object value) {
		parameter.put(key, value);
		return this;
	}

	/**
	 * 返回map
	 * 
	 * @return
	 */
	public Map<String, Object> build() {
		return parameter;
	}

	/**
	 * 创建新增参数的时候，自动封装custId,createUser,lastUpdateUser,version
	 * @return
	 */
	public Map<String, Object> newBuild() {
//		String userId = SecurityUtil.getCurrentUserId();
//		if (userId != null) {
//			parameter.put("custId", userId);
//			parameter.put("createUser", userId);
//			parameter.put("lastUpdateUser", userId);
//			parameter.put("version", new BigDecimal("0"));
//		}
		return parameter;
	}

	
	/**
	 * 创建更新参数的时候，自动封装custId,lastUpdateUser
	 * @return
	 */
	public Map<String, Object> updateBuild() {
//		String userId = SecurityUtil.getCurrentUserId();
//		if (userId != null) {
//			parameter.put("custId", userId);
//			parameter.put("lastUpdateUser", userId);
//		}
		return parameter;
	}

}
