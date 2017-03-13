package com.test.sort;

/**
 * @author yan
 * @Description:
 * @date 2017/3/12
 */
public class MergeSort {

    private static Comparable[] aux;

    public static void sort(Comparable[] a){
        aux = new Comparable[a.length];
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int start, int end){
        //将数组a[start..end]排序
        if(end <= start)
            return;
        int mid = start + (end - start) / 2;
        sort(a, start, mid);
        sort(a, mid + 1, end);
        merge(a, start, mid, end);
    }

    public static void merge(Comparable[] a, int start, int mid, int end){
        //归并
        int i = start;
        int j = mid + 1;
        //保存副本
        for(int k = start; k <= end; k++)
            aux[k] = a[k];

        for(int k = start; k <= end; k++){
            if(i > mid){
                a[k] = aux[j++];
            } else if(j > end){
                a[k] = aux[i++];
            } else if(aux[j].compareTo(aux[i]) > 0){
                a[k] = a[j++];
            } else {
                a[k] = a[i++];
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
