package com.zero.common;

import cn.hutool.core.util.StrUtil;
import com.zero.Exception.InfoException;
import com.zero.Utils.StrUtils;
import com.zero.Utils.Utils;
import com.zero.entity.Entity;
import com.zero.entity.ExcelData_FOR_DM2MysqlDFGFService;
import com.zero.entity.XFileInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author shajingzhe
 * @date 2023/4/23 下午5:28
 */
@Slf4j
public class dm2mysqlDFGFService {

	/**
	 * 地方公服dm转mysql数据对比工具
	 */
	@Test
	public void parseSysParameterText() {

		long start = System.currentTimeMillis();

		String o_dbText;//源数据文件内容
		String t_dbText;//目标数据文件内容
		String m_mapText;//映射数据文件内容
		String tableName = "律师1";
		List<ExcelData_FOR_DM2MysqlDFGFService> errorInfoList = new ArrayList<>();//错误信息集合
		String path = "/data/workplace/临时文件/工具文件夹/DFGF/" + tableName;
		log.info("loading....");
		try {
			o_dbText = Utils.fileRead(path + "/original");
			t_dbText = Utils.fileRead(path + "/target");
			m_mapText = Utils.fileRead(path + "/map");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// 源数据处理
		String[] o_split = StringUtils.split(o_dbText, "\n");
		LinkedHashMap<String, Entity> o_transformInfoMap = transformDBTextSpitInfo2LinkedHashMap(o_split,"源数据");
		//o_split=null;
		log.info("源数据字段数量: "+o_transformInfoMap.size());

		// 目标数据处理
		String[] t_split = StringUtils.split(t_dbText, "\n");
		LinkedHashMap<String, Entity> t_transformInfoMap = transformDBTextSpitInfo2LinkedHashMap(t_split,"目标数据");
		//t_split=null;
		log.info("目标数据字段数量: "+t_transformInfoMap.size());

		// 映射文件处理
		String[] m_split = StringUtils.split(m_mapText, "\n");
		LinkedHashMap<String, String> m_transformInfoMap = transformMapTextSpitInfo2LinkedHashMap(errorInfoList, m_split);
		//m_split=null;
		log.info("映射数量: "+m_transformInfoMap.size());


		log.info("数据加载完成,数据初步检查通过");

		//问题数据整合处理
		m_transformInfoMap.forEach((o_name, t_name) -> {
			ExcelData_FOR_DM2MysqlDFGFService e = new ExcelData_FOR_DM2MysqlDFGFService();
			//映射检查
			List<String> errorInfoList_ls = new ArrayList<>();
			if (!checkMapKVIsSimilar(o_name, t_name)) {
				errorInfoList_ls.add("映射名称不相同");
			}
			e.setMappingStr(o_name + " -> " + t_name);

			boolean findField = true;// 源与目标映射是否都找到对应的的字段(信号量)

			//源数据检查
			Entity o_entity = new Entity();
			boolean findSuccess = findFieldInfoByName(o_transformInfoMap, o_name, o_entity, errorInfoList_ls, "源");
			if (findSuccess) {
				e.setO_name(o_entity.getName());
				e.setO_displayName(o_entity.getDisplayName());
				e.setO_type(o_entity.getTyple());
			} else {
				findField = false;
			}

			//目标数据检查
			Entity t_entity = new Entity();
			findSuccess = findFieldInfoByName(t_transformInfoMap, t_name, t_entity, errorInfoList_ls, "目标");
			if (findSuccess) {
				e.setT_name(t_entity.getName());
				e.setT_displayName(t_entity.getDisplayName());
				e.setT_type(t_entity.getTyple());
			} else {
				findField = false;
			}

			if (!findField) {//未找全双方字段信息
				e.setErrorInfo(errorInfoList_ls.toString());
				errorInfoList.add(e);
				return;
			}
			if (!o_entity.getDisplayName().equals(t_entity.getDisplayName())) {
				errorInfoList_ls.add("源字段名描述与目标字段描述不一致");
			}
			if (!o_entity.getTyple().equals(t_entity.getTyple())) {
				errorInfoList_ls.add("源字段名描述与目标字段类别不一致");
			}
			if (errorInfoList_ls.size() == 0) {
				return;
			}
			e.setErrorInfo(errorInfoList_ls.toString());
			errorInfoList.add(e);
		});
		if (errorInfoList.size() == 0) {
			log.info("数据对比完成，未发现异常数据");
			long end = System.currentTimeMillis();
			log.info(String.format("Total Time：%d ms", end - start));
			return;
		}
		log.info("数据对比完成，发现异常数据,正在数据文档");

		//region 文件处理
		//xls处理
		LinkedHashMap<String, String> aliasMap = new LinkedHashMap<>();
		aliasMap.put("o_name", "源数据名称");
		aliasMap.put("o_displayName", "源数据显示名");
		aliasMap.put("o_type", "源数据字段类型");
		aliasMap.put("t_name", "目标数据名称");
		aliasMap.put("t_displayName", "目标数据显示名");
		aliasMap.put("t_type", "目标数据字段类型");
		aliasMap.put("mappingStr", "映射");
		aliasMap.put("errorInfo", "错误原因");
		List<Object> objectList = new ArrayList<>();
		objectList.addAll(errorInfoList);
		String filePath = "";
		try {
			byte[] fileBytes = Utils.creatXls(objectList, aliasMap);
			String fileName = "[" + tableName + "]数据迁移数据对比文档.xls";
			XFileInfo xfileInfo = new XFileInfo("系统工具生成文件");
			MultipartFile file = new MockMultipartFile(fileName, fileBytes);
			xfileInfo.setFileName(fileName);
			xfileInfo.setFile(file);
			filePath = Utils.uploadFile(xfileInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		log.info("文档生产完毕，文件地址：\n" + filePath);
		long end = System.currentTimeMillis();
		log.info(String.format("Total Time：%d ms", end - start));
		return;
		//endregion

	}

	/**
	 * 通过映射名找到 数据库中对应的字段，并赋值entity、errorInfoList_ls（本映射的错误信息)
	 *
	 * @param transformInfoMap
	 * @param name
	 * @param entity
	 * @param errorInfoList_ls
	 * @return 是否找到
	 */
	private boolean findFieldInfoByName(LinkedHashMap<String, Entity> transformInfoMap, String name,
			Entity entity, List<String> errorInfoList_ls, String sourceName) {
		if (StrUtil.isBlank(name)) {
			return false;
		}
		//字段名处理
		if (name.startsWith("P_")) {
			name = name.substring("P_".length());
		} else if (name.startsWith("p_")) {
			name = name.substring("p_".length());
		} else {
			throw new RuntimeException("异常字段: " + name);
		}
		name = StrUtils.underlineTransferSmallHump(name);

		String finalName = name;
		try {
			transformInfoMap.forEach((filedName, filedEntity) -> {
				if (!finalName.equalsIgnoreCase(filedName)) {
					return;
				}
				if (filedEntity == null) {
					throw new RuntimeException(sourceName + "数据检索异常");
				}
				entity.setName(filedName);
				entity.setDisplayName(filedEntity.getDisplayName());
				entity.setTyple(filedEntity.getTyple());
				throw new InfoException();
			});
		} catch (InfoException infoException) {
			return true;
		}
		errorInfoList_ls.add(sourceName + "映射名称未找到对应字段");
		return false;
	}

	/**
	 * 检查 映射数据 名称是否相同/相似
	 *
	 * @param o_name
	 * @param t_name
	 * @return
	 */
	private boolean checkMapKVIsSimilar(String o_name, String t_name) {
		boolean equal = StrUtil.equalsAnyIgnoreCase(o_name, t_name);
		return equal;
	}

	private boolean isSystemField(String name) {
		if (name.startsWith("P_") || name.startsWith("p_")) {
			return false;
		}
		return true;
	}

	/**
	 * 对切割完的 源/目标 数据库文件数据进行处理
	 *
	 * @param splitInfo
	 * @return 字段名，字段信息（字段显示名，字段类型）
	 */
	private LinkedHashMap<String, Entity> transformDBTextSpitInfo2LinkedHashMap(String[] splitInfo,String sourceName) {
		LinkedHashMap<String, Entity> linkedHashMap = new LinkedHashMap<>();
		String o_name = null;
		for (int i = 0; i < splitInfo.length; ) {
			Entity o_entity = new Entity();
			//每三个进行一次性循环
			for (int m = 1; m < 4; m++) {
				if (m == 1) {
					o_name = splitInfo[i].replaceAll(" ", "");//去除空格;
					if(StrUtils.containChinese(o_name)){
						throw new RuntimeException(sourceName+"排版异常:"+o_name+"["+o_name+"]"+"["+i+"]");
					}
				} else if (m == 2) {
					o_entity.setDisplayName(splitInfo[i]);
					if(!StrUtils.containChinese(splitInfo[i])){
						throw new RuntimeException(sourceName+"排版异常:"+o_name+"["+splitInfo[i]+"]"+"["+i+"]");
					}
				} else if (m == 3) {
					o_entity.setTyple(splitInfo[i]);
					if(!"UUID".equalsIgnoreCase(splitInfo[i])&&!StrUtils.containChinese(splitInfo[i])){
						throw new RuntimeException(sourceName+"排版异常:"+o_name+"["+splitInfo[i]+"]"+"["+i+"]");
					}
				} else {
					throw new RuntimeException("异常数据");
				}
				i++;
				if (i >= splitInfo.length) {
					break;
				}
			}
			linkedHashMap.put(o_name, o_entity);
		}
		return linkedHashMap;
	}

	/**
	 * 对切割完的 映射 文件数据进行处理
	 *
	 * @param splitInfo
	 * @return 源字段名，目标字段名
	 */
	private LinkedHashMap<String, String> transformMapTextSpitInfo2LinkedHashMap(List<ExcelData_FOR_DM2MysqlDFGFService> errorInfoList,
			String[] splitInfo) {
		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
		String o_name = null;
		String t_name = null;
		HashSet<String> o_nameSet = new HashSet<>();
		HashSet<String> t_nameSet = new HashSet<>();
		int systemFieldNum = 0;
		for (int i = 0; i < splitInfo.length; ) {
			//每2个进行一次性循环
			for (int m = 1; m < 3; m++) {
				if (m == 1) {
					t_name = splitInfo[i].replaceAll(" ", "");//去除空格
				} else if (m == 2) {
					o_name = splitInfo[i].replaceAll(" ", "");//去除空格
				} else {
					throw new RuntimeException("异常数据");
				}
				i++;
				if (i >= splitInfo.length) {
					break;
				}
			}
			if (StrUtil.isBlank(o_name) || StrUtil.isBlank(t_name)) {
				log.error(o_name + " -> " + t_name);
				throw new RuntimeException("映射数据异常");
			}
			if (!o_nameSet.add(o_name.toLowerCase())) {
				ExcelData_FOR_DM2MysqlDFGFService e = new ExcelData_FOR_DM2MysqlDFGFService();
				e.setMappingStr(o_name + " -> ");
				e.setErrorInfo("源数据字段名重复");
				errorInfoList.add(e);
			}
			if (!t_nameSet.add(t_name.toLowerCase())) {
				ExcelData_FOR_DM2MysqlDFGFService e = new ExcelData_FOR_DM2MysqlDFGFService();
				e.setMappingStr(" -> " + t_name);
				e.setErrorInfo("目标数据字段名重复");
				errorInfoList.add(e);
			}
			//除去系统字段
			if (isSystemField(o_name) || isSystemField(t_name)) {
				log.info("系统字段： " + o_name + " -> " + t_name);
				systemFieldNum++;
				continue;
			}
			linkedHashMap.put(o_name, t_name);
		}
		log.info("系统字段合计数量：" + systemFieldNum + " ; " + (systemFieldNum == 10));
		return linkedHashMap;
	}
}
