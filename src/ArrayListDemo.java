import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Function:ArrayList线程不安全的问题及其解决办法
 *
 * @author liubing
 * Date: 2019/5/31 5:44 PM
 * @since JDK 1.8
 */
public class ArrayListDemo {
    public static void main(String args[]) {
        unSafeList();
//        resolveMethod1();
//        resolveMethod2();
//        resolveMethod3();
    }

    //java.util.ConcurrentModificationException
    //多个线程一块往这个list里面写东西
    public static void unSafeList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 使用vector
     */
    public static void resolveMethod1() {
        List<String> list = new Vector<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 使用Collections.synchronizedList加锁的list
     */
    public static void resolveMethod2() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 使用JUC包下的写时复制类CopyOnWriteArrayList
     */
    public static void resolveMethod3() {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
