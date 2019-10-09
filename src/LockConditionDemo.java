import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Function：写了一个关于分组唤醒线程或者精确唤醒线程的demo，lock才有这种机制
 * 而Sychnroized只能是唤醒随机一个线程或者是唤醒所有的线程
 * <p>
 * A线程输出3次B线程输出4次C线程输出5次然后紧接着A线程输出3次B。。。来十遍
 *
 * @author liubing
 * Date: 2019/6/10 7:08 PM
 * @since JDK 1.8
 */
public class LockConditionDemo {

    public static void main(String args[]) {
        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                MyThread.print3();
            }, "A").start();
            new Thread(() -> {
                MyThread.print4();
            }, "B").start();
            new Thread(() -> {
                MyThread.print5();
            }, "C").start();
        }

    }

}

class MyThread {
    private static Integer number = 1;
    private static Lock lock = new ReentrantLock();
    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static Condition conditionC = lock.newCondition();

    /**
     * 想把三个方法合并成一个。。但是不太行？？
     */
//    public static void printN(){
//        lock.lock();
//        try{
//            while(number!=1){
//                conditionA.await();
//            }
//            while(number!=2){
//                conditionB.await();
//            }while(number!=3){
//                conditionC.await();
//            }for(int i = 0; i < number+2;i++){
//                System.out.println(Thread.currentThread().getName()+"第"+i+"次");
//            }
//            number++;
//            while(number!=1){
//                conditionB.signal();
//            }while(number!=2){
//                conditionC.signal();
//            }while(number!=3){
//                conditionA.signal();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            lock.unlock();
//        }
//    }
    public static void print3() {
        lock.lock();
        try {
            while (number != 1) {
                conditionA.await();
            }
            System.out.println("======================");
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + "第" + i + "次");
            }
            number = 2;
            conditionB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void print4() {
        lock.lock();
        try {
            while (number != 2) {
                conditionB.await();
            }
            for (int i = 0; i < 4; i++) {
                System.out.println(Thread.currentThread().getName() + "第" + i + "次");
            }
            number = 3;
            conditionC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void print5() {
        lock.lock();
        try {
            while (number != 3) {
                conditionC.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "第" + i + "次");
            }
            number = 1;
            conditionA.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}