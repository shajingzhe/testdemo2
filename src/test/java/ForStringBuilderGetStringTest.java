/**
 * @author shajingzhe
 * @date 2023/4/20 上午11:26
 */
public class ForStringBuilderGetStringTest {
	public static void main(String[] args) {
		String a=forStringBuilderGetString(8,"asdf");
		System.out.println(a);
	}

	/**
	 * 循环获取大量测试使用文字
	 * @param forNum
	 * @param initString
	 * @return
	 */
	private static String forStringBuilderGetString(int forNum, String initString) {
		StringBuilder stringBuilder = new StringBuilder().append(initString);
		for (int i = 1; i <= forNum; i++) {
			stringBuilder.append(stringBuilder);
		}
		return stringBuilder.toString();
	}
}
