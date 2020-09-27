package com.kirdow.arpgg.gfx;

import java.util.Arrays;

public class Screen {

    private int xTrans, yTrans;

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

    public void translate(int x, int y) {
        this.xTrans += x;
        this.yTrans += y;
    }

    public void resetTranslation() {
        this.xTrans = 0;
        this.yTrans = 0;
    }

    private void fillRect(int x0, int y0, int x1, int y1, int c) {
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
        x0 -= xTrans;
        y0 -= yTrans;

        fillRect(x0, y0, x0 + w, y0 + h, c);
    }

    public void drawTile(int x, int y, int tile) {
        x -= xTrans;
        y -= yTrans;

        int tx = tile % 16;
        int ty = tile / 16;
        int taPos = tx * 16 + ty * 16 * Textures.TILEMAP.w;

        for (int fy = 0; fy < 16; fy++) {
            int py = fy + y;
            if (py < 0 || py >= h) continue;
            for (int fx = 0; fx < 16; fx++) {
                int px = fx + x;
                if (px < 0 || px >= w) continue;

                int pixel = Textures.TILEMAP.pixels[fx + fy * Textures.TILEMAP.w + taPos];
                if (pixel == 0xFF00FF || pixel == 0x7F007F) continue;
                pixels[px + py * w] = pixel;
            }
        }
    }

    public void drawTexture(int x, int y, int w, int h, int u, int v, Screen texture) {
        this.drawTexture(x, y, w, h, u, v, texture, false);
    }

    public void drawTexture(int x, int y, int w, int h, int u, int v, Screen texture, boolean mirror) {
        x -= xTrans;
        y -= yTrans;

        int pixel;
        for (int fy = 0; fy < h; fy++) {
            int py = fy + y;
            if (py < 0 || py >= this.h)
                continue;

            int ty = fy + v;
            if (ty < 0 || ty >= texture.h)
                continue;

            for (int fx = 0; fx < w; fx++) {
                int px = fx + x;
                if (px < 0 || px >= this.w)
                    continue;

                int tx = fx + u;
                if (mirror) {
                    tx = u + w - fx - 1;
                }
                if (tx < 0 || tx >= texture.w)
                    continue;

                pixel = texture.pixels[tx + ty * texture.w];
                if (pixel == 0xFF00FF || pixel == 0x7F007F) continue;
                pixels[px + py * this.w] = pixel;
            }
        }
    }

    public void drawAnimation(int x, int y, int w, int h, int u, int v, int frameTime, int frameCount, Screen texture) {
        drawAnimation(x, y, w, h, u, v, frameTime, frameCount, texture, false);
    }

    public void drawAnimation(int x, int y, int w, int h, int u, int v, int frameTime, int frameCount, Screen texture, boolean mirror) {
        int frame = (int)(System.currentTimeMillis() % (frameTime * frameCount)) / frameTime;
        drawAnimationFrame(x, y, w, h, u, v, frame, texture, mirror);
    }

    public void drawAnimationFrame(int x, int y, int w, int h, int u, int v, int frame, Screen texture) {
        drawAnimationFrame(x, y, w, h, u, v, frame, texture, false);
    }

    public void drawAnimationFrame(int x, int y, int w, int h, int u, int v, int frame, Screen texture, boolean mirror) {
        drawTexture(x, y, w, h, u + w * frame, v, texture, mirror);
    }

}
