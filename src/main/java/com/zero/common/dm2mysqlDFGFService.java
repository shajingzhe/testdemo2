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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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

		String o_dbText;//源数据文件内容
		String t_dbText;//目标数据文件内容
		String m_mapText;//映射数据文件内容
		List<ExcelData_FOR_DM2MysqlDFGFService> errorInfoList = new ArrayList<>();//错误信息集合
		String path = "/data/workplace/临时文件/工具文件夹/DFGF/" + "律师";
		try {
			o_dbText = Utils.fileRead(path + "/original");
			t_dbText = Utils.fileRead(path + "/target");
			m_mapText = Utils.fileRead(path + "/mapping");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// 源数据处理
		String[] o_split = StringUtils.split(o_dbText, "\n");
		LinkedHashMap<String, Entity> o_transformInfoMap = transformDBTextSpitInfo2LinkedHashMap(o_split);
		//o_split=null;

		// 目标数据处理
		String[] t_split = StringUtils.split(t_dbText, "\n");
		LinkedHashMap<String, Entity> t_transformInfoMap = transformDBTextSpitInfo2LinkedHashMap(t_split);
		//t_split=null;

		// 映射文件处理
		String[] m_split = StringUtils.split(m_mapText, "\n");
		LinkedHashMap<String, String> m_transformInfoMap = transformMapTextSpitInfo2LinkedHashMap(m_split);
		//m_split=null;

		Set<String> o_keySet = o_transformInfoMap.keySet();
		Set<String> t_keySet = t_transformInfoMap.keySet();

		//问题数据整合处理
		m_transformInfoMap.forEach((o_name, t_name) -> {
			ExcelData_FOR_DM2MysqlDFGFService e = new ExcelData_FOR_DM2MysqlDFGFService();
			//映射检查
			List<String> errorInfoList_ls = new ArrayList<>();
			if (!checkMapKVIsSimilar(o_name, t_name)) {
				errorInfoList_ls.add("映射名称不相同");
			}
			e.setMappingStr(o_name + " -> " + t_name);
			e.setO_name(o_name);
			e.setT_name(t_name);

			boolean findField = true;// 源与目标映射是否都找到对应的的字段(信号量)

			//源数据检查
			String o_displayName = null;
			String o_type = null;
			if (!checkIsSimilar(o_keySet, o_name)) {
				errorInfoList_ls.add("源映射名称未找到对应字段");
				findField = false;
			} else {
				Entity o_entity = o_transformInfoMap.get(o_name);
				if (o_entity != null) {
					o_displayName = o_entity.getDisplayName();
					o_type = o_entity.getTyple();
					e.setO_displayName(o_displayName);
					e.setO_type(o_type);
				}
			}

			//目标数据检查
			String t_displayName = null;
			String t_type = null;
			if (!checkIsSimilar(t_keySet, t_name)) {
				errorInfoList_ls.add("目标映射名称未找到对应字段");
				findField = false;
			} else {
				Entity t_entity = t_transformInfoMap.get(t_name);
				if (t_entity != null) {
					t_displayName = t_entity.getDisplayName();
					t_type = t_entity.getTyple();
					e.setO_displayName(t_displayName);
					e.setO_type(t_type);
				}
			}

			if (!findField) {
				return;
			}
			if (o_displayName.equals(t_displayName)) {
				errorInfoList_ls.add("源字段名描述与目标字段描述不一致");
			}
			if (o_type.equals(o_type)) {
				errorInfoList_ls.add("源字段名描述与目标字段类别不一致");
			}

			if (errorInfoList_ls.size() != 0) {
				e.setMappingStr(errorInfoList_ls.toString());
				errorInfoList.add(e);
			}

		});


		//xls处理
		LinkedHashMap<String, String> aliasMap = new LinkedHashMap<>();
		aliasMap.put("o_name", "源数据名称");
		aliasMap.put("o_displayName", "源数据显示名");
		aliasMap.put("o_type", "源数据字段类型");
		aliasMap.put("t_name", "，目标数据名称");
		aliasMap.put("t_displayName", "目标数据显示名");
		aliasMap.put("t_type", "目标数据字段类型");
		aliasMap.put("mappingStr", "映射");
		aliasMap.put("error", "错误原因");
		List<Object> objectList = new ArrayList<>();
		objectList.addAll(errorInfoList);

		try {
			byte[] fileBytes = Utils.creatXls(objectList, aliasMap);
			String fileName = "数据迁移数据对比文档.xls";
			XFileInfo xfileInfo = new XFileInfo("系统工具生成文件");
			MultipartFile file = new MockMultipartFile(fileName, fileBytes);
			xfileInfo.setFileName(fileName);
			xfileInfo.setFile(file);
			Utils.uploadFile(xfileInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println("生成成功");
		return;

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

	/**
	 * 检查名称是否有相同/相似
	 *
	 * @param set  对比数据集合
	 * @param name 需对比数据
	 * @return
	 */
	private boolean checkIsSimilar(Set<String> set, String name) {
		if (name == null) {
			return false;
		}
		if (name.startsWith("P_")) {
			name.substring("P_".length());
		} else if (name.startsWith("p_")) {
			name.substring("p_".length());
		} else {
			throw new RuntimeException("异常字段: " + name);
		}
		name = StrUtils.underlineTransferSmallHump(name);
		try {
			String finalName = name;
			set.forEach(fieldName -> {
				if (finalName.equalsIgnoreCase(fieldName)) {
					throw new InfoException();
				}
			});
		} catch (InfoException e) {
			return true;
		}
		return false;
	}

	/**
	 * 对切割完的映射文件数据进行处理，除去系统字段
	 *
	 * @param errorInfoList
	 * @param m_split
	 * @return
	 */
	private LinkedHashMap<String, String> transformMapTextSpitInfo2LinkedHashMap(List<ExcelData_FOR_DM2MysqlDFGFService> errorInfoList,
			String[] m_split) {
		LinkedHashMap<String, String> m_transformInfo = new LinkedHashMap<>();
		ArrayList<String> arrayList = new ArrayList(Arrays.asList(m_split));
		String o_name = null;
		String t_name = null;
		for (int i = 0; i < arrayList.size(); ) {
			String mapInfo = arrayList.get(i);
			String[] split = StringUtils.split(mapInfo, " -> ");

			//异常数据处理
			if (split.length != 2) {
				ExcelData_FOR_DM2MysqlDFGFService e = new ExcelData_FOR_DM2MysqlDFGFService();
				e.setErrorInfo("映射文件拆分数量异常: " + mapInfo + String.format("[%s]", i + 1));
				errorInfoList.add(e);
				arrayList.remove(i);
				continue;
			}
			o_name = split[0];
			t_name = split[1];
			if (StringUtils.isBlank(o_name) || StringUtils.isBlank(t_name)) {
				ExcelData_FOR_DM2MysqlDFGFService e = new ExcelData_FOR_DM2MysqlDFGFService();
				e.setMappingStr(mapInfo);
				e.setErrorInfo("映射文件拆分内容异常: " + mapInfo + String.format("[%s]", i + 1));
				errorInfoList.add(e);
				arrayList.remove(i);
				continue;
			}

			//除去系统字段
			if (isSystemField(o_name) || isSystemField(t_name)) {
				arrayList.remove(i);
				log.info(o_name + " -> " + t_name);
				continue;
			}

			m_transformInfo.put(o_name, t_name);
			i++;
		}
		return m_transformInfo;
	}

	private boolean isSystemField(String name) {
		if (name == null) {
			throw new RuntimeException("NPE");
		}
		if (name.startsWith("P_") || name.startsWith("p_")) {
			return true;
		}
		return false;
	}

	/**
	 * 对切割完的 源/目标 数据库文件数据进行处理
	 *
	 * @param splitInfo
	 * @return 字段名，字段信息（字段显示名，字段类型）
	 */
	private LinkedHashMap<String, Entity> transformDBTextSpitInfo2LinkedHashMap(String[] splitInfo) {
		LinkedHashMap<String, Entity> linkedHashMap = new LinkedHashMap<>();
		String o_name = null;
		Entity o_entity = new Entity();
		for (int i = 0; i < splitInfo.length; ) {
			//每三个进行一次性循环
			for (int m = 1; m < 4; m++) {
				switch (m) {
					case 1:
						o_name = splitInfo[i];
						break;
					case 2:
						o_entity.setDisplayName(splitInfo[i]);
						break;
					case 3:
						o_entity.setTyple(splitInfo[i]);
						break;
					default:
						throw new RuntimeException("异常数据");
				}
				i++;
				if (i < splitInfo.length) {
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
	private LinkedHashMap<String, String> transformMapTextSpitInfo2LinkedHashMap(String[] splitInfo) {
		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
		String o_name = null;
		String t_name = null;
		for (int i = 0; i < splitInfo.length; ) {
			//每2个进行一次性循环
			for (int m = 1; m < 3; m++) {
				switch (m) {
					case 1:
						o_name = splitInfo[i];
						break;
					case 2:
						t_name = splitInfo[i];
						break;
					default:
						throw new RuntimeException("异常数据");
				}
				i++;
				if (i < splitInfo.length) {
					break;
				}
			}
			//除去系统字段
			if (isSystemField(o_name) || isSystemField(t_name)) {
				log.info(o_name + " -> " + t_name);
			}
			linkedHashMap.put(o_name, t_name);
		}
		return linkedHashMap;
	}
}
