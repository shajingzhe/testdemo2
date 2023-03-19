import java.util.Arrays;

public class KuaiSuPaiXu {
    public static class QuickSort_05 {
        public static void main(String[] args) {
            //int a[]={50,1,12,2};
            int a[] = {44, 38, 5, 47, 15, 27, 2, 46, 4, 19, 50, 48};
            quicksort(a, 0, a.length - 1);
            System.out.println(Arrays.toString(a));
        }

        private static void quicksort(int[] a, int low, int high) {
            int i, j;
            if (low > high) {
                return;
            }
            i = low;
            j = high;
            int temp = a[low];//基准位,low=length时，会报异常，java.lang.ArrayIndexOutOfBoundsException: 4 ，所以必须在if判断后面,就跳出方法。
            while (i < j) {
                //先从右边开始往左递减，找到比temp小的值才停止
                while (temp <= a[j] && i < j) {
                    j--;
                }
                //再看左边开始往右递增，找到比temp大的值才停止
                while (temp >= a[i] && i < j) {
                    i++;
                }
                //满足 i<j 就交换,继续循环while(i<j)
                if (i < j) {
                    int t = a[i];
                    a[i] = a[j];
                    a[j] = t;
                }
            }
            //最后将基准位跟  a[i]与a[j]相等的位置，进行交换,此时i=j
            a[low] = a[i];
            a[i] = temp;
            //左递归
            quicksort(a, low, j - 1);
            //右递归
            quicksort(a, j + 1, high);
        }
    }
}
