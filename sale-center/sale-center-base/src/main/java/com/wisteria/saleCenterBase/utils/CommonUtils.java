package com.wisteria.saleCenterBase.utils;

public class CommonUtils {

    public static int loadIdBySys() {
        final double random = Math.random();
        return (int) (random * 100000 / 1);
    }

}