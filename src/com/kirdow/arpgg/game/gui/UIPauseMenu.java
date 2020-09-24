package com.kirdow.arpgg.game.gui;

import com.kirdow.arpgg.gfx.Screen;

import java.awt.event.KeyEvent;

public class UIPauseMenu extends UIBase {

    private final UIGame gameUi;

    public UIPauseMenu(UIGame gameUi) {
        super("Pause Menu");

        this.gameUi = gameUi;
    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(Screen fb) {
        for (int y = (fb.h / 3) - 15; y < (fb.h / 3) + 15; y++) {
            for (int x = (fb.w / 2) - 15; x < (fb.w / 2) - 5; x++) {
                fb.pixels[(x) + y * fb.w] = 0xFFFFFF;
                fb.pixels[(x + 20) + y * fb.w] = 0xFFFFFF;
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int charCode) {
        if (typedChar == KeyEvent.VK_ESCAPE) {
            gameUi.unpause();
            return;
        }

        super.logKey(typedChar, charCode);
    }
}
