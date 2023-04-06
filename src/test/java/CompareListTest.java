import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author shajingzhe
 * @date 2023/4/6 上午11:20
 */
public class CompareListTest {
	@Test
	public static void main(String[] args) {
		// 老师集合
//		Arrays.asList().remove();
//		new LinkedList<>().add()
		List<String> teachers = new ArrayList<>();
		teachers.add("1");
		teachers.add("2");
		teachers.add("3");
		teachers.add("4");
		teachers.add("5");
		teachers.add("6");
		List<String> teachers2=new ArrayList<>();
		teachers2.addAll(teachers);
		teachers2.add("67");
		teachers2.add(2,"45");
		teachers.forEach(teachers2::remove);
		System.out.println(teachers);
		System.out.println("===================");
		System.out.println(teachers2);
//		for (int i = 0; teachers.size() > i; i++) {
//			String zone = teachers.get(i);
//			if ("2".equals(zone)) {
//				teachers.remove(zone);
//			}
//		}
//		System.out.println(teachers);
//
//		Iterator<String> zonesIterator = teachers.iterator();
//		while (zonesIterator.hasNext()) {
//			if ("2".equals(zonesIterator.next())) {
//				zonesIterator.remove();
//			}
//		}
//
//		Iterator<String> iterator=teachers.iterator();
//		while (iterator.hasNext()){
//			String a=iterator.next();
//			System.out.println(a);
//		}
//
//		Iterator<String> iterator1 = teachers.iterator();
//		while(iterator1.hasNext()){
//			String a=iterator1.next();

//		}

	}
}
