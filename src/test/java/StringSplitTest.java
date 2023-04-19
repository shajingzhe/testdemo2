import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author shajingzhe
 * @date 2023/4/11 下午4:06
 */
public class StringSplitTest {

	public static void main(String[] args) {
		test11();
	}

	private static void test01() {
		String str = "a,b,c,,2";
		String[] ary = str.split(",");
		System.out.println(ary.length);
	}

	private static void test02() {
		List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu", "sunliu");
		Comparator<? super String> comparator = Comparator.comparing(String::length);//-------
		Optional<String> max = list.stream().max(comparator);
		System.out.println(max);
	}

	private static void test05() {
		List<Integer> list = Arrays.asList(1, 17, 27, 7);
		Optional<Integer> max = list.stream().max(Integer::compareTo);//-------
		// 自定义排序
		Optional<Integer> max2 = list.stream().max(new Comparator<Integer>() {//-------
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		System.out.println(max);
	}

//	private static void test06() {
//		initPerson();
//		Comparator<? super Person> comparator = Comparator.comparingInt(Person::getAge);//-------
//		Optional<Person> max = personList.stream().max(comparator);
//		System.out.println(max);
//	}

	private static void test11() {
		String[] arr = {"z, h, a, n, g", "s, a, n"};
		List<String> list = Arrays.asList(arr);
		System.out.println(list);
		List<String> collect = list.stream().flatMap(x -> {
			String[] array = x.split(",");
			Stream<String> stream = Arrays.stream(array);
			return stream;
		}).collect(Collectors.toList());
		System.out.println(collect);
	}

//	/**
//	 * 取出大于18岁的员工转为map
//	 *
//	 */
//	private static void test15() {
//		initPerson();
//		Map<String, Person> collect = personList.stream().
//				filter(x -> x.getAge() > 18).
//				collect(Collectors.toMap(Person::getName, y -> y));
//		System.out.println(collect);
//	}

}
