package com.zero.Interface;

import com.zero.entity.Entity;
import com.zero.entity.ExcelData_FOR_DM2MysqlDFGFService;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author shajingzhe
 * @date 2023/5/4 下午2:10
 */
public interface DemoIni {

	LinkedHashMap<String, Entity> transformDBTextSpitInfo2LinkedHashMap_O(String[] o_split);

	LinkedHashMap<String, Entity> transformDBTextSpitInfo2LinkedHashMap_T(String[] o_split);

	LinkedHashMap<String, String> transformMapTextSpitInfo2LinkedHashMap_M(
			List<ExcelData_FOR_DM2MysqlDFGFService> errorInfoList, String[] o_split);

	void dealData(List<ExcelData_FOR_DM2MysqlDFGFService> errorInfoList, LinkedHashMap<String, Entity> o_transformInfoMap,
			LinkedHashMap<String, Entity> t_transformInfoMap, String o_name, String t_name);

	void dealDataExtraAction(List<ExcelData_FOR_DM2MysqlDFGFService> errorInfoList, LinkedHashMap<String, Entity> o_transformInfoMap,
			LinkedHashMap<String, Entity> t_transformInfoMap, LinkedHashMap<String, String> m_transformInfoMap);
}
