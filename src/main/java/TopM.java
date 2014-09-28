/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class TopM {

    public static Comparable select(Comparable[] a, int k) {
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (j == k) return a[k];
            else if (j > k) hi = j - 1;
            else if (j < k) lo = j + 1;
        }
        return a[k];
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        Comparable sential = a[lo];
        int i = lo;
        int j = hi + 1;
        while(true ){
            while( less(a[++i],sential))
                if(i == hi ) break;
            while( less(sential,a[--j]))
                if(j == lo) break;;
            if( i>= j)
                break;
            exch(a,i,j);
        }
        exch(a,lo,j);
        return j;
    }

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    public static void main(String[] args) {
        Integer[] arrary = new Integer[]{1,2,4,3,6,7,100,8,5,0};
        System.out.println(select(arrary,10));
    }

}
