import java.util.concurrent.CountDownLatch;

/**
 * Function:这是一个同步辅助类的demo，演示了CountDownLatch的小case，
 * 这个东西类似于一个计数器，给他一个初始值，然后有个方法可以一直减1，await方法
 * 是判断目前计数是否为0，为0的话才能往下继续执行，不为0就卡住，某个线程调用了await方法后会校验目前计数器内的值
 * 如果是0就会继续执行，不是0就把线程挂起阻塞，等到为0就再唤醒线程。
 * 本demo顺带着加了一个枚举类，复习了枚举类的有关知识
 *
 * @author liubing
 * Date: 2019/6/7 5:47 PM
 * @since JDK 1.8
 */
public class CountDownLatchDemo {
    static CountDownLatch countDownLatch = new CountDownLatch(6);

    public static void main(String args[]) {

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "被秦始皇所灭");
                countDownLatch.countDown();
            }, SixCounty.getCountyById(i).toString()).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("秦始皇灭六国");
    }

}

enum SixCounty {

    ONE(1, "齐国", "齐桓公"),
    TWO(2, "楚国", "楚王"),
    THREE(3, "燕国", "小燕子"),
    FOUR(4, "韩国", "金中分"),
    FIVE(5, "赵国", "赵无极"),
    SIX(6, "魏国", "魏小狗");
    private Integer countryId;
    private String countryName;
    private String kingName;

    SixCounty() {
    }

    SixCounty(Integer countryId, String countryName, String kingName) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.kingName = kingName;
    }

    @Override
    public String toString() {
        return "SixCounty{" +
                "countryId=" + countryId +
                ", countryName='" + countryName + '\'' +
                ", kingName='" + kingName + '\'' +
                '}';
    }

    public static SixCounty getCountyById(Integer countryId) {
        SixCounty[] counties = SixCounty.values();
        for (SixCounty element : counties) {
            if (element.getCountryId() == countryId) {
                return element;
            }
        }
        return null;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getKingName() {
        return kingName;
    }

    public void setKingName(String kingName) {
        this.kingName = kingName;
    }
}