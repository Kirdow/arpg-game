package com.kirdow.arpgg.gfx;

public class Font {

    private static Screen fontTex = null;
    private static int[] fontWidth = null;

    private static int[] getFontWidth() {
        if (fontWidth != null)
            return fontWidth;

        int[] widths = new int[256];

        for (int y = 0; y < 128; y+=8) {
            int yp = y / 8;
            for (int x = 0; x < 128; x+=8) {
                int xp = x / 8;

                int width = -1;
                for (int fx = 7; fx >= 0; fx--) {
                    boolean found = false;
                    for (int fy = 0; fy < 8; fy++) {
                        int pixel = Textures.FONT.pixels[x + fx + (y + fy) * Textures.FONT.w];

                        if (pixel == 0x7F007F || pixel == 0xFF00FF)
                            continue;

                        found = true;
                        break;
                    }

                    if (!found) {
                        --width;
                    } else {
                        width += 9;
                        break;
                    }
                }

                if (width >= 0) {
                    widths[xp + yp * 16] = width;
                } else {
                    widths[xp + yp * 16] = -1;
                }
            }
        }

        widths[0x20] = 7;

        return fontWidth = widths;
    }

    public static int getCharWidth(char c) {
        int[] widths = getFontWidth();

        return widths[c % 256];
    }

    public static int getStringWidth(String text) {
        int width = 0;
        for (char c : text.toUpperCase().toCharArray()) {
            width += getCharWidth(c);
        }
        return width;
    }

    public static float channelConvert(int c) {
        c &= 0xFF;

        return (float)c / 255.0f;
    }

    public static int channelConvert(float c) {
        c = (c < 0.0f ? 0.0f : (c > 1.0f ? 1.0f : c));

        return (int)(c * 255.0f);
    }

    public static int shadowColor(int color) {
        final float SHADOW_CONST = 0.35f;

        float r = channelConvert(color >> 16) * SHADOW_CONST;
        float g = channelConvert(color >> 8) * SHADOW_CONST;
        float b = channelConvert(color) * SHADOW_CONST;

        return channelConvert(r) << 16 | channelConvert(g) << 8 | channelConvert(b);
    }

    public static int drawShadowCentered(String text, Screen fb, int x, int y, int color, int pixelSize) {
        int width = getStringWidth(text);
        if (width > 0) ++width;

        x -= (width * pixelSize) / 2;

        return drawShadow(text, fb, x, y, color, pixelSize);
    }

    public static int drawShadow(String text, Screen fb, int x, int y, int color, int pixelSize) {
        draw(text, fb, x + pixelSize, y + pixelSize, shadowColor(color), pixelSize);
        int width = draw(text, fb, x, y, color, pixelSize);
        return width > 0 ? (width + pixelSize) : width;
    }

    public static int drawCentered(String text, Screen fb, int x, int y, int color, int pixelSize) {
        x -= (getStringWidth(text) * pixelSize) / 2;

        return draw(text, fb, x, y, color, pixelSize);
    }

    public static int draw(String text, Screen fb, int x, int y, int color, int pixelSize) {
        text = text.toUpperCase();

        int total = 0;
        int xpos = x;
        for (char c : text.toCharArray()) {
            int width = getCharWidth(c);
            if (width == -1)
                continue;

            draw(c, fb, xpos, y, color, pixelSize);

            xpos += pixelSize * width;
            total += pixelSize * width;
        }

        return total;
    }

    public static void draw(char c, Screen fb, int x, int y, int color, int pixelSize) {
        int cX = (c % 16) * 8;
        int cY = (c / 16) * 8;

        int sy, sx, spx, spy;
        for (int xp = 0; xp < 8; xp++) {
            spx = xp * pixelSize;
            for (int yp = 0; yp < 8; yp++) {
                spy = yp * pixelSize;

                int pixel = Textures.FONT.pixels[cX + xp + (cY + yp) * Textures.FONT.w];

                if (pixel == 0xFF00FF || pixel == 0x7F007F)
                    continue;

                for (int yr = 0; yr < pixelSize; yr++) {
                    sy = y + spy + yr;
                    if (sy < 0 || sy >= fb.h)
                        continue;

                    for (int xr = 0; xr < pixelSize; xr++) {
                        sx = x + spx + xr;
                        if (sx < 0 || sx >= fb.w)
                            continue;

                        fb.pixels[sx + sy * fb.w] = color;
                    }
                }
            }
        }
    }

}
