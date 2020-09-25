package com.kirdow.arpgg.game.gui;

import com.kirdow.arpgg.GameTimer;
import com.kirdow.arpgg.gfx.Font;
import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.gfx.Textures;
import com.kirdow.arpgg.util.Vectori;

import java.awt.event.KeyEvent;
import java.util.Random;

public class UIMainMenu extends UIBase {

    private float xTimer;

    public UIMainMenu() {
        super("MainMenu");
    }

    public Vectori tileId;

    @Override
    public void init() {
        super.init();

        xTimer = 0;
        Random r = new Random();
        int rnum = r.nextInt();

        final Vectori[] IDS = new Vectori[]{
                new Vectori(0, 0),
                new Vectori(1, 0)
        };

        int id = rnum % IDS.length;
        if (id < 0)
            id += IDS.length;

        tileId = IDS[id];
    }

    @Override
    public void tick() {
        xTimer = (xTimer + 20.0f * GameTimer.DELTA) % 64;
    }

    @Override
    public void draw(Screen fb) {
        final int w2 = 12;

        final Screen TILEMAP = Textures.TILEMAP;
        for (int y = 0; y < fb.h; y++) {
            int tileY = (y) & 0xF;
            for (int x = 0; x < fb.w; x++) {
                int tileX = (((0x3F - (int)xTimer) + x)) & 0xF;

                fb.pixels[x + y * fb.w] = TILEMAP.pixels[(tileId.ix * 16 + tileX) + (tileId.iy * 16 + tileY) * TILEMAP.w];
            }
        }

        for (int x = (fb.w / 2) - w2; x < (fb.w / 2) + w2; x++) {
            int n = w2 - Math.abs((x - (fb.w / 2) + w2) / 2);
            for (int y = (fb.h / 3) - n; y < (fb.h / 3) + n; y++) {
                fb.pixels[x + y * fb.w] = 0xFFAA22;
            }
        }

        Font.drawShadowCentered("A-RPG-G!", fb, fb.w / 2, 12, 0x00FF00, 1);
    }

    @Override
    public void keyTyped(char typedChar, int charCode) {
        if (typedChar == KeyEvent.VK_SPACE) {
            game.setCurrentGui(new UIGame());
        }
    }
}
