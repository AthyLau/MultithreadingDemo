import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Function:这是一个线程同步的辅助类，跟countdownlatch相反，这个是一直加到某个数字，才能继续往下执行
 * <p>
 * 先到的先等，到齐了开始执行某个线程
 * <p>
 * 相当于人到齐了才能开会，集齐七个龙珠才能召唤神龙
 *
 * @author liubing
 * Date: 2019/6/7 7:04 PM
 * @since JDK 1.8
 */
public class CyclicBarrierDemo {
    public static void main(String args[]) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, new Thread(() -> {
            System.out.println("召唤神龙");
        }));
        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "被悟空找到了！");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, SevenDragonball.getBall(i).toString()).start();
        }
    }

}

enum SevenDragonball {

    ONE(1, "一星龙珠"), TWO(2, "二星龙珠"), THREE(3, "三星龙珠"), FOUR(4, "四星龙珠"), FIVE(5, "五星龙珠"), SIX(6, "六星龙珠"), SEVEN(7, "七星龙珠");
    private Integer dragonballId;
    private String dragonballName;

    SevenDragonball() {
    }

    SevenDragonball(Integer dragonballId, String dragonballName) {
        this.dragonballId = dragonballId;
        this.dragonballName = dragonballName;
    }

    public static SevenDragonball getBall(Integer dragonballId) {
        SevenDragonball[] sevenDragonballs = SevenDragonball.values();
        for (SevenDragonball sevenDragonball : sevenDragonballs) {
            if (sevenDragonball.getDragonballId() == dragonballId) {
                return sevenDragonball;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return dragonballId + "  " + dragonballName + "  ";
    }

    public Integer getDragonballId() {
        return dragonballId;
    }

    public void setDragonballId(Integer dragonballId) {
        this.dragonballId = dragonballId;
    }

    public String getDragonballName() {
        return dragonballName;
    }

    public void setDragonballName(String dragonballName) {
        this.dragonballName = dragonballName;
    }
}
