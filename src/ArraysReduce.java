import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Function:
 *
 * @author liubing
 * Date: 2019/4/16 7:25 PM
 * @since JDK 1.8
 */
//给出一个有n(0 < n <= 10000)个整数的数组A（-10000 <= Ai <=10000) ， 请问是否可以至多修改一个元素， 使这个数组成为非递减数组。
public class ArraysReduce {
    public static String result = "";

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String result = "";
        String str = "";
        while (!(str = sc.nextLine()).equals("")) {
            String[] strs = str.split(" ");
            int[] arr = new int[strs.length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = Integer.parseInt(strs[i]);
            }
            if (result.equals("")) {
                result = result + getResult(arr);
            } else {
                result = result + "\n" + getResult(arr);
            }

        }
        System.out.println(result);
    }

    public static String getResult(int[] arr) {
        return "No";
    }

}
