package com.wisteria.saleCenter.utils;

import java.util.Date;

public class CommonUtils {

    public static int loadIdBySys() {
        long nowTime = new Date().getTime();
        double random = Math.random();
        int a = (int) (random * 100000000 / 1);
        int b = (int) (nowTime % 1000000000);
        int count = a + b;
        return count;
    }
}
