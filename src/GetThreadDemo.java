/**
 * Function:
 *
 * @author liubing
 * Date: 2019/6/11 6:17 PM
 * @since JDK 1.8
 */
public class GetThreadDemo {
    public static void main(String args[]) {
        MineThread1 mineThread1 = new MineThread1();
        mineThread1.start();

        MineThread2 mineThread2 = new MineThread2();
        Thread t = new Thread(mineThread2);
        t.start();
    }
}

class MineThread1 extends Thread {
    public void run() {
        System.out.println("!111111");
    }
}

class MineThread2 implements Runnable {

    @Override
    public void run() {
        System.out.println("222222222");
    }
}