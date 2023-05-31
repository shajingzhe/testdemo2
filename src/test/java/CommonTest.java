import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.zero.exception.InfoException;
import com.zero.utils.StrUtils;
import com.zero.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommonTest {

	private volatile static int i =1;

	public static void main(String[] args) throws IOException {
		String method = method();
		System.out.println(method);
	}

	public static String  method() throws IOException {
		Student student1=new Student();
		student1.setName("张三");
		Student student2=new Student();
		student2.setName("李四");
		try{
			return student1.getName();
		}catch (Exception e){

		}finally {
			student1=null;
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
		HashMap map=new HashMap<>();
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

	public static void method( String param) {
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
