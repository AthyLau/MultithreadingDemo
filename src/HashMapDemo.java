import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Function:
 *
 * @author liubing
 * Date: 2019/5/31 8:15 PM
 * @since JDK 1.8
 */
public class HashMapDemo {
    public static void main(String args[]) {
//        unSafeMap();
//        resolveMethod1();
//        resolveMethod2();
        resolveMethod3();
    }

    //java.util.ConcurrentModificationException
    //多个线程一块往这个map里面写东西
    public static void unSafeMap() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }


    /**
     * 使用Collections.synchronizedMap加锁的map
     */
    public static void resolveMethod1() {
        Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 使用JUC包下的ConcurrentHashMap类
     */
    public static void resolveMethod2() {
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 使用hashTable
     */
    public static void resolveMethod3() {
        Map<String, String> map = new Hashtable<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
