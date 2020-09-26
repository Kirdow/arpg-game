package com.kirdow.arpgg.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Perlin {

    private static final int[] PRIMES = new int[]{ 995615039, 600173719, 701464987, 831731269, 162318869, 136250887, 174329291, 946737083, 245679977, 362489573, 795918041, 350777237, 457025711, 880830799, 909678923, 787070341, 177340217, 593320781, 405493717, 291031019, 391950901, 458904767, 676625681, 424452397, 531736441, 939683957, 810651871, 997169939, 842027887, 423882827 };
    private static final Random primeRNG = new Random();

    private int[] primes = new int[PRIMES.length];

    private int octaves = 7;
    private double persistence = 0.5;
    private double multiplier = 1.0;

    public Perlin() {
        Arrays.stream(PRIMES).forEach(p -> {
            int pos = primeRNG.nextInt(PRIMES.length);
            while (primes[pos] != 0) {
                pos = (pos + 1) % PRIMES.length;
            }
            primes[pos] = p;
        });
    }

    public int getPrimeMax() {
        return PRIMES.length / 3;
    }

    public void setOctaves(int octaves) {
        this.octaves = octaves % getPrimeMax();
    }

    public void setPersistence(double persistence) {
        this.persistence = persistence;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    private int prime(int i, int j) {
        int max = primes.length / 3;
        return primes[(j % 3) + (i % max) * 3];
    }

    public double noise(int i, int x, int y) {
        int n = x + y * 57;
        n = (n << 13) ^ n;
        int a = prime(i, 0), b = prime(i, 1), c = prime(i, 2);
        int t = (n * (n * n * a + b) + c) & 0x7fffffff;
        return 1.0 - (double)t / 1073741824.0;
    }

    public double smoothedNoise(int i, int x, int y) {
        double corners = (noise(i, x - 1, y - 1) + noise(i, x + 1, y - 1) + noise(i, x - 1, y + 1) + noise(i, x + 1, y + 1)) / 16.0,
                sides = (noise(i, x - 1, y) + noise(i, x + 1, y) + noise(i, x, y - 1) + noise(i, x, y + 1)) / 8.0,
                center = noise(i, x, y) / 4.0;
        return corners + sides + center;
    }

    public double interpolate(double a, double b, double x) {
        double ft = x * 3.1415927,
                f = (1.0 - Math.cos(ft)) * 0.5;
        return a * (1.0 - f) + b * f;
    }

    public double interpolatedNoise(int i, double x, double y) {
        int integerX = (int)x;
        int integerY = (int)y;
        double fractionalX = x - integerX;
        double fractionalY = y - integerY;

        double v1 = smoothedNoise(i, integerX, integerY),
                v2 = smoothedNoise(i, integerX + 1, integerY),
                v3 = smoothedNoise(i, integerX, integerY + 1),
                v4 = smoothedNoise(i, integerX + 1, integerY + 1),
                i1 = interpolate(v1, v2, fractionalX),
                i2 = interpolate(v3, v4, fractionalX);
        return interpolate(i1, i2, fractionalY);
    }

    public double valueNoise2d(double x, double y) {
        double total = 0,
                frequency = Math.pow(2, octaves),
                ampitude = 1;
        int pmax = PRIMES.length / 3;
        for (int i = 0; i < octaves; ++i) {
            frequency /= 2;
            ampitude += persistence;
            total += interpolatedNoise(i % pmax, x / frequency, y / frequency) * ampitude;
        }
        return total / frequency;
    }

    public double[] generate(int w0, int h0, int x0, int y0) {
        int w = w0 + 2;
        int h = h0 + 2;
        int x = x0 - 1;
        int y = y0 - 1;

        double[] map = new double[w*h];

        final double XO = 100000.0;
        final double YO = 10000.0;

        for (int ym = 0; ym < h; ym++) {
            for (int xm = 0; xm < w; xm++) {
                double noise = valueNoise2d(xm + x + XO, ym + y + YO);
                map[xm + ym * w] = noise;
            }
        }

        for (int i = 0; i < w*h; i++)
            map[i] *= multiplier;

        double[] fmap = new double[w0 * h0];
        for (int ym = 0; ym < h0; ym++) {
            for (int xm = 0; xm < w0; xm++) {
                double noise = map[(xm + 1) + (ym + 1) * w];
                fmap[xm + ym * w0] = noise;
            }
        }

        map = null;

        return fmap;
    }

}
