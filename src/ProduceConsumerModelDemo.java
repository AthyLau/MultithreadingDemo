import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Function:阻塞队列形式也就是传说中的3.0版本生产者消费者模型，底层消息中间件的原理
 *
 * @author liubing
 * Date: 2019/6/11 4:45 PM
 * @since JDK 1.8
 */
public class ProduceConsumerModelDemo {
    public static void main(String args[]) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            try {
                System.out.println("生产者开始服务");
                System.out.println();
                myResource.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "producer").start();
        new Thread(() -> {
            try {
                System.out.println("消费者开始服务");
                System.out.println();
                myResource.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "consumer").start();
        try {
            System.out.println();
            System.out.println();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("生产者消费者都停下！！");
        myResource.allStop();
        System.out.println();
        System.out.println();
        System.out.println(myResource.getBlockingQueue());
    }
}

class MyResource {
    private volatile Boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(Thread.currentThread().getName() + "BlockingQueue接口的实现类" + blockingQueue.getClass().getName() + "传入构造方法");
    }

    public void producer() throws InterruptedException {
        String data = null;
        while (FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            if (blockingQueue.offer(data, 2L, TimeUnit.SECONDS)) {
                System.out.println("生产者生产一个,并放入阻塞队列里面成功.");
            } else {
                System.out.println("生产者生产一个,并放入阻塞队列里面失败.");
            }
            Thread.sleep(1000);
        }
        System.out.println("生产者停止服务");
    }

    public void consumer() throws InterruptedException {
        String result = null;
        while (FLAG) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (result == null || result.equals("")) {
                System.out.println("消费者消费一个，并从阻塞队列里面取出失败。");
                FLAG = false;
                break;
            }
            System.out.println("消费者消费一个，并从阻塞队列里面取出成功。");
            Thread.sleep(1000);
        }
        System.out.println("消费者停止服务");
    }

    public void allStop() {
        FLAG = false;
    }

    public BlockingQueue<String> getBlockingQueue() {
        return blockingQueue;
    }
}
