package com.crud.demo.utils;

public class ResultUtils {
	public static Result Success() {
		return new Result(1,"成功");
	}
	public static Result Success(String msg) {
		return new Result(1,msg);
	}
	public static Result Error() {
		return new Result(-1,"失败");
	}
}
