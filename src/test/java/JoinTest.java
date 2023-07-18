import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author shajingzhe
 * @Date 2023/6/14 下午1:38
 */
public class JoinTest {
	public static void main(String[] args) {
		List<String> list= new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		String join = StringUtils.join(list, "','");
		System.out.println("'"+join+"'");
		String format = "4646";
		System.out.println(format);
	}
}
