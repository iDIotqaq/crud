package com.crud.demo.utils;

import java.io.Serializable;

import lombok.Data;

@Data
public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8735555716171007361L;
	
	private int code;
	private Object data;
	private String msg;
	public Result(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public Result(int code,String msg, Object data) {
		super();
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

}
