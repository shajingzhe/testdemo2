package com.zero.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zero.entity.XFileInfo;
import com.zero.entity.XLSCreateConfigInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;

public class FileUtils {

	//文件输出位置
	private static String rootPath = "/data/workplace/临时文件/工具文件夹/DFGF/doc"; //请勿以"/"结尾，指出文件夹所在位置即可

	public static String fileRead(String fileAddress) throws Exception {
		File file = new File(fileAddress);//定义一个file对象，用来初始化FileReader
		FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
		BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
		StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
		String s = "";
		while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
			sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
		}
		bReader.close();
		String str = sb.toString();
		return str;
	}

	/**
	 * 创建xls表格
	 *
	 * @param objectList 所使用数据
	 * @param aliasMap   别名
	 * @return
	 * @throws IOException
	 */
	public static byte[] creatXls(List<Object> objectList, LinkedHashMap<String, String> aliasMap) throws IOException {
		OutputStream os = new ByteArrayOutputStream();
		ByteArrayOutputStream xlsDoc_ByteOS = null;
		// 通过工具类创建writer，默认创建xls格式
		ExcelWriter writer = ExcelUtil.getWriter();
		try {
			List<Object> rows = CollUtil.newArrayList(objectList);
			//自定义标题别名
			writer.setHeaderAlias(aliasMap);
			writer.setOnlyAlias(true);
			// 一次性写出内容，使用默认样式，强制输出标题
			writer.write(rows, true);
			//列宽度自动伸缩
			autoSetSizeColumn(aliasMap, writer);
			//out为OutputStream，需要写出到的目标流
			writer.flush(os);
			xlsDoc_ByteOS = (ByteArrayOutputStream) os;
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			throw exception;
		} finally {
			// 关闭writer，释放内存
			writer.close();
			os.close();
		}
		return xlsDoc_ByteOS != null ? xlsDoc_ByteOS.toByteArray() : null;
	}

	private static void autoSetSizeColumn(LinkedHashMap<String, String> aliasMap, ExcelWriter writer) {
		int columnNum = aliasMap.size();
		while (columnNum > 0) {
			columnNum--;
			writer.autoSizeColumn(columnNum);
		}
	}

	/**
	 * 创建xls表格（分表）
	 *
	 * @param xlsCreateConfigInfoList
	 * @return
	 * @throws IOException
	 */
	public static byte[] creatXls(List<XLSCreateConfigInfo> xlsCreateConfigInfoList) throws IOException {
		List<Object> rows = null;
		OutputStream os = new ByteArrayOutputStream();
		ByteArrayOutputStream xlsDoc_ByteOS = null;
		// 通过工具类创建writer，默认创建xls格式
		ExcelWriter writer = ExcelUtil.getWriter();
		try {
			if (xlsCreateConfigInfoList.size() > 0) {//第一页特殊处理
				writer.renameSheet(xlsCreateConfigInfoList.get(0).getSheetName());
				rows = CollUtil.newArrayList(xlsCreateConfigInfoList.get(0).getObjectList());
				LinkedHashMap<String, String> aliasMap=xlsCreateConfigInfoList.get(0).getAliasMap();
				//自定义标题别名
				writer.setHeaderAlias(aliasMap);
				//列宽度自动伸缩
				autoSetSizeColumn(aliasMap, writer);
				writer.setOnlyAlias(true);
				xlsCreateConfigInfoList.remove(0);
				// 一次性写出内容，使用默认样式，强制输出标题
				writer.write(rows, true);
			}
			//处理后续页面
			for (XLSCreateConfigInfo xlsCreateConfigInfo : xlsCreateConfigInfoList) {
				writer.setSheet(xlsCreateConfigInfo.getSheetName());
				rows = CollUtil.newArrayList(xlsCreateConfigInfo.getObjectList());
				LinkedHashMap<String, String> aliasMap=xlsCreateConfigInfo.getAliasMap();
				//自定义标题别名
				writer.setHeaderAlias(xlsCreateConfigInfo.getAliasMap());
				//列宽度自动伸缩
				autoSetSizeColumn(aliasMap, writer);
				writer.setOnlyAlias(true);
				// 一次性写出内容，使用默认样式，强制输出标题
				writer.write(rows, true);
			}
			writer.flush(os);
			xlsDoc_ByteOS = (ByteArrayOutputStream) os;
		} catch (Exception exception) {
			throw exception;
		} finally {
			// 关闭writer，释放内存
			writer.close();
			os.close();
		}
		return xlsDoc_ByteOS != null ? xlsDoc_ByteOS.toByteArray() : null;
	}


	/**
	 * 生成文件
	 *
	 * @param xfileInfo
	 * @param rootPath  //请勿以"/"结尾，指出文件夹所在位置即可
	 * @return 文件地址
	 * @throws IOException
	 */
	public static String uploadFile(XFileInfo xfileInfo, String rootPath) throws IOException {//todo s 需要考虑在catalog下另加一层小文件夹的情况，例如以日期为命名的文件夹
		String ID = StrUtils.getSnowflakeId();

		MultipartFile file = xfileInfo.getFile();

		String fileType = xfileInfo.getFileType();
		if (StrUtil.isEmpty(fileType)) {
			String fileName = xfileInfo.getFileName();
			if (StrUtil.isEmpty(fileName))
				fileName = StrUtil.isEmpty(file.getName()) ? file.getOriginalFilename() : file.getName();
			fileType = FileNameUtil.extName(fileName);
			if (StrUtil.isBlank(fileType)) {//todo s 限制文件可上传类型
				throw new RemoteException("未获取文件类型");
			}
			xfileInfo.setFileType(fileType);
		}

		String fileName = xfileInfo.getFileName();//此处待优化
		if (StringUtils.isBlank(fileName)) {
			xfileInfo.setFileName(ID + "." + fileType);
		} else {
			if (fileName.indexOf("/") > 0 || fileName.indexOf("\\") > 0) {
				throw new RemoteException("文件名中不可包含“/“或”\\“");
			}
		}

		String file_fullName = FileNameUtil.mainName(xfileInfo.getFileName()) + "_" + ID + "." + fileType;
		String filePath = rootPath + "/" + xfileInfo.getCatalog();
		File dirFile = new File(filePath);
		if (!dirFile.isDirectory())
			dirFile.mkdirs();
		File destFile = new File(filePath + "/" + file_fullName);
		while (destFile.exists())//如果目标文件已经存在，需要重新获取文件名
		{
			ID = StrUtils.getSnowflakeId();
			xfileInfo.set_ID(ID);
			file_fullName = FileNameUtil.mainName(xfileInfo.getFileName()) + "_" + ID + "." + fileType;
			destFile = new File(filePath, file_fullName);
		}
		//合并文件
		RandomAccessFile raFile = null;
		BufferedInputStream inputStream = null;
		try {
			//以读写的方式打开目标文件
			raFile = new RandomAccessFile(destFile, "rw");
			raFile.seek(raFile.length());
			inputStream = new BufferedInputStream(file.getInputStream());
			byte[] buf = new byte[102400];
			int length = 0;
			while ((length = inputStream.read(buf)) != -1) {
				raFile.write(buf, 0, length);
			}
		} catch (Exception e) {
			//log.info("上传出错:" + e.getMessage());
			throw new IOException(e.getMessage());
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (raFile != null) {
					raFile.close();
				}
			} catch (Exception e) {
				//log.info("上传出错:" + e.getMessage());
				throw new IOException(e.getMessage());
			}
		}
		return destFile.getPath();
	}

	/**
	 * 去除地址的结尾“/”符号
	 *
	 * @param addressPath
	 * @return
	 */
	public static String removeEndSymbol(String addressPath) {
		return StrUtil.endWith(addressPath, "/") ? StrUtil.split(addressPath, addressPath.length() - 1)[0] : addressPath;
	}
}
