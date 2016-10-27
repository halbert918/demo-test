package com.test.ddd.facade.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public enum ResultEnum {
	
	/** 网络异常 **/
	NETWORK_EXCEPTION("NETWORK_EXCEPTION", "网络异常", "comn_02_0000"),
	/** 未知异常 */
	UN_KNOWN_EXCEPTION("UN_KNOWN_EXCEPTION", "未知异常", "comn_04_0000"),
	/** 执行成功 */
	EXECUTE_SUCCESS("EXECUTE_SUCCESS", "执行成功", "comn_04_0001")
	;
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/** 错误码 */
	private final String errorCode;
	
	/**
	 * 构造一个<code>ResultEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 * @param errorCode
	 */
	private ResultEnum(String code, String message, String errorCode) {
		this.code = code;
		this.message = message;
		this.errorCode = errorCode;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return Returns the errorCode.
	 */
	public String getErrorCode() {
		return errorCode;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}
	
	/**
	 * @return Returns the errorCode.
	 */
	public String errorCode() {
		return errorCode;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return ResultEnum
	 */
	public static ResultEnum getByCode(String code) {
		for (ResultEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 通过枚举<code>errorCode</code>获得枚举
	 * 
	 * @param errorCode
	 * @return ResultEnum
	 */
	public static ResultEnum getByErrorCode(String errorCode) {
		for (ResultEnum _enum : values()) {
			if (_enum.getErrorCode().equals(errorCode)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ResultEnum>
	 */
	public List<ResultEnum> getAllEnum() {
		List<ResultEnum> list = new ArrayList<ResultEnum>();
		for (ResultEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (ResultEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
