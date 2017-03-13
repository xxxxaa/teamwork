package com.test.sort;

import java.util.Arrays;

/**
 * @author yan
 * @Description:选择排序
 * @date 2017/3/11
 */
public class SelectionSort {

    public static void sort(Comparable[] a){
        int n = a.length;
        for(int i = 0; i < n; i++){
            //将a[i]和a[+1]中最小的元素进行交换
            int min = i;
            for(int j = i + 1; j < n; j++){
                if(a[j].compareTo(a[min]) > 0){
                    min = j;
                }
                Comparable t = a[i];
                a[i] = a[min];
                a[min] = t;
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
