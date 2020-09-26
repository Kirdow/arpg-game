package com.kirdow.arpgg.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Textures {

    public static final Screen[] DEFAULT_ALPHA_TEXTURES = new Screen[3];
    public static final Screen DEFAULT_ALPHA_TEXTURE;
    public static final Screen TILEMAP;
    public static final Screen ENTITYMAP;
    public static final Screen WALLS;
    public static final Screen FONT;

    static {
        Screen alphaTexture256 = null;
        for (int i = 6; i <= 8; i++) {
            int size = 1 << i;

            Screen alphaTexture = new Screen(size, size);
            int modSize = size / 16;
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    int color = (x % modSize == 0 || y % modSize == 0) ? 0x7F007F : 0xFF00FF;
                    alphaTexture.pixels[x + y * size] = color;
                }
            }
            DEFAULT_ALPHA_TEXTURES[i - 6] = alphaTexture;
            if (i == 8)
                alphaTexture256 = alphaTexture;
        }
        DEFAULT_ALPHA_TEXTURE = alphaTexture256;
        File folder = new File(".data");
        if (!folder.exists())
            folder.mkdirs();
        try {
            Files.setAttribute(Paths.get(folder.toURI()), "dos:hidden", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 3; i++) {
            int size = 1 << (i + 6);
            saveTextureToFile(DEFAULT_ALPHA_TEXTURES[i], String.format(".data/default_alpha_texture%d.png", size));
        }
        String readme = "These files are purely generated to have a pink/purple texture map ready for using.\nThey serve no purpose within the game itself.\nDon't bother editing or deleting as these will be re-created automatically when you next start the game.\nIf you want to remove this option, feel free to rebuild from source and remove the code in Textures.java (inside the static {} block) which saves files to disk.";
        try {
            Files.write(Paths.get(new File(".data", "readme.txt").toURI()), readme.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        TILEMAP = loadTextureFromResource("/tex/tilemap.png");
        ENTITYMAP = loadTextureFromResource("/tex/entitymap.png");
        WALLS = loadTextureFromResource("/tex/edgesections.png");
        FONT = loadTextureFromResource("/tex/font.png");
    }

    public static final Screen loadTextureFromResource(final String path) {
        return loadTextureFromStream(Textures.class.getResourceAsStream(path));
    }

    public static final Screen loadTextureFromStream(final InputStream is) {
        Screen screen = null;
        try {
            BufferedImage image = ImageIO.read(is);
            final int w = image.getWidth();
            final int h = image.getHeight();
            screen = new Screen(w, h);
            image.getRGB(0, 0, w, h, screen.pixels, 0, w);
            for (int i = 0; i < screen.pixels.length; i++) {
                screen.pixels[i] = screen.pixels[i] & 0xFFFFFF;
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Throwable ignored) {
            }
        }
        return screen == null ? DEFAULT_ALPHA_TEXTURE : screen;
    }

    private static final boolean saveTextureToFile(Screen screen, final String path) {
        boolean success = false;
        try {
            success = saveTextureToStream(screen, new FileOutputStream(path));
        } catch (IOException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return success;
    }

    private static final boolean saveTextureToStream(Screen screen, final OutputStream os) {
        boolean success = false;
        try {
            BufferedImage image = new BufferedImage(screen.w, screen.h, BufferedImage.TYPE_INT_RGB);
            image.setRGB(0, 0, screen.w, screen.h, screen.pixels, 0, screen.w);
            ImageIO.write(image, "PNG", os);
            success = true;
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
            } catch (Throwable ignored) {
            }
        }
        return success;
    }

}
