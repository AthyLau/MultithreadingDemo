/**
 * Function:演示指令重排序的Demo
 *
 * @author liubing
 * Date: 2019/5/29 4:38 PM
 * @since JDK 1.8
 */
public class InstructionReorderDemo {
    //单线程情况下先执行method1方法再执行method2方法
    //首先i = 10;然后flag = true;结果是15

    //多线程情况下呢，多个线程一起执行method1时，指令重排序，某个线程先执行了flag=true;
    int i = 0;
    Boolean flag = false;

    public void method1() {
        this.i = 10;
        this.flag = true;
    }

    public void method2() {
        if (flag) {
            i = i + 5;
            System.out.println(i);
        }
    }

}

class ReorderingDemo {

    static int x = 0, y = 0, a = 0, b = 0;

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 100; i++) {
            x = y = a = b = 0;
            Thread one = new Thread() {
                public void run() {
                    a = 1;
                    x = b;
                }
            };
            Thread two = new Thread() {
                public void run() {
                    b = 1;
                    y = a;
                }
            };
            one.start();
            two.start();
            one.join();
            two.join();
            System.out.println(x + " " + y);
        }
    }

}
