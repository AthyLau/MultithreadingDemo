import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Function:同步阻塞队列，生产一个消费一个，没消费就不生产。
 *
 * @author liubing
 * Date: 2019/6/10 5:12 PM
 * @since JDK 1.8
 */
public class SychronousQueueDemo {

    public static void main(String args[]) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "添加A");
            try {
                blockingQueue.put("A");
                System.out.println(Thread.currentThread().getName() + "添加B");
                blockingQueue.put("B");
                System.out.println(Thread.currentThread().getName() + "添加C");
                blockingQueue.put("C");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

        new Thread(() -> {
            try {
                Thread.sleep(3000);

                System.out.println(Thread.currentThread().getName() + "取出A");
                blockingQueue.take();

                Thread.sleep(3000);

                System.out.println(Thread.currentThread().getName() + "取出B");
                blockingQueue.take();

                Thread.sleep(3000);

                System.out.println(Thread.currentThread().getName() + "取出C");
                blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();
    }

}
