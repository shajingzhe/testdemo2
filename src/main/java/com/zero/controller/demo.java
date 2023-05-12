package com.zero.controller;

import com.zero.modal.DemoService;
import com.zero.service.DemoIniImp;

/**
 * @author shajingzhe
 * @date 2023/5/4 下午2:44
 */
public class demo {

	public static void main(String[] args) {
		DemoService demoService=new DemoIniImp();
		demoService.parseSysParameterText("司法鉴定所");
	}

}
