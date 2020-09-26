package com.kirdow.arpgg.util;

public class Box {

    public final int x, y, w, h;

    public Box(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Box(int x, int y, Vectori bounds) {
        this(x, y, bounds.ix, bounds.iy);
    }

    public Box(Vectori pos, Vectori bounds) {
        this(pos.ix, pos.iy, bounds);
    }

    public Box(Box other) {
        this(other.x, other.y, other.w, other.h);
    }

    public boolean intersect(int x, int y, int w, int h) {
        return this.x < x + w && this.y < y + h && x < this.x + this.w && y < this.y + this.h;
    }

    public boolean intersect(Box other) {
        return intersect(other.x, other.y, other.w, other.h);
    }

    public boolean isInside(int x, int y) {
        return x >= this.x && y >= this.y && x < this.x + this.w && y < this.y + this.h;
    }

    public boolean isInside(Vectori v) {
        return isInside(v.ix, v.iy);
    }

}
