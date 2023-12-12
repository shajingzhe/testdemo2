package gpt;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExample extends ReentrantLock {
	public static void main(String[] args) {
		List<String> list = new CopyOnWriteArrayList<>(); // 使用CopyOnWriteArrayList保证线程安全
		BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

		ExecutorService executorService = Executors.newFixedThreadPool(5);

		// 线程1：向List中添加信息
		executorService.execute(() -> {
			try {
				while (true) {
					String info = generateInfo();
					list.add(info);
					blockingQueue.put(info);
					Thread.sleep(1000); // 模拟信息添加的时间间隔
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		// 线程2~N：从List中取出信息
		for (int i = 0; i < 4; i++) {
			executorService.execute(() -> {
				try {
					while (true) {
						String info = blockingQueue.take(); // 阻塞直到有信息可取
						processInfo(info);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
	}

	private static String generateInfo() {
		// 模拟生成信息的逻辑
		return "Info-" + System.currentTimeMillis();
	}

	private static void processInfo(String info) {
		// 模拟处理信息的逻辑
		System.out.println("Processing: " + info);
	}

	private void demo() throws InterruptedException {
		ReentrantLock reentrantLock = new ReentrantLock();
		Condition condition = reentrantLock.newCondition();
		condition.await();
		condition.signalAll();
	}
}
