
package com.wei.zuba.vo;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.wei.zuba.utils.Json;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResultVo implements Serializable {

	private static final long serialVersionUID = 4116312283845727413L;
	public Map<String, Object> result = Maps.newHashMap();

	public ResultVo(boolean isSuccess) {
		this(isSuccess, isSuccess ? "成功!" : "失败!");
	}

	public ResultVo(boolean isSuccess, Object message) {
		this(isSuccess, message, null);
	}

	public ResultVo(boolean isSuccess, Object message, Object data) {
		renderResult(isSuccess, message, data);
	}

	private void renderResult(boolean success, Object message, Object data) {
		result.put("success", success ? true : false);
		result.put("message", message);
		result.put("data", data);
	}

	public static ResultVo getInstance(boolean isSuccess) {
		return new ResultVo(isSuccess);
	}

	public void putValue(String key, Object value) {
		result.put(key, value);
	}

	public Object getValue(String key) {
		return result.get(key);
	}

	public static boolean isSuccess(final ResultVo result) {
		if (result == null)
			return false;
		if (null == result.result.get("success")) {
			return false;
		}
		return (boolean) result.result.get("success");
	}

	@Override
	public String toString() {
		return Json.ObjectMapper.writeValue(this);
	}
}