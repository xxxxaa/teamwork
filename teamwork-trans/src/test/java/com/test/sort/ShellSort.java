package com.test.sort;

/**
 * @author yan
 * @Description:
 * @date 2017/3/12
 */
public class ShellSort {

    public static void sort(Comparable[] a){
        int n = a.length;
        int h = 1;
        while (h < n / 3)
            h = 3 * h + 1;
        while (h >= 1){
            for(int i = h; i < n; i++){
                for(int j = i; j >= h && a[j].compareTo(a[j - h]) > 0; j -= h){
                    Comparable t = a[j];
                    a[j] = a[j - h];
                    a[j - h] = t;
                }
            }
            h = h / 3;
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
