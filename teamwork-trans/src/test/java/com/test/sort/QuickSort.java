package com.test.sort;

/**
 * @author yan
 * @Description:
 * @date 2017/3/12
 */
public class QuickSort {

    public static void sort(Comparable[] a){
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int start, int end){
        if(end <= start)
            return;
        int j = partition(a, start, end);
        sort(a, start, j - 1);
        sort(a, j + 1, end);
    }

    private static int partition(Comparable[] a, int start, int end){
        int i = start;
        int j = end + 1;
        Comparable v = a[start];
        while (true){
            while (a[++i].compareTo(v) > 0)
                if(i == end)
                    break;
            while (v.compareTo(a[--j]) > 0)
                if(j == start)
                    break;
            if(i >= j)
                break;
            Comparable t = a[i];
            a[i] = a[j];
            a[j] = t;
        }
        Comparable t = a[start];
        a[start] = a[j];
        a[j] = t;
        return j;
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{4,9,2,8};
        sort(a);
        for(Integer b : a){
            System.out.println(b);
        }

    }
}
