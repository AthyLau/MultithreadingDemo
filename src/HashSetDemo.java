import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Function:   多线程并发条件下set集合的线程安全问题demo以及解决办法
 *
 * @author liubing
 * Date: 2019/5/31 8:11 PM
 * @since JDK 1.8
 */
public class HashSetDemo {
    public static void main(String args[]) {
//        unSafeSet();
//        resolveMethod1();
        resolveMethod2();
    }

    //java.util.ConcurrentModificationException
    //多个线程一块往这个set里面写东西
    public static void unSafeSet() {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }


    /**
     * 使用Collections.synchronizedList加锁的list
     */
    public static void resolveMethod1() {
        Set<String> set = Collections.synchronizedSet(new HashSet<String>());
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 使用JUC包下的写时复制类CopyOnWriteArraySet
     */
    public static void resolveMethod2() {
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
