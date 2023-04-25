package com.zero.Utils;

/**
 * @author shajingzhe
 * @date 2023/4/24 上午11:29
 */
public class ExcpUtils {
	public static void throwExpIfFalse(boolean hasLength, String symbol_access_empty) {
		if(!hasLength){
			throw new RuntimeException("symbol_access_empty");
		}
	}
}
