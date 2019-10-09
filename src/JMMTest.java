/**
 * Function:
 *
 * @author liubing
 * Date: 2019/5/31 5:59 PM
 * @since JDK 1.8
 */
public class JMMTest {
    //volatile 字段只能修饰变量名跟值都在线程共享区域的变量
    //由此可见基本数据类型的成员变量的变量名跟值都在堆中,引用类型也是一样的
    //而加了static的静态成员变量都在方法区中
    volatile int mmm = 1;
    volatile Integer i = 0;
    volatile JMMTest jm = new JMMTest();
    volatile static int mm = 1;
    volatile static Integer asd = 0;
    volatile static JMMTest jmm = new JMMTest();

    public static void mian(String args[]) {


    }

    /**
     * 多线程条件下，线程不可访问其他线程内部的变量，只能访问共享区域的变量。此处是加了static的原因导致只能访问static的变量
     * j为局部基本数据类型变量。jx为局部基本数据类型的包装类型的变量
     * 本质上基本数据类型的包装类型与基本数据类型在内存中的位置是一样的都是在栈中。
     */
    public static void one() {
        int j = 0;
        Integer jx = new Integer(1);
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
//                j++;
//                jx++;
                mm++;
                asd++;
                jmm = null;
            }, String.valueOf(i)).start();
        }
    }

    /**
     * volatile 字段只能修饰变量名跟值都在线程共享区域的变量
     */
    public static void two() {
//        volatile Integer j = 2;
//        volatile JMMTest jmmTest = new JMMTest();

    }
}
