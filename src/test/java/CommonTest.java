import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zero.entity.Entity;
import com.zero.entity.EntityInfo;
import com.zero.exception.InfoException;
import com.zero.utils.SerializableRunnable;
import com.zero.utils.StrUtils;
import com.zero.utils.FileUtils;
import com.zero.utils.thread.ThreadPoolExecutorFactory;
import com.zero.utils.thread.ThreadUtils;
import entity.Person;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.util.RamUsageEstimator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

@Slf4j
public class CommonTest {


	private volatile static int i = 1;
	private static Object resource = new Object();
	static boolean run = true;

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		for (int i1 = 0; i1 < 10; i1++) {
			System.out.println(StrUtils.generateUUID());
		}

	}

	//内存大小测试
	private static void m36() {
		HashMap<String,String> hashMap=new HashMap<>();
		for (int j = 0; j < 24000000; j++) {
			hashMap.put(String.valueOf(UUID.randomUUID()),"value"+j);
		}
		System.out.println("map size 100, value is " + RamUsageEstimator.humanSizeOf(hashMap));
	}

	//遍历移除
	private static void m35() {
		List<String> dataList = new ArrayList<>();
		dataList.add("Value1");
		dataList.add("Value2");
		dataList.add("Value3");

		// 使用迭代器循环遍历列表
		Iterator<String> iterator = dataList.iterator();
		while (iterator.hasNext()) {
			String value = iterator.next();
			iterator.remove();// 移除当前元素
			System.out.println(value);
		}
		log.info("1");
	}

	private static void m34() {
		Map<String, String> map = new HashMap<>();
		System.out.println("map init value is " + RamUsageEstimator.sizeOf(map));
		for (int i = 0; i < 100; i++) {
			RandomStringUtils.randomAlphanumeric(100);
			map.put(RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10));
		}
		String primaryMediator = map.get(null);
		log.info(primaryMediator);
	}

	private static void n33() {
		Map<String, String> map = new HashMap<>();
		System.out.println("map init value is " + RamUsageEstimator.sizeOf(map));
		for (int i = 0; i < 100; i++) {
			RandomStringUtils.randomAlphanumeric(100);
			map.put(RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10));
		}
		System.out.println("map size 100, value is " + RamUsageEstimator.sizeOf(map));
		System.out.println("map size 100, value is " + RamUsageEstimator.humanSizeOf(map));
	}

	private static void m32() throws JsonProcessingException {
		// 假设你的 JSON 字符串是一个 PageImpl 对象
		String jsonString = "{\"content\":[{\"name\":\"John\",\"age\":30}],\"number\":1,\"size\":10,\"totalElements\":1,\"totalPages\":1}";

		// 使用 ObjectMapper 进行反序列化
		ObjectMapper objectMapper = new ObjectMapper();
		Page<Person> pageFromJson = objectMapper.readValue(jsonString, new TypeReference<PageImpl<Person>>() {
		});

		// 输出反序列化后的内容
		System.out.println("Number: " + pageFromJson.getNumber());
		System.out.println("Size: " + pageFromJson.getSize());
		System.out.println("Total Elements: " + pageFromJson.getTotalElements());
		System.out.println("Total Pages: " + pageFromJson.getTotalPages());
		System.out.println("Content: " + pageFromJson.getContent());
	}

	private static void m31() {
		HashMap<String, String> map = new HashMap<>();
		map.put(null, "123");
		System.out.println("执行成功");
		String s = map.get(null);

		System.out.println(s);
	}

	private static void m30() throws IOException, ClassNotFoundException {
		SerializableRunnable runnable = () -> log.info("运行成功!");

		String storyStr = ThreadUtils.transformObj2Str(runnable);
		SerializableRunnable runnable1 = ThreadUtils.transformStr2Obj(storyStr);

		ThreadPoolExecutorFactory.executeCommon(runnable1);
	}


	//身份证
	private static void m29() {
		boolean validCard = IdcardUtil.isValidCard("320582199812157917");
		Date date = null;
		String format = DateUtil.format(date, "yyyy-MM-dd");
		System.out.println(format);
	}

	private static void m28() {
		ConcurrentHashMap<String, String> errorInfoHashMap = new ConcurrentHashMap<>();
		for (int i = 0; i < 10; i++) {
			errorInfoHashMap.put(String.valueOf(i), String.valueOf(i));
		}
		log.info(errorInfoHashMap.keySet().toString());
	}

	//通货膨胀
	private static void m27() {
		double a = 1;
		for (int i = 0; i < 5; i++) {
			a = a * 1.08;
		}
		System.out.println(a);
	}

	private static void m26() {
		LinkedList<String> oldList = new LinkedList<>();
		oldList.add("1");
		oldList.add("2");
		oldList.add("3");
		oldList.add("4");
		oldList.add("sd");
		LinkedList<String> newList = new LinkedList<>(oldList);
		LinkedList<String> aList = null;
		LinkedList<String> bList = null;

		if ((aList == null || bList == null) && aList != bList) {
			boolean w = true;
		}
		boolean a = CollectionUtils.containsAll(oldList, newList);
		System.out.println(a);
	}

	private static void m25() {
		for (int i = 1; i <= 10; i++) {
			String uuid = StrUtil.uuid();
			uuid = uuid.replaceAll("-", "");
			System.out.println(uuid);
		}
		String a = "a813d30a2d7746c18a314ead9a2e1ecf";
		String b = "86edc619e30e41639d97f638fa2cf894";
		System.out.println(a.length());
		System.out.println(b.length());
	}

	private static void m24() {
		log.info("开始");
		HashMap<String, Object> hashMap = new HashMap<>();
		for (int i = 1; i <= 2000000; i++) {
			hashMap.put("key" + i, "value" + i);
		}
		log.info("结束");
		// 代码运行开始时间
		Long startTime = System.currentTimeMillis();
		String a = (String) hashMap.get("41653");
		// 代码运行结束时间
		Long endTime = System.currentTimeMillis();
		// 打印说明
		log.info("程序执行时长{}ms", endTime - startTime);
	}

	private static void m23() throws InterruptedException {
		Thread t = new Thread(() -> {
			while (true) {
				if (!run) {
					System.out.println("停止");
					break;
				}
				System.out.println("运行中");
			}
		});
		t.start();

		sleep(1);
		Thread t1 = new Thread(() -> {
			System.out.println("准备停止");
			run = false; // 线程t不会如预想的停下来
		});
		t1.start();
	}

	private static void m22() throws InterruptedException {
		Object r1 = resource;
		HashMap<Integer, Integer> list = new HashMap<>();

		AtomicInteger taskNum = new AtomicInteger();
		Thread thread1 = new Thread(() -> {
			for (int i = 1; i <= 100000; i++) {
				synchronized ("123") {
					list.put(i * 2, i * 2);
				}
			}
			taskNum.getAndIncrement();
		});
		Thread thread2 = new Thread(() -> {
			for (int i = 1; i <= 100000; i++) {
				synchronized ("123") {
					list.put(i * 2 + 1, i * 2 + 1);
				}
			}
			taskNum.getAndIncrement();
		});
		thread1.start();
		thread2.start();
		while (1 == 1) {
			sleep(1);
			if (taskNum.get() == 2) {
				System.out.println(list.size());
				break;
			}

		}
	}

	private static void m21() {
		ConcurrentHashMap<Integer, Integer> hashMap = new ConcurrentHashMap<>();
		for (int i = 1; i <= 12; i++) {
			hashMap.put(i, i);
		}
		System.out.println("数量：" + hashMap.mappingCount());
	}

	private static void m20() {
		ConcurrentLinkedQueue<String> actionQueue = new ConcurrentLinkedQueue<String>();
		for (int i = 1; i <= 12; i++) {
			actionQueue.add(String.valueOf(i));
		}
		for (int i = 1; i <= 12; i++) {
			String poll = actionQueue.peek();
			System.out.println(poll);
		}
		System.out.println("数据1：" + actionQueue);
	}

	private static void m19() {
		EntityInfo top = new EntityInfo();
		List<EntityInfo> child = new ArrayList<>();
		put(child, top);
		System.out.println(top);
		//System.out.println("map size 100, value is " + RamUsageEstimator.humanSizeOf(top));
	}

	private static void put(List<EntityInfo> child, EntityInfo top) {
		//for (int i = 1; i < 3000000; i++) {
		EntityInfo e = new EntityInfo();
		top = e;
		e.setEntityId(UUID.randomUUID().toString());
		e.setParentEntityInfo(top);
		e.setEntityTypeId("45454556");
		ConcurrentHashSet<String> objects = new ConcurrentHashSet<>();
		objects.add(top.getEntityId());
		e.setParentEntityIdSet(objects);
		e.setChildEntityInfoList(child);
		System.out.println("map size 100, value is " + RamUsageEstimator.humanSizeOf(e));
		//}
	}

	private static void m18() {
		List<Person> personList = new ArrayList<Person>();
		personList.add(new Person("张三", 8, 3000));
		personList.add(new Person("李四", 18, 5000));
		personList.add(new Person("王五", 28, 7000));
		personList.add(new Person("孙六", 38, 9000));
		DoubleSummaryStatistics collect = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));
		System.out.println("一次性统计所有信息:" + collect);
	}

	private static void m17() {
		String a = "1";
		assert1();
		System.out.println("已运行！！");
	}

	public static void assert1() {
		Assert.isTrue(1 != 1, "操作正在执行，请稍后再试！");
	}

	private static void m16() {
		for (int i = 1; i <= 1000000; i++) {
			System.out.print("789\r当前数值：" + i);
		}
	}

	public static List<String> method15() {
		List<String> a = new ArrayList<>();
		try {
			a.add("1");
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			a.add("2");
		}
		return a;
	}

	public static int method16() {
		Integer a = Integer.valueOf(1);
		try {
			a = 2;
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return a;
	}

	public static String method() throws IOException {
		Student student1 = new Student();
		student1.setName("张三122");
		Student student2 = new Student();
		student2.setName("李四21");
		try {
			return student1.getName();
		} catch (Exception e) {

		} finally {
			student1 = null;
			//System.in.read();
			System.gc();
			System.out.println("触发成功");
			//System.in.read();
		}
		return student2.getName();
	}

	private static void testTrim() {
		String str = "   hello world。 ";
		str = str.trim(); // 去掉前面的空格
		System.out.println(str);
		HashMap map = new HashMap<>();
		map.entrySet();
	}

	private static void test12() {
		Integer a = 1;
		Integer b = 2;
		Integer c = null;
		Boolean flag = false;
// a*b 的结果是 int 类型，那么 c 会强制拆箱成 int 类型，抛出 NPE 异常
		Integer result = (flag ? a * b : c);//todo s 存在疑问放进去时，参数的类型是看变量的类型还是对象的类型
	}

	public static void test11() {
		method(null);
	}

	public static void method(String param) {
		switch (param) {
// 肯定不是进入这里
			case "sth":
				System.out.println("it's sth");
				break;
// 也不是进入这里
			case "null":
				System.out.println("it's null");
				break;
// 也不是进入这里
			default:
				System.out.println("default");
		}
	}


	private static void change(String str) {
		str = "56";
	}

	public static boolean isChinese(String con) {
		con = con.replaceAll(" ", "");
		System.out.println(con);
		for (int i = 0; i < con.length(); i = i + 1) {
			if (!Pattern.compile("[\u4e00-\u9fa5]").matcher(
					String.valueOf(con.charAt(i))).find()) {
				return false;
			}
		}

		return true;
	}

	private static void test10() {
		Set<String> s1 = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		s1.addAll(Arrays.asList(new String[]{"a", "b", "c"}));
		boolean addSuccess = s1.add("A");
		System.out.println(addSuccess);

		Set<String> s2 = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		s2.addAll(Arrays.asList(new String[]{"A", "B", "C"}));

		System.out.println(s1.equals(s2));
	}

	private static boolean checkIsSimilar(List<String> list, String name) {
		if (name == null) {
			return false;
		}
		try {
			list.forEach(fieldName -> {
				if (name.equalsIgnoreCase(fieldName)) {
					throw new InfoException();
				}
			});
		} catch (InfoException e) {
			return true;
		}
		return false;
	}

	private static void test9() {
		String a = "P_COMMUNITY_SCORE -> p_community_score";
		String b = "P_COMMUNITY_SCORE";
		String c = "id";
		String b1 = StringUtils.removeStart(b, "P_");
		String c1 = StringUtils.removeStart(c, "p_");
		String b1_t = StrUtils.underlineTransferSmallHump(b1);
		String c1_t = StrUtils.underlineTransferSmallHump(c1);
		boolean equal = StrUtil.equalsAnyIgnoreCase(b1_t, c1_t);
		System.out.println(equal);
	}

	private static void test8() {
		String originalText;
		try {
			originalText = FileUtils.fileRead("/data/workplace/临时文件/工具文件夹/DFGF/1");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	private static void test7() {
//		Hashtable hashMap=new Hashtable();
//		hashMap.put("qwe",null);
//		System.out.println(hashMap);
		List<Pair<String, Double>> pairArrayList = new ArrayList<>(2);
		pairArrayList.add(new Pair<>("version1", 4.22));
		pairArrayList.add(new Pair<>("version2", null));
		Map<String, String> map = pairArrayList.stream().collect(
// 抛出 NullPointerException 异常
				Collectors.toMap(Pair::getKey, str -> str.getKey() != null ? str.getKey() : "", (v1, v2) -> v2));
		System.out.println(map);
	}

	private static void test6() {
		String[] departments = new String[]{"iERP", "iERP", "EIBU"};
// 抛出 IllegalStateException 异常
		Map<Integer, String> map = Arrays.stream(departments)
				.collect(Collectors.toMap(String::hashCode, str -> str));
		System.out.println(map);
	}

	private static void test5() {
		List<Pair<String, Double>> pairArrayList = new ArrayList<>(3);
		pairArrayList.add(new Pair<>("version", 6.19));
		pairArrayList.add(new Pair<>("version", 10.24));
		pairArrayList.add(new Pair<>("version", 13.14));
		pairArrayList.add(new Pair<>("version1", 13.14));
		pairArrayList.add(new Pair<>("version2", 13.14));
		System.out.println(pairArrayList.toString());
//		int a = 1;
//		for (int i = 0; i < pairArrayList.size(); ) {
//			Pair<String, Double> pair = pairArrayList.get(i);
//			if (a == 2) {
//				pairArrayList.remove(1);
//				continue;
//			}
//			i++;
//			a++;
//		}
//		Map<String, Double> map = pairArrayList.stream().collect(
//// 生成的 map 集合中只有一个键值对：{version=13.14}
//				Collectors.toMap(Pair::getKey, Pair::getValue, (v1, v2) -> v2));
//		System.out.println(map);
	}

	private static Date dataRemoveMinuteSecond(Date oldDate) {
		if (oldDate == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date newData = null;
		try {
			newData = sdf.parse(sdf.format(oldDate));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return newData;
	}

	private static void test4() {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		if (cal1 == cal2) {
			System.out.println("t");
		} else {
			System.out.println("f");
		}
	}

	private static void test3() {
		double a = 17 + 40 + 20 + 44 + 32 + 25 + 30;
		System.out.println(a);
		a = a * 1.5;
		System.out.println(a);
	}

	private static void test2() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("1").append("2");
		String o = new StringBuilder().append("1").append("2").toString();
		String a = "1" + new StringBuilder().append("2");
		String b = "12";
		String m = stringBuilder.toString();
		if (a == b) {
			System.out.println("相同");
		} else {
			System.out.println("不相同");
		}
		System.out.println(m);
		System.out.println("================");
		stringBuilder.append("3").append("4");
		String n = stringBuilder.toString();
		System.out.println(n);
	}

	private static void test1() {
		String a = "73a0203a-a056-4788-b131-38cb2c8e5688";
		System.out.println("a:" + a.length());
		String b = "aba7bfc09f66473ca542f9b23a2bbab9";
		System.out.println("b:" + b.length());
		double a1 = 50000.0;
		if (a1 == 5.0 * 10000) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
}
