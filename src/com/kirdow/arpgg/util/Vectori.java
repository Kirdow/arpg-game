package com.kirdow.arpgg.util;

public class Vectori<T extends Vectori> {

    public static final Vectori ZERO = new Vectori();

    public final int ix, iy;

    public Vectori(int x, int y) {
        this.ix = x;
        this.iy = y;
    }

    public Vectori() {
        this(0, 0);
    }

    public T add(int x, int y) {
        return (T)new Vectori(ix + x, iy + y);
    }

    public T add(final Vectori other) {
        return add(other.ix, other.iy);
    }

    public T sub(int x, int y) {
        return (T)new Vectori(ix - x, iy - y);
    }

    public T sub(final Vectori other) {
        return sub(other.ix, other.iy);
    }

    public T mul(int other) {
        return (T)new Vectori(ix * other, iy * other);
    }

    public T div(int other) {
        if (other == 0) return (T)Vectori.ZERO;

        return (T)new Vectori(ix / other, iy / other);
    }

    public int length() {
        return (int)Math.sqrt(ix * ix + iy * iy);
    }

}
