import entity.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author shajingzhe
 * @date 2023/4/7 下午5:00
 */
public class StreamTest {
	public static void main(String[] args) {
		test12();
	}

	private static  void test12(){
		List<String> list=new ArrayList<>();
//		list.add("1");
//		list.add("2");
//		list.add("3");
//		list.add("4");
		String result =list.stream().collect(Collectors.joining(";"));
		System.out.println(result);
	}

	private static void test11(){
		List<Person> personList=new ArrayList<>();
		personList.add(new Person("zhangsan",25, 3000, "male", "tieling"));
		personList.add(new Person("lisi",27, 5000, "male", "tieling"));
		personList.add(new Person("wangwu",29, 7000, "female", "tieling"));
		personList.add(new Person("sunliu",26, 3000, "female", "dalian"));
		personList.add(new Person("yinqi",27, 5000, "male", "dalian"));
		personList.add(new Person("guba",21, 7000, "female", "dalian"));

		// 按工资升序排序（自然排序）
		List<String> newList = personList.stream().sorted(Comparator.comparing(Person::getSalary)).map(Person::getName)
				.collect(Collectors.toList());
		// 按工资倒序排序
		List<Integer> newList2 = personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed())
				.map(Person::getSalary).collect(Collectors.toList());
		// 先按工资再按年龄升序排序
		List<String> newList3 = personList.stream()
				.sorted(Comparator.comparing(Person::getSalary).thenComparing(Person::getAge)).map(Person::getName)
				.collect(Collectors.toList());
		// 先按工资再按年龄自定义排序（降序）
		List<String> newList4 = personList.stream().sorted((p1, p2) -> {
			int a=p1.getSalary();
			int b=p2.getSalary();
			if (p1.getSalary() == p2.getSalary()) {
				return p2.getAge() - p1.getAge();
			} else {
				return p2.getSalary() - p1.getSalary();
			}
		}).map(Person::getName).collect(Collectors.toList());

		System.out.println("按工资升序排序：" + newList);
		System.out.println("按工资降序排序：" + newList2);
		System.out.println("先按工资再按年龄升序排序：" + newList3);
		System.out.println("先按工资再按年龄自定义降序排序：" + newList4);
	}

	private static void test8() {
		List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);

		// 遍历输出符合条件的元素
		list.stream().filter(x -> x > 6).forEach(System.out::println);
		// 匹配第一个
		Optional<Integer> findFirst = list.stream().filter(x -> x > 6).findFirst();
		// 匹配任意（适用于并行流）
		Optional<Integer> findAny = list.parallelStream().filter(x -> x > 6).findAny();
		// 是否包含符合特定条件的元素
		boolean anyMatch = list.stream().anyMatch(x -> x < 6);
		System.out.println("匹配第一个值：" + findFirst.get());
		System.out.println("匹配任意一个值：" + findAny.get());
		System.out.println("是否存在大于6的值：" + anyMatch);
	}

	private static void test1() {
		Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);

		Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 3).limit(4);
		stream2.forEach(System.out::println);

		Stream<Double> stream3 = Stream.generate(Math::random).limit(3);
		stream3.forEach(System.out::println);
	}

}
