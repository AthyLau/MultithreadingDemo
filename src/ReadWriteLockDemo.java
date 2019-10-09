import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Function:读写锁问题。也就是共享锁跟排他锁
 *
 * @author liubing
 * Date: 2019/6/7 3:49 PM
 * @since JDK 1.8
 */
public class ReadWriteLockDemo {

    public static void main(String args[]) {
        for (int i = 0; i < 5; i++) {
            final String name = i + "";
            new Thread(() -> {
                Mycache.put(name, name);
            }, String.valueOf(i)).start();
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5; i++) {
            final String name = i + "";
            new Thread(() -> {
                Mycache.get(name);
            }, String.valueOf(i)).start();
        }
    }


}

class Mycache {
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static volatile Map<String, Object> myCache = new HashMap<>();

    //写入数据到缓存
    public static void put(String str, Object o) {
        reentrantReadWriteLock.writeLock().lock();
        try {
            System.out.println("开始写入:" + str);
            try {
                Thread.sleep(300);
//                if(!str.equals("0")){
////                    Thread.sleep(300);
////                }
////                else {
////                    Thread.sleep(3000);
////                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myCache.put(str, o);
            System.out.println("写入完成:" + str);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    //从缓存读出数据
    public static void get(String str) {
        reentrantReadWriteLock.readLock().lock();
        Object value = null;
        try {
            System.out.println("开始读取:" + str);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            value = myCache.get(str);
            System.out.println("读取完成:" + value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }
}