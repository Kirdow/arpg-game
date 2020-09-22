package com.kirdow.arpgg.gfx;

import java.util.Arrays;

public class Screen {

    public final int w, h;
    public final int[] pixels;

    public Screen(int w, int h) {
        this.w = w;
        this.h = h;
        this.pixels = new int[w*h];
    }

    public void clear(int color) {
        Arrays.fill(pixels, color);
    }

    public void flush(int[] other) {
        final int L = Math.min(other.length, pixels.length);

        System.arraycopy(pixels, 0, other, 0, L);
    }

}
