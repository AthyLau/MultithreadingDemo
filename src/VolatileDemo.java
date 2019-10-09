import java.util.concurrent.atomic.AtomicInteger;

/**
 * Function:volatile字段的可见性与原子性的验证
 * <p>
 * 1、验证volatile的可见性。两种方式。一种在MyData对象前加volatile字段，一种是在myData类的number字段前加volatile
 * 基本数据类型的成员变量也是在堆里面的。堆线程共享，加volatile字段就好使。静态成员变量在方法区，线程共享，加volatile字段也好使。好使=可见性没问题。
 * volatile只能修饰变量名与值都在共享区域的变量
 * <p>
 * 2、验证volatile不保证原子性
 * 如何解决这个不保证原子性的问题呢？
 * 1、synchronized字段
 * 2、AtomicInteger
 *
 * @author liubing
 * Date: 2019/5/27 5:12 PM
 * @since JDK 1.8
 */
public class VolatileDemo {
    static volatile MyData myData = new MyData();

    public static void main(String[] args) {
//        noAtomicityDemo();
        seeOKByVolatile();
    }

    public static void noAtomicityDemo() {
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.addPlus();
                    myData.addAtomicInteger();
                }
            }, String.valueOf(i)).start();
        }
        System.out.println(myData.number);
        System.out.println(myData.atomicInteger.get());
    }

    /**
     * 验证volatile的可见性
     */
    public static void seeOKByVolatile() {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "come in");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            myData.addTO60();
            myData.addAgeTO60();
            System.out.println(Thread.currentThread().getName() + "update myData.number = " + myData.age);
        }, "aaa").start();
        while (myData.age == 0) {

        }
        System.out.println(Thread.currentThread().getName() + "mission is over , myData.number = " + myData.age);
    }
}

class MyData {
    int number = 0;
    int age = 0;
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addAtomicInteger() {
        atomicInteger.getAndIncrement();
    }

    public void addAgeTO60() {
        this.age = 60;
    }

    public void addAgePlus() {
        age++;
    }

    public void addTO60() {
        this.number = 60;
    }

    public void addPlus() {
        number++;
    }
}