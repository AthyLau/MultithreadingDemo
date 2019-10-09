/**
 * Function:快拍
 *
 * @author liubing
 * Date: 2019/6/11 11:23 PM
 * @since JDK 1.8
 */
public class QuicklySortDemo {
    static int[] p = {5, 4, 3, 2, 5, 1, 7, 3, 8, 3, 4, 5, 6};

    public static void main(String args[]) {
        sort(0, p.length - 1);
        for (int i = 0; i < p.length; i++) {
            System.out.print(p[i] + "  ");
        }
    }

    public static void sort(int left, int right) {
        int start = left;
        int len = right;
        int key = p[left];
        while (left < right) {
            while (left < right && p[right] >= key) {
                --right;
            }
            p[left] = p[right];
            while (left < right && p[left] <= key) {
                ++left;
            }
            p[right] = p[left];
        }
        p[left] = key;
        if (right - 1 > start) {
            sort(start, right - 1);
        }
        if (len > right + 1) {
            sort(right + 1, len);
        }
    }

    public static int partition(int low, int high) {
        int key = p[low];
        while (low < high) {
            while (low < high && p[high] >= key) {
                --high;
            }
            p[low] = p[high];
            while (low < high && p[low] <= key) {
                ++low;
            }
            p[high] = p[low];
        }
        p[low] = key;
        return low;
    }
}
