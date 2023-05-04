package com.zero.controller;

import com.zero.modal.DemoService;
import com.zero.service.DemoServiceImp;

/**
 * @author shajingzhe
 * @date 2023/5/4 下午2:44
 */
public class demo {

	public static void main(String[] args) {
		DemoService demoService=new DemoServiceImp();
		demoService.parseSysParameterText("司法鉴定所");
	}

}
