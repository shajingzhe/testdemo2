package Think;

import Think.strategy.WorkTimeStrategy;
import Think.strategy.WorkTimeStrategyImpl;

/**
 * @author shajingzhe
 * @date 2023/4/7 上午10:20
 */
public class Main {

	WorkTimeStrategy workTimeStrategy;


	Main(){
		this.workTimeStrategy=new WorkTimeStrategyImpl();
	}

	public static void main(String[] args) {

	}
}
