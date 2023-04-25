package com.zero.Exception;

/**
 * @author shajingzhe
 * @date 2023/4/24 下午4:07
 */
public class InfoException extends RuntimeException {
	static final long serialVersionUID = -7034897190745766932L;

	public InfoException() {
	}

	public InfoException(String message) {
		super(message);
	}

	public InfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public InfoException(Throwable cause) {
		super(cause);
	}

	protected InfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
