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
 * 居村委员会双方数据对比
 *
 * @Author shajingzhe
 * @Date 2023/7/21 下午3:36
 */
@Slf4j
public class CheckDataService {
	Map<String, List<String>> communityMap = new HashMap<>();
	HashSet<String> communityHashSet = new HashSet<>();
	List<Model> resultList = new ArrayList<>();
	HashSet<String> errorNameSet = new HashSet<>();
	String version = "2.3.3";


	public static void main(String[] args) {
	}

	@Test
	public void check() {
		// 代码运行开始时间
		Long startTime = System.currentTimeMillis();

		initCommunityMap();

		ExcelReader reader1 = ExcelUtil.getReader("/home/homolo/桌面/文档/测试文档/XLS 工作表.xls");
		List<Map<String, Object>> readAll1 = reader1.readAll();
		String resultValue;
		for (Map<String, Object> map : readAll1) {
			String juCunName = MapUtil.getStr(map, "居村社区");
			String orginalZhengName = MapUtil.getStr(map, "街镇乡");
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
			String errorInfo = "";
			String findZhengName = "";
			String zhengName = MapUtil.getStr(map, "街镇乡");
			List<String> communityNameList = communityMap.get(zhengName);
			if (communityNameList == null) {
				if (errorNameSet.add(zhengName)) {
					log.error("异常街镇乡数据:" + zhengName);
				}

//				Iterator<Object> iterator = map.keySet().iterator();
//				while (iterator.hasNext()) {
//					Object key = iterator.next();
//					System.out.println("key:" + key + ",vaule:" + map.get(key));
//				}

				//全表扫描
				//Iterator<List<String>> iterator = communityMap.values().iterator();
				Iterator<String> iterator = communityMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					List<String> value = communityMap.get(key);
					for (String communityName : value) {
						float levenshtein = LevenshteinUtils.levenshtein(juCunName, communityName);
						if (levenshtein > levenshteinMax) {
							levenshteinMax = levenshtein;
							resultValue = communityName;
							findZhengName = key;
						}
					}
				}
				errorInfo = "未找到对应街道，全表查找";


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
			model.setOrginalZhengName(orginalZhengName);
			if (!StringUtils.isBlank(errorInfo)) {
				model.setErrorInfo(errorInfo);
				model.setFindZhengName(findZhengName);
			}
			resultList.add(model);
		}

		System.out.println(StringUtils.join(errorNameSet, "、"));
		LinkedHashMap<String, String> aliasMap = new LinkedHashMap<>();
		aliasMap.put("original", "源数据");
		aliasMap.put("infer", "推测数据");
		aliasMap.put("similarity", "相似度");
		aliasMap.put("errorInfo", "异常信息");
		aliasMap.put("orginalZhengName", "原街道名称");
		aliasMap.put("findZhengName", "查找街道名称");
		List<Object> objectList = new ArrayList<>();
		objectList.addAll(resultList);
		FileUtils.generateXls(aliasMap, objectList, "社区信息推测(" + version + ").xls", "/home/homolo/桌面/文档/测试文档");
		// 代码运行结束时间
		Long endTime = System.currentTimeMillis();
		// 打印说明
		System.out.println("程序执行时长"+(endTime-startTime));
	}

	/**
	 * 初始化社区信息
	 */
	private void initCommunityMap() {
		ExcelReader reader1 = ExcelUtil.getReader("/home/homolo/桌面/文档/测试文档/居村委信息-社区云提供.xls");
		List<Map<String, Object>> readAll1 = reader1.readAll();
		for (Map<String, Object> map : readAll1) {
			String zhengName = MapUtil.getStr(map, "街道");
			if (communityHashSet.add(zhengName)) {
				List<String> list = new ArrayList<>();
				String juCunName = MapUtil.getStr(map, "居村委");
				list.add(juCunName);
				communityMap.put(zhengName, list);
			} else {
				List<String> list = communityMap.get(zhengName);
				String juCunName = MapUtil.getStr(map, "居村委");
				list.add(juCunName);
			}
		}
	}

	@Data
	private class Model {
		String original;
		String infer;
		float similarity;
		String errorInfo;
		String findZhengName;
		String orginalZhengName;
	}
}
