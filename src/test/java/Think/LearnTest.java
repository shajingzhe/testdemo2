package Think;

import org.springframework.cache.Cache;

/**
 * @author shajingzhe
 * @date 2023/4/7 上午9:58
 */
public class LearnTest {

	//todo s拆分函数
	private TacticsService tacticsService;

	LearnTest learnTest(TacticsService tacticsService) {
		this.tacticsService = tacticsService;
		return null;
	}

	public static void main(String[] args) {
		//获取信息
		getInfo();
		//处理信息，去除无用信息
		handleInfo();
		//对精简的数据进行处理，推导
		pickInfo();
		//对推导数据进行验证正确性
		checkInfo();
		//将信息储存入对应层数据库
		store2DB();
	}

	/**
	 * 将信息储存入对应层数据库
	 */
	private static void store2DB() {
	}

	/**
	 * 对精简的数据进行处理，推导
	 */
	private static void checkInfo() {
	}

	/**
	 * 对推导数据进行验证正确性
	 */
	private static void pickInfo() {
	}

	/**
	 * 处理信息，去除无用信息
	 */
	private static void handleInfo() {
	}

	/**
	 * 获取信息
	 */
	private static void getInfo() {
	}

	public class dDB {
		String deepDB;
		Object commonDB;
		Cache cacheDB;

		//
	}
}
