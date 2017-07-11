package com.powerplay.exception;

public class PublishException extends Exception {

	private static final long serialVersionUID = -2422764500612445109L;

	public PublishException(String msg) {
		super(msg);
	}

	public PublishException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
