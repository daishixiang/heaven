package com.daisx.heaven.util;

/**
 * @Author: daisx
 * @Date: 2021/10/22 13:24
 */
public class MathUntil {
    /**
     * 用于判断一个数是否是素数，如果是，返回true，否则返回false
     *
     * @param a 输入的值
     * @return true  false
     */
    public static boolean isPrime(int a) {
        boolean flag = true;
        //素数不小于2
        if (a < 2) {
            return false;
        } else {
            for (int i = 2; i <= Math.sqrt(a); i++) {
                //若能被整除则说明不是素数，返回false
                if (a % i == 0) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        System.out.println(isPrime(37));
    }
}
