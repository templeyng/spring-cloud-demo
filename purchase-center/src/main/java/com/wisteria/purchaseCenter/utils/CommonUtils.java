package com.wisteria.purchaseCenter.utils;

import java.util.Random;

public class CommonUtils {
    public static int loadRandomQuantity() {
        final int randomInt = new Random().nextInt(200);
        return randomInt + 20;
    }

    public static int loadRandomId() {
        final double random = Math.random();
        return (int) (random * 100000 / 1);
    }
}
