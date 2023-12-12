package com.zero.utils.thread;

import com.zero.utils.SerializableRunnable;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Author shajingzhe
 * @Date 2023/11/1 上午10:55
 */
@Slf4j
public class ThreadUtils {

	//对象转String
	public static SerializableRunnable transformStr2Obj(String storyStr) throws IOException, ClassNotFoundException {
		byte[] storyBT = storyStr.getBytes("ISO-8859-1");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(storyBT);
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		SerializableRunnable runnable = (SerializableRunnable) objectInputStream.readObject();
		return runnable;
	}

	//String转对象
	public static String transformObj2Str(SerializableRunnable serializableRunnable) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		// 创建对象输出流，将对象序列化到字节数组输出流
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

		// 序列化对象
		objectOutputStream.writeObject(serializableRunnable);

		// 获取序列化后的字节数组
		byte[] serializedData = byteArrayOutputStream.toByteArray();

		String storyStr = new String(serializedData, "ISO-8859-1");

		return storyStr;
	}
}
