import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Function:自旋锁演示demo
 * <p>
 * A线程加锁后沉睡5秒然后解锁，B线程加锁解锁
 * <p>
 * B线程会等待A线程加锁完成之后才开始加锁
 * <p>
 * 请手写一个自旋锁
 *
 * @author liubing
 * Date: 2019/6/6 5:17 PM
 * @since JDK 1.8
 */
public class SpinLockDemo {
    //定一个一个带时间戳的原子线程类，给的默认值是false
    static AtomicStampedReference<Thread> atomicThread = new AtomicStampedReference<>(null, 1);

    public static void main(String args[]) {
        new Thread(() -> {
            myLock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                myUnLock();
            }
        }, "AAA").start();
        new Thread(() -> {
            myLock();
            myUnLock();
        }, "BBB").start();
    }

    public static void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " come in,version is " + atomicThread.getStamp());
        while (!atomicThread.compareAndSet(null, thread, 1, 2)) {
//            System.out.println("加锁失败");
        }
        System.out.println(thread.getName() + "加锁成功");
        System.out.println(thread.getName() + " come out,version is " + atomicThread.getStamp());
    }

    public static void myUnLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " come in,version is " + atomicThread.getStamp());
        while (!atomicThread.compareAndSet(thread, null, 2, 1)) {
//            System.out.println("解锁失败");
        }
        System.out.println(thread.getName() + "解锁成功");
        System.out.println(thread.getName() + " come out,version is " + atomicThread.getStamp());
    }
}
