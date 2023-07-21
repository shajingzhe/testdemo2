package com.zero.controller;

import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.zero.utils.FileUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @Author shajingzhe
 * @Date 2023/7/19 上午10:53
 */
public class JGXLSService {

	public static void main(String[] args) throws Exception {

		ExcelReader reader = ExcelUtil.getReader("/home/homolo/桌面/文档/测试文档/XLS 工作表.xls");
		List<Map<String, Object>> readAll = reader.readAll();
		ExcelReader reader1 = ExcelUtil.getReader("/home/homolo/桌面/文档/测试文档/ 社区信息.xls");
		List<Map<String, Object>> readAll1 = reader1.readAll();
		String a = "1";
	}
}
