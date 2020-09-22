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

    public void fillRect(int x0, int y0, int x1, int y1, int c) {
        int startX = x0 < 0 ? 0 : x0;
        int startY = y0 < 0 ? 0 : y0;
        int endX = x1 >= w ? (w - 1) : x1;
        int endY = y1 >= h ? (h - 1) : y1;

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                pixels[x + y * w] = c;
            }
        }
    }

    public void fillSizedRect(int x0, int y0, int w, int h, int c) {
        fillRect(x0, y0, x0 + w, y0 + h, c);
    }

}
