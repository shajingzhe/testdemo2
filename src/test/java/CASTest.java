import java.util.concurrent.atomic.AtomicInteger;

import java.util.concurrent.CountDownLatch;

public class CASTest {
    private volatile int count = 0;
    public static AtomicInteger atomicIntegerCount = new AtomicInteger(0);

    public void doAdd(CountDownLatch countDownLatch) {
        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        count++;
                        atomicIntegerCount.incrementAndGet();
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(4);
        CASTest add = new CASTest();
        add.doAdd(countDownLatch);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("volatile count:" + add.getCount());
        System.out.println("atomicIntegerCount：" + atomicIntegerCount.get());

    }

}
