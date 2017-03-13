package com.test.sort;

/**
 * @author yan
 * @Description:
 * @date 2017/3/11
 */
public class InsertionSort {

    public static void sort(Comparable[] a){
        int n = a.length;
        for(int i = 0; i < n; i++){
            for(int j = i; j > 0 && a[j].compareTo(a[j - 1]) > 0; j--){
                Comparable t = a[j];
                a[j] = a[j - 1];
                a[j - 1] = t;
            }
        }
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{4,9,2,8};
        sort(a);
        for(Integer b : a){
            System.out.println(b);
        }

    }

}
