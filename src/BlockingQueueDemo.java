import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Function:阻塞队列的demo、阻塞队列的四套方法的区别，主要是使用有界阻塞队列也就是ArrayBlockingQueue
 *
 * @author liubing
 * Date: 2019/6/10 4:36 PM
 * @since JDK 1.8
 */
public class BlockingQueueDemo {
    public static void main(String args[]) {
//        frist();
//        second();
//        third();
        fourth();
    }
    //add() remove() element()

    /**
     * 这是异常的一组阻塞队列的使用demo
     * 添加删除 如果正确返回值为true如果错误则抛出异常 查看若正确则返回正确的队列首元素错误则抛出异常
     */
    public static void frist() {
        //要给新建的阻塞队列设置初始长度
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        //添加三次
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //添加超出的一个元素进去
//        System.out.println(blockingQueue.add("x"));
        //显示存在的队列首元素
        System.out.println(blockingQueue.element());
        //删除三次
        System.out.println(blockingQueue.remove("c"));
        System.out.println(blockingQueue.remove("a"));
        System.out.println(blockingQueue.remove("b"));
        //显示不存在的队列首元素会报错
        System.out.println(blockingQueue.element());

    }
    //offer() poll() peek()

    /**
     * 这是返回特殊值的一组阻塞队列的API方法
     * 添加如果正确则返回值为true如果错误则返回值为false
     * 删除如果正确则返回删除的队列的元素错误则返回null
     * 查看如果正确则返回查看的队列的首元素，错误则返回null
     */
    public static void second() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("A"));
        System.out.println(blockingQueue.offer("B"));
        System.out.println(blockingQueue.offer("C"));
        System.out.println(blockingQueue.offer("X"));

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

        System.out.println(blockingQueue.peek());
    }
    //put() take()

    /**
     * 这是rabbitMQ的底层实现原理，put() take()这套方法会保证不丢失消息，put如果失败会一直阻塞在这里等待着put进去，take如果失败也是一样的
     */
    public static void third() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        try {
            blockingQueue.put("A");
            blockingQueue.put("B");
            blockingQueue.put("C");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                blockingQueue.put("X");
                System.out.println(blockingQueue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            blockingQueue.take();
            blockingQueue.take();
            blockingQueue.take();
            blockingQueue.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    //offer() poll()

    /**
     * 这是一组超时的阻塞队列的API Demo可以设置添加删除操作的具体等待时间是多少。
     */
    public static void fourth() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        try {
            blockingQueue.offer("a", 2L, TimeUnit.SECONDS);
            blockingQueue.offer("b", 2L, TimeUnit.SECONDS);
            blockingQueue.offer("c", 2L, TimeUnit.SECONDS);
            new Thread(() -> {
                try {
                    blockingQueue.offer("X", 2L, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            blockingQueue.poll(2L, TimeUnit.SECONDS);
            blockingQueue.poll(2L, TimeUnit.SECONDS);
            blockingQueue.poll(2L, TimeUnit.SECONDS);
            blockingQueue.poll(2L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
