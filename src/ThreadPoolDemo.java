import java.util.concurrent.*;

/**
 * Function:三种线程池的获得方式以及其使用、以及自定义线程池的四种拒绝策略演示demo
 *
 * @author liubing
 * Date: 2019/6/12 3:54 PM
 * @since JDK 1.8
 */
public class ThreadPoolDemo {
    public static void main(String args[]) {
        personalThreadPool();


    }

    //new ThreadPoolExecutor.AbortPolicy()  拒绝策略，任务队列满+线程满 直接报错
    //new ThreadPoolExecutor.CallerRunsPolicy() 拒绝策略，任务队列满+线程满 让调用线程池完成任务的线程来执行任务
    //new ThreadPoolExecutor.DiscardOldestPolicy() 拒绝策略，任务队列满+线程满 抛弃队列中等待最久的任务
    //new ThreadPoolExecutor.DiscardPolicy() 拒绝策略，任务队列满+线程满 直接抛弃任务
    //自定义线程池
    public static void personalThreadPool() {
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );
        try {
            //模拟十个人来办理业务
            for (int i = 0; i < 30; i++) {
                final int stamp = i;
                threadPool.execute(new Thread(() -> {
                    System.out.println("线程" + Thread.currentThread().getName() + "完成业务" + stamp);
                }));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    //executor框架自带的线程池
    public static void executorThreadPool() {
        //五个线程的线程池
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //单个线程的线程池
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //多个线程的线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();

        try {
            //模拟十个人来办理业务
            for (int i = 0; i < 30; i++) {
                threadPool.execute(new Thread(() -> {
                    System.out.println("线程" + Thread.currentThread().getName() + "完成业务");
                }));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
