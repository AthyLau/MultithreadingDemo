import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Function:
 *
 * @author liubing
 * Date: 2019/7/4 3:29 PM
 * @since JDK 1.8
 */
public class Test {
    private static List<String> list = new Vector<>();

    public static void addString() {
        list.add("1");
//        System.out.println("线程名字是："+Thread.currentThread().getName());
        System.out.println(list);

    }

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                Test.addString();
            }, i + "").start();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
    }
}
