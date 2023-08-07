import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NonRepeatNumberGenerator {

	private List<Integer> numberList;
	private Random random;

	public NonRepeatNumberGenerator() {
		numberList = new ArrayList<>();
		for (int i = 1; i <= 1000000; i++) {
			numberList.add(i);
		}
		random = new Random();
	}

	public synchronized int getNonRepeatNumber() {
		if (numberList.isEmpty()) {
			return -1; // or any other sentinel value to indicate no more numbers
		}

		int index = random.nextInt(numberList.size());
		int number = numberList.remove(index);

		return number;
	}

	public static void main(String[] args) {
		NonRepeatNumberGenerator generator = new NonRepeatNumberGenerator();

		Runnable runnable = () -> {
			int threadId = Integer.parseInt(Thread.currentThread().getName());
			for (int i = 0; i < 100; i++) {
				int number = generator.getNonRepeatNumber();
				if (number != -1) {
					System.out.println("Thread " + threadId + ": " + number);
				} else {
					System.out.println("Thread " + threadId + ": No more numbers");
					break;
				}
			}
		};

		for (int i = 1; i <= 10; i++) {
			Thread thread = new Thread(runnable, String.valueOf(i));
			thread.start();
		}
	}
}
//这个程序创建了一个名为NonRepeatNumberGenerator的类，它维护了一个包含数字1到1000的链表。每个线程通过调用getNonRepeatNumber方法从链表中获取一个不重复的数字。
// 当链表中的数字用尽时，方法会返回-1作为标志值。程序创建了10个线程来并行获取数字，并在控制台打印输出结果。
//希望这个程序能满足你的需求！如果你有任何其他问题，欢迎继续提问。