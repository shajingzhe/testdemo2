package com.zero.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.zero.utils.FileUtils;
import com.zero.utils.LevenshteinUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author shajingzhe
 * @Date 2023/7/21 下午3:36
 */
@Slf4j
public class CheckDataService {
	Map<String, List<String>> communityMap = new HashMap<>();
	HashSet<String> communityHashSet = new HashSet<>();
	List<Model> resultList = new ArrayList<>();

	public static void main(String[] args) {
	}

	@Test
	public void check() {
		initCommunityMap();

		ExcelReader reader1 = ExcelUtil.getReader("/home/homolo/桌面/文档/测试文档/XLS 工作表.xls");
		List<Map<String, Object>> readAll1 = reader1.readAll();
		String resultValue;
		for (Map<String, Object> map : readAll1) {
			String juCunName = MapUtil.getStr(map, "居村社区");
			if (StringUtils.isBlank(juCunName)) {
				Model model = new Model();
				model.setOriginal("");
				model.setInfer("");
				model.setSimilarity(1);
				resultList.add(model);
				continue;
			}
			resultValue = "";
			float levenshteinMax = 0;
			String zhengName = MapUtil.getStr(map, "街镇乡");
			List<String> communityNameList = communityMap.get(zhengName);
			if (communityNameList == null) {
				log.error("异常街镇乡数据:" + zhengName);
				//全表扫描
				Iterator<List<String>> iterator = communityMap.values().iterator();
				while (iterator.hasNext()) {
					List<String> value = iterator.next();
					for (String communityName : value) {
						float levenshtein = LevenshteinUtils.levenshtein(juCunName, communityName);
						if (levenshtein > levenshteinMax) {
							levenshteinMax = levenshtein;
							resultValue = communityName;
						}
					}
				}

			} else {
				//局部扫描 todo s 抽出
				for (String communityName : communityNameList) {
					float levenshtein = LevenshteinUtils.levenshtein(juCunName, communityName);
					if (levenshtein > levenshteinMax) {
						levenshteinMax = levenshtein;
						resultValue = communityName;
					}
				}
			}
			Model model = new Model();
			model.setOriginal(juCunName);
			model.setInfer(resultValue);
			model.setSimilarity(levenshteinMax);
			resultList.add(model);
		}

		LinkedHashMap<String, String> aliasMap = new LinkedHashMap<>();
		aliasMap.put("original", "源数据");
		aliasMap.put("infer", "推测数据");
		aliasMap.put("similarity", "相似度");
		List<Object> objectList = new ArrayList<>();
		objectList.addAll(resultList);
		FileUtils.generateXls(aliasMap, objectList, "社区信息推测(2.2).xls", "/home/homolo/桌面/文档/测试文档");
	}

	/**
	 * 初始化社区信息
	 */
	private void initCommunityMap() {
		ExcelReader reader1 = ExcelUtil.getReader("/home/homolo/桌面/文档/测试文档/ 社区信息.xls");
		List<Map<String, Object>> readAll1 = reader1.readAll();
		for (Map<String, Object> map : readAll1) {
			String zhengName = MapUtil.getStr(map, "街镇名称");
			if (communityHashSet.add(zhengName)) {
				List<String> list = new ArrayList<>();
				String juCunName = MapUtil.getStr(map, "居村名称");
				list.add(juCunName);
				communityMap.put(zhengName, list);
			} else {
				List<String> list = communityMap.get(zhengName);
				String juCunName = MapUtil.getStr(map, "居村名称");
				list.add(juCunName);
			}
		}
	}

	@Data
	private class Model {
		String original;
		String infer;
		float similarity;
	}
}
