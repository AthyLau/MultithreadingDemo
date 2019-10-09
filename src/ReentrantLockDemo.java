import java.util.concurrent.locks.ReentrantLock;

/**
 * Function:可重入锁代码demo，校验了两种可重入锁、可重入锁就是递归锁
 * <p>
 * synchronized也是可重入锁
 * ReentrantLock也是一个典型的可重入锁
 *
 * @author liubing
 * Date: 2019/6/6 3:36 PM
 * @since JDK 1.8
 */
public class ReentrantLockDemo {
    public static Integer sum = 0;

    public static void main(String args[]) {
        getRecursionSyn();
        getReentrantLock();
    }

    //synchronized也是可重入锁
    public synchronized static void getRecursionSyn() {
        if (sum == 10) {
            System.out.println(sum);
        } else {
            sum++;
            getRecursionSyn();
        }
    }

    //ReentrantLock也是一个典型的可重入锁
    public static void getReentrantLock() {
        //Lock lock = new ReentrantLock(false);
        //ReentrantLock实现了lock接口
        ReentrantLock reentrantLock = new ReentrantLock(false);
        reentrantLock.lock();
        //这边lock两次报错吗？不会报错，只要这边锁n次下边解n次那就运行正确。锁的次数跟解锁的次数不对应的话那程序运行就会卡住
        try {
            if (sum == 0) {
                System.out.println(sum);
            } else {
                sum--;
                getReentrantLock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }
}
