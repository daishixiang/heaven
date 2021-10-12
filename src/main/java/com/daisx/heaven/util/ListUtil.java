package com.daisx.heaven.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: daisx
 * @Date: 2021/1/27 15:36
 */
public class ListUtil {
    /**
     * 差集(基于API解法) 适用于小数据量
     * 求List1中有的但是List2中没有的元素
     * 时间复杂度 O(list1.size() * list2.size())
     */
    public static List<Long> subList(List<Long> list1, List<Long> list2) {
        list1.removeAll(list2);
        return list1;
    }

    /**
     * 差集(基于常规解法）优化解法1 适用于中等数据量
     * 求List1中有的但是List2中没有的元素
     * 空间换时间降低时间复杂度
     * 时间复杂度O(Max(list1.size(),list2.size()))
     */
    public static List<Long> subList1(List<Long> list1, List<Long> list2) {
        //空间换时间 降低时间复杂度
        Map<Long, Long> tempMap = new HashMap<>();
        for(Long str:list2){
            tempMap.put(str,str);
        }
        //LinkedList 频繁添加删除 也可以ArrayList容量初始化为List1.size(),防止数据量过大时频繁扩容以及数组复制
        List<Long> resList = new LinkedList<>();
        for(Long str:list1){
            if(!tempMap.containsKey(str)){
                resList.add(str);
            }
        }
        return resList;
    }

    /**
     * 差集(基于java8新特性)优化解法2 适用于大数据量
     * 求List1中有的但是List2中没有的元素
     */
    public static List<Long> subList2(List<Long> list1, List<Long> list2) {
        Map<Long, Long> tempMap = list2.parallelStream().collect(Collectors.toMap(Function.identity(), Function.identity(), (oldData, newData) -> newData));
        return list1.parallelStream().filter(str->{
            return !tempMap.containsKey(str);
        }).collect(Collectors.toList());
    }



    /**
     * 交集(基于API解法) 适用于小数据量
     * 求List1和List2中都有的元素
     * 时间复杂度 O(list1.size() * list2.size())
     */
    public static List<Long> intersectList(List<Long> list1, List<Long> list2){
        list1.retainAll(list2);
        return list1;
    }
    /**
     * 交集(基于常规解法) 优化解法1  适用于中等数据量
     * 求List1和List2中都有的元素
     * 时间复杂度O(Max(list1.size(),list2.size()))
     */
    public static List<Long> intersectList1(List<Long> list1, List<Long> list2){
        //空间换时间 降低时间复杂度
        Map<Long, Long> tempMap = new HashMap<>();
        for(Long str:list2){
            tempMap.put(str,str);
        }
        //LinkedList 频繁添加删除 也可以ArrayList容量初始化为List1.size(),防止数据量过大时频繁扩容以及数组复制
        List<Long> resList = new LinkedList<>();
        for(Long str:list1){
            if(tempMap.containsKey(str)){
                resList.add(str);
            }
        }
        return resList;
    }
    /**
     * 交集(基于java8新特性)优化解法2 适用于大数据量
     * 求List1和List2中都有的元素
     */
    public static List<Long> intersectList2(List<Long> list1, List<Long> list2){
        Map<Long, Long> tempMap = list2.parallelStream().collect(Collectors.toMap(Function.identity(), Function.identity(), (oldData, newData) -> newData));
        return list1.parallelStream().filter(str->{
            return tempMap.containsKey(str);
        }).collect(Collectors.toList());
    }


    /**
     * 并集(不去重)
     * 合并list1和list2 不考虑去除重复元素
     * 数组扩容 数组copy
     * @param list1
     * @param list2
     * @return
     */
    public static List<Long> mergeList(List<Long> list1, List<Long> list2){
        list1.addAll(list2);
        return list1;
    }

    /**
     * 并集(去重) 基于API解法
     * 合并list1和list2 去除重复元素
     * 时间复杂度主要取决于removeAll 取差集 O(list1.size() * list2.size())
     */
    public static List<Long> distinctMergeList(List<Long> list1, List<Long> list2){
        //第一步 先求出list1与list2的差集
        list1.removeAll(list2);
        //第二部 再合并list1和list2
        list1.addAll(list2);
        return list1;
    }
    /**
     * 并集(去重) 基于Java8新特性 适用于大数据量
     * 合并list1和list2 去除重复元素
     */
    public static List<Long> distinctMergeList1(List<Long> list1, List<Long> list2){
        //第一步 先求出list1与list2的差集
        list1 = subList2(list1,list2);
        //第二部 再合并list1和list2
        list1.addAll(list2);
        return list1;
    }


    public static void main(String[] args) {
        List<Long> p=new ArrayList<>();
        p.add(11L);
        p.add(21L);
        p.add(31L);

        test(p);
    }


    public static void test(List<Long> p){
        List<Long> e=new ArrayList<>();
        e.add(1L);
        e.add(21L);
        e.add(3L);

        List<Long> d=new ArrayList<>();
        List<Long> a=new ArrayList<>();
        d = subList2(e, p);

        a=subList2(p,e);

        System.out.println("========a========");
        for (Long aLong : a) {
            System.out.println(aLong);
        }

        System.out.println("========d========");
        for (Long aLong : d) {
            System.out.println(aLong);
        }

        System.out.println("========p========");
        for (Long aLong : p) {
            System.out.println(aLong);
        }
    }
}
