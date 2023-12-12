package Think;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * 数据写入文件.
 */
public class FileReadWriteExample {

	public static void writeToFile(String content, String filePath) {
		try (FileWriter writer = new FileWriter(filePath)) {
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFromFile(String filePath) {
		StringBuilder content = new StringBuilder();
		try (FileReader reader = new FileReader(filePath)) {
			int character;
			while ((character = reader.read()) != -1) {
				content.append((char) character);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	public static void main(String[] args) throws JsonProcessingException {


		HashMap<String, String> personMap = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			personMap.put(String.valueOf(i),"156");
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(personMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String filePath = "/home/homolo/桌面/文档/临时/file.txt";

		// 写入文件
		writeToFile(jsonString, filePath);

		// 从文件读取内容
		String contentRead = readFromFile(filePath);
		HashMap<String,String> personFromJson = objectMapper.readValue(jsonString, HashMap.class);

		System.out.println("Content read from file: " + contentRead);
	}
}
