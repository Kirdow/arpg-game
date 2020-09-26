package com.kirdow.arpgg.util;

public class Vectorf extends Vectori<Vectorf> {

    public static final Vectorf ZERO = new Vectorf();

    public final float x, y;

    public Vectorf(float x, float y) {
        super((int)x, (int)y);
        this.x = x;
        this.y = y;
    }

    public Vectorf() {
        this(0, 0);
    }

    public Vectorf addf(float x, float y) {
        return new Vectorf(this.x + x, this.y + y);
    }

    public Vectorf addf(final Vectorf p) {
        return addf(p.x, p.y);
    }

    public Vectorf subf(float x, float y) {
        return new Vectorf(this.x - x, this.y - y);
    }

    public Vectorf subf(final Vectorf p) {
        return subf(p.x, p.y);
    }

    public Vectorf mulf(float x) {
        return new Vectorf(this.x * x, this.y * x);
    }

    public Vectorf divf(float f) {
        if (f == 0.0f) return ZERO;

        return new Vectorf(this.x / f, this.y / f);
    }

    public float lengthf() {
        return (float)Math.sqrt(x * x + y * y);
    }

    public int length() {
        return (int)Math.sqrt(x * x + y * y);
    }

    public Vectorf normalize() {
        float len = lengthf();
        if (len == 0.0f) return ZERO;

        float x = this.x / len;
        float y = this.y / len;

        return new Vectorf(x, y);
    }

}
