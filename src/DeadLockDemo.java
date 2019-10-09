import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Function:模拟死锁的情况发生
 *
 * @author liubing
 * Date: 2019/6/13 2:53 PM
 * @since JDK 1.8
 */
public class DeadLockDemo {
    public static void main(String args[]) {
        ThreadResource threadResource = new ThreadResource();
        new Thread(() -> {
            threadResource.method1();
        }, "线程A").start();
        new Thread(() -> {
            threadResource.method2();
        }, "线程B").start();
    }

}

class ThreadResource {
    private Lock lockA = new ReentrantLock();
    private Lock lockB = new ReentrantLock();

    public void method1() {
        lockA.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "线程取到锁A");
            Thread.sleep(1000);
            method2();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockA.unlock();
        }
    }

    public void method2() {
        lockB.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "线程取到锁B");
            Thread.sleep(1000);
            method1();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockB.unlock();
        }
    }
}