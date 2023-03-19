import entity.XFileInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static dm.jdbc.util.DriverUtil.log;


public class ThreadTest {
    private static volatile XFileInfo m = new XFileInfo("23");

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            System.out.println("使用lambda表示创建线程");
        });
        thread1.start();

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
                    Thread.sleep(1000);
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
