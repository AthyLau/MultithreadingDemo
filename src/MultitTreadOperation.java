import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Function:生产者消费者模型，第三种自己想的那种其实就是用了消息队列，见代码ProduceConsumerModel
 * <p>
 * 多线程操作资源类，生产者生产一个，消费者就消费一个，相当于同步队列
 *
 * @author liubing
 * Date: 2019/6/10 6:04 PM
 * @since JDK 1.8
 */
public class MultitTreadOperation {
    public static void main(String args[]) {
//        testSychnroizedResource();
//        testLockResource();
        testAtomicResource();
        int i = 0;
        double d = 0.0;
        short s = 1;
        char c = '1';
        i = s + 1;
        i = s++;

        s = s++;
        d = s++;
        i = c++;


    }

    public static void testSychnroizedResource() {
        SychnroizedResource sychnroizedResource = new SychnroizedResource();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                sychnroizedResource.increment();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                sychnroizedResource.increment();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                sychnroizedResource.decrement();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                sychnroizedResource.decrement();
            }
        }, "D").start();
    }

    public static void testLockResource() {
        LockResource lockResource = new LockResource();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                lockResource.increment();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                lockResource.increment();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                lockResource.decrement();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                lockResource.decrement();
            }
        }, "D").start();
    }

    /**
     * 多线程情况下操作资源类使用atomic原子类并不能解决问题实现生产者生产一个消费者消费一个的机制
     */
    public static void testAtomicResource() {
        AtomicResource atomicResource = new AtomicResource();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                atomicResource.increment();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                atomicResource.increment();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                atomicResource.decrement();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                atomicResource.decrement();
            }
        }, "D").start();
    }
}

/**
 * 传统的1.0版本下，使用Sychnroized字段加锁的多线程操作下的资源类代码
 */
class SychnroizedResource {
    private Integer number = 0;

    public synchronized void increment() {
        while (number != 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "生产者生产1个，number = " + number);
        this.notifyAll();
    }

    public synchronized void decrement() {
        while (number == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "消费者消费1个，number = " + number);
        this.notifyAll();
    }
}

/**
 * 2.0版本，使用lock加锁保证唤醒线程使用signal
 */
class LockResource {
    private Integer number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();
        try {
            while (number != 0) {
                //生产者等待
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "生产者生产1个，number = " + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            while (number == 0) {
                //消费者等待
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "消费者消费1个，number = " + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

/**
 * 额外自己考虑的一个版本，使用volatile字段+原子操作类实现,然而并不行。
 **/
class AtomicResource {
    private volatile AtomicInteger atomicInteger = new AtomicInteger(0);

    public void increment() {
        while (atomicInteger.get() != 0) {

        }
        atomicInteger.getAndIncrement();
        System.out.println(Thread.currentThread().getName() + "生产者生产1个，number = " + atomicInteger.get());
    }

    public void decrement() {
        while (atomicInteger.get() == 0) {

        }
        atomicInteger.getAndDecrement();
        System.out.println(Thread.currentThread().getName() + "消费者消费1个，number = " + atomicInteger.get());
    }
}