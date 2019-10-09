import java.util.concurrent.Semaphore;

/**
 * Function:信号量类,多个线程抢多个资源的时候会用到这个类。
 * 比如小米的秒杀系统。10000个人抢100个新款手机
 * 比如多个车抢多个停车位置的时候。
 *
 * @author liubing
 * Date: 2019/6/7 7:23 PM
 * @since JDK 1.8
 */
public class SemaPhoreDemo {

    public static void main(String args[]) {
        Semaphore semaphore = new Semaphore(3);//资源数量为3，3个车位
        for (int i = 1; i <= 6; i++) {
            //一共有六辆车
            new Thread(() -> {
                try {
                    //抢占一个车位
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢占一个车位");
                    //占有三秒，停车停三秒
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //离开车位
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + "占有三秒后，离开车位");
                }
            }, String.valueOf(i)).start();
        }
    }


}
