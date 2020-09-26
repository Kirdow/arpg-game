package com.kirdow.arpgg.util;

public class DataUtils {

    public static boolean bits(int data, int bits) {
        return bits(data, bits, bits);
    }

    public static boolean bits(int data, int mask, int bits) {
        return (data & mask) == bits;
    }

}
