import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;

/**
 * Function:演示CAS比较并交换的案例
 * ABA问题
 * x         y
 * 线程1 except A，update B
 * 线程2 except A, update B
 * 线程3 except B, update A
 * 线程执行顺序为 1x、2x、1y、3x、3y、2y结果为A—>B->A->B
 * 去银行取钱，某个动作点击了两次，比如余额为 100 取钱 50 动作点击了两次，分别为线程1，2，此时某人汇钱给此账户 50 元为线程3按照上边的顺序执行后结果为 50元，显然是错误的，应该是100元
 * 加个版本号可以解决问题。
 *
 * @author liubing
 * Date: 2019/5/30 7:11 PM
 * @since JDK 1.8
 */
public class CASDemo {
    //余额为100
    //原子引用类型可以自己随意构造不带时间戳的原子引用类型
    static volatile AtomicReference<Integer> atomicReference = new AtomicReference<>();
    //原子整型JUC已经构造好的原子整型
    static volatile AtomicInteger atomicInteger = new AtomicInteger(100);
    //原子时间戳引用类型，可以自己随意构造带时间戳的原子引用类型
    static volatile AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<Integer>(100, 0);

    public static void main(String args[]) {
//        System.out.println("ABA问题");
//        ABAQuestion();
//        System.out.println("ABA问题解决方案");
//        ABAAnswer();
    }

    public static void ABAQuestion() {
        //取钱50线程1
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "线程修改100为50的结果是" + atomicInteger.compareAndSet(100, 50));
        }, "1").start();
        //取钱50线程2
        new Thread(() -> {
            while (!atomicInteger.compareAndSet(100, 50)) {

            }
            System.out.println(Thread.currentThread().getName() + "线程修改100为50的结果是" + true);
            System.out.println(Thread.currentThread().getName() + "线程查看结果为" + atomicInteger.get());
        }, "2").start();
        //汇钱50
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "线程修改50为100的结果是" + atomicInteger.compareAndSet(50, 100));
        }, "3").start();

    }

    public static void ABAAnswer() {
        //取钱50线程1
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "线程修改100为50的结果是" + atomicStampedReference.compareAndSet(100, 50, 0, 1));
        }, "1").start();
        //取钱50线程2
        new Thread(() -> {
            Boolean flag = true;
            int sum = 0;
            while (!atomicStampedReference.compareAndSet(100, 50, 0, 1)) {
                sum++;
                if (sum == 10000) {
                    flag = false;
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + "线程修改100为50的结果是" + flag);
            System.out.println(Thread.currentThread().getName() + "线程查看结果为" + atomicStampedReference.getReference());
        }, "2").start();
        //汇钱50
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "线程修改50为100的结果是" + atomicStampedReference.compareAndSet(50, 100, 1, 2));
        }, "3").start();

    }
}
