import java.util.*;

/**
 * Function:
 *
 * @author liubing
 * Date: 2019/3/30 5:44 PM
 * @since JDK 1.8
 */
public class HappyHands {

    private static class Node {
        private Integer data;
        private Node left;
        private Node right;

        public Node() {
        }

        public Node(int data) {
            this.data = data;
        }

        public Integer getData() {
            return data;
        }

        public void setData(Integer data) {
            this.data = data;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }

    public static boolean isBST() {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        String[] datas = str.split(",");
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            nodes.add(new Node(Integer.parseInt(datas[i])));
        }
        for (int i = 0; i + i + 2 < nodes.size() && i + i + 1 < nodes.size(); i++) {
            nodes.get(i).setLeft(nodes.get(i + i + 1));
            nodes.get(i).setRight(nodes.get(i + i + 2));
        }
        Node root = nodes.get(0);
        return inOrder(root);
    }

    public static boolean inOrder(Node root) {
        int mydata = Integer.MIN_VALUE;
        if (root != null) {
            Stack<Node> nodes = new Stack<>();
            while (!(nodes.size() == 0 || root != null)) {
                if (root != null) {
                    nodes.push(root);
                    root = root.left;
                } else {
                    root = nodes.pop();
                    if (root.data < mydata) {
                        return false;
                    }
                }
                mydata = root.data;
                root = root.right;
            }
        }
        return true;
    }


    public static void main(String args[]) {

        System.out.println(isBST());
//        rabbit1();
    }

    public static void rabbit1() {
        Scanner in = new Scanner(System.in);
        int m = 0;
        int n = 0;
        int k = 0;
        while (in.hasNextInt()) {
            m = in.nextInt();
            n = in.nextInt();
            k = in.nextInt();
            break;
        }
        int sum = 0;
        int d = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                d = getSum(i) + getSum(j);
                if (d <= k) {
                    sum++;
                }
            }
        }
        System.out.print(sum);
    }

    public static Integer getSum(Integer i) {
        Integer sum;
        String str = i.toString();
        char c[] = str.toCharArray();
        if (str.length() > 1) {
            sum = 0;
            for (int x = 0; x < c.length; x++) {
                sum = sum + Integer.parseInt(c[x] + "");
            }
        } else {
            sum = i;
        }
        return sum;
    }
}


/**
 * //    public static int[] getOneOrderArray(int a[],int b[]) {
 * //        //定义一个新数组，长度为两个数组长度之和
 * //        int arr[] = new int[a.length+b.length];
 * //        //i:a数组下标    j：b数组下标  k：新数组下标
 * //        int i=0,j=0,k=0;
 * ////                按位循环比较两个数组，较小元素的放入新数组，下标加一（注意，较大元素对应的下标不加一），直到某一个下标等于数组长度时退出循环
 * //        while(i<a.length && j<b.length)
 * //            if(a[i] <= b[j]) {
 * //                arr[k++] = a[i++];
 * //                System.out.println(arr);
 * //                System.out.println();
 * //            }else{
 * //                arr[k++] = b[j++];
 * //            }
 * //        /* 后面连个while循环是用来保证两个数组比较完之后剩下的一个数组里的元素能顺利传入 *
 * //         * 此时较短数组已经全部放入新数组，较长数组还有部分剩余，最后将剩下的部分元素放入新数组，大功告成
 */
//        while(i < a.length)
//            arr[k++] = a[i++];
//        while(j < b.length)
//            arr[k++] = b[j++];
//        return arr;
//    }
//    public static void getCharNumber(){
//        Scanner sc = new Scanner(System.in);
//        String str = sc.nextLine();
//        char[] chars = str.toCharArray();
//        String resultString = chars[0]+"";
//        int lastNum = 1;
//        for(int i = 1; i< chars.length;i++){
//            if(chars[i]==chars[i-1]){
//                lastNum = lastNum+1;
//            }else{
//                if(lastNum!=1){
//                    resultString = resultString+lastNum+chars[i];
//                }else{
//                    resultString = resultString+chars[i];
//                }
//                lastNum = 1;
//            }
//        }
//        if(lastNum!=1){
//            resultString = resultString+lastNum;
//        }
//        System.out.println(resultString);
//    }
//    public int climbStairs(int n) {
//        // write your code here
//        int dp[]=new int[n+1];
//        if(n==0){
//            return 1;
//        }
//        dp[0]=1;
//        dp[1]=1;
//        if(n==1){
//            return 1;
//        }else{
//            for(int i=2;i<=n;i++){
//                dp[i]=dp[i-1]+dp[i-2];
//            }
//            return dp[n];
//        }
//    }
//    **/