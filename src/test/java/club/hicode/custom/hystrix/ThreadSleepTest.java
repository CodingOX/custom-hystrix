package club.hicode.custom.hystrix;

import java.util.concurrent.*;

/**
 * XXXX
 *
 * @author LiuChunfu
 * @date 2018/10/7
 */
public class ThreadSleepTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Future<String> submit = pool.submit(() -> {
            try {
                Thread.sleep(1333333);
                System.out.println(123);
                return "123";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "545";
            }
        });

        String s = submit.get(3, TimeUnit.SECONDS);
        System.out.println(s);
    }
}
