package com.zero.modal;

import com.zero.Interface.DemoIni;
import com.zero.utils.FileUtils;
import com.zero.entity.Entity;
import com.zero.entity.ExcelData4DataMigration;
import com.zero.entity.XFileInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author shajingzhe
 * @date 2023/5/4 上午11:36
 */
@Slf4j
public abstract class DemoService implements DemoIni {

	//原数据文件地址
	private String folderPath;

	//输出xls标题
	private LinkedHashMap<String, String> aliasMap;

	//下载文件夹
	private String rootPath_downFile;

	protected DemoService(String folderPath, LinkedHashMap<String, String> aliasMap, String rootPath_downFile) {
		this.folderPath = folderPath;
		this.aliasMap = aliasMap;
		this.rootPath_downFile = rootPath_downFile;
	}

	/**
	 * 地方公服dm转mysql数据对比工具
	 */
	public void parseSysParameterText(String tableName) {//"司法鉴定所"

		long start = System.currentTimeMillis();

		String o_dbText;//源数据文件内容
		String t_dbText;//目标数据文件内容
		String m_mapText;//映射数据文件内容

		List<ExcelData4DataMigration> errorInfoList = new ArrayList<>();//错误信息集合
		folderPath = FileUtils.removeEndSymbol(folderPath);
		String path = folderPath + "/" + tableName;
		log.info("loading....");
		try {
			o_dbText = FileUtils.fileRead(path + "/original");
			t_dbText = FileUtils.fileRead(path + "/target");
			m_mapText = FileUtils.fileRead(path + "/map");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// 源数据处理
		LinkedHashMap<String, Entity> o_transformInfoMap = transformDBTextSpitInfo2LinkedHashMap_O(o_dbText);
		log.info("源数据字段数量: " + o_transformInfoMap.size());

		// 目标数据处理
		LinkedHashMap<String, Entity> t_transformInfoMap = transformDBTextSpitInfo2LinkedHashMap_T(t_dbText);
		log.info("目标数据字段数量: " + t_transformInfoMap.size());

		// 映射文件处理
		LinkedHashMap<String, String> m_transformInfoMap = transformMapTextSpitInfo2LinkedHashMap_M(errorInfoList, m_mapText);
		log.info(" 非系统字段映射数量: " + m_transformInfoMap.size());


		log.info("数据加载完成,数据初步检查通过");

		//问题数据整合处理
		m_transformInfoMap.forEach((o_name, t_name) -> {
			 dealData(errorInfoList, o_transformInfoMap, t_transformInfoMap, o_name, t_name);
		});
		if (errorInfoList.size() == 0) {
			log.info("数据对比完成，未发现异常数据");
			long end = System.currentTimeMillis();
			log.info(String.format("Total Time：%d ms", end - start));
			return;
		}
		log.info("数据对比完成，发现异常数据,正在数据文档");

		dealDataExtraAction(errorInfoList, o_transformInfoMap, t_transformInfoMap, m_transformInfoMap);

		//region 文件处理
		//xls处理
		List<Object> objectList = new ArrayList<>();
		objectList.addAll(errorInfoList);
		String filePath = "";
		try {
			byte[] fileBytes = FileUtils.creatXls(objectList, aliasMap);
			String fileName = "[" + tableName + "]数据迁移数据对比文档.xls";
			XFileInfo xfileInfo = new XFileInfo("系统工具生成文件");
			MultipartFile file = new MockMultipartFile(fileName, fileBytes);
			xfileInfo.setFileName(fileName);
			xfileInfo.setFile(file);
			filePath = FileUtils.uploadFile(xfileInfo, rootPath_downFile);
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
}
