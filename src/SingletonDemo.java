/**
 * Function:线程安全的单例模式与线程不安全的单例模式 ！！！
 *
 * @author liubing
 * Date: 2019/5/29 5:23 PM
 * @since JDK 1.8
 */
public class SingletonDemo {

    private static volatile SingletonDemo singletonDemo;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "SingletonDemo");
    }

    //DCL模式（double check lock）双重检测模式  双端检索机制
    public static SingletonDemo getInstance() {
        if (singletonDemo == null) {
            synchronized (SingletonDemo.class) {
                if (singletonDemo == null) {
                    singletonDemo = new SingletonDemo();
                }
            }
        }
        return singletonDemo;
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++) {
            new Thread(() -> {
                System.out.println(SingleDemo.getInstance() == SingleDemo.getInstance());
                System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
            }, String.valueOf(0)).start();
        }

    }
}

/**
 * 线程不安全的单例模式
 */
class SingleDemo {
    private static SingleDemo singleDemo;

    private SingleDemo() {
        System.out.println(Thread.currentThread().getName() + "SingleDemo");
    }

    public static SingleDemo getInstance() {
        if (singleDemo == null) {
            singleDemo = new SingleDemo();
        }
        return singleDemo;
    }
}