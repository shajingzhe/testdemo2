import com.zero.entity.XFileInfo;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static dm.jdbc.util.DriverUtil.log;
import static java.lang.Thread.sleep;


public class ThreadTest {
	private static volatile XFileInfo m = new XFileInfo("23");

	public static void main(String[] args) {

		String name = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println(name);
		// get pid
		String pid = name.split("@")[0];
		System.out.println("Pid is:" + pid);
		String au1 = "12";
		String bu2 = "123";
		Thread thread1 = new Thread(() -> {
			synchronized (au1) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (bu2) {

				}
			}
		},"testThread1");
		Thread thread2 = new Thread(() -> {
			synchronized (bu2) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (au1) {

				}
			}
		},"testThread2");
		thread1.start();
		thread2.start();
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("start.....");


	}

	private static void test4() {
		Thread thread1 = new Thread(() -> {
			System.out.println("使用lambda表示创建线程");
		});
		thread1.start();
	}


	public static void m1() {
		StringBuilder sb = new StringBuilder();
		sb.append(1);
		sb.append(2);
		sb.append(3);
		System.out.println(sb.toString());
	}

	public static void m2(StringBuilder sb) {
		sb.append(1);
		sb.append(2);
		sb.append(3);
		System.out.println(sb.toString());
	}

	public static StringBuilder m3() {
		StringBuilder sb = new StringBuilder();
		sb.append(1);
		sb.append(2);
		sb.append(3);
		return sb;
	}

	private static void test3() {
		Thread thread = new Thread(() -> {
			System.out.println("使用lambda表示创建线程");
		});
		//1、使用线程池的工厂类Executors里边提供的静态方法newFixedThreadPool生产一个指定线程数量的线程池
		ExecutorService es = Executors.newFixedThreadPool(2);
		//3、调用ExecutorsService中的方法submit，传递线程任务（实现类），开启线程，执行run方法
		es.submit(() -> {
			log.info("123");//todo s 无法往后处理
		});
	}

	private static void test2() {
		int a = (int) 1.4;
		System.out.println(a);
		int b = (int) 1.6;
		System.out.println(b);

	}

	private static void test1() {
		String m = "1";
		Thread a = new Thread(() -> {
			synchronized (m) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				log.info("123");
			}
		});
		log.info("start....");
		a.start();
	}
}
