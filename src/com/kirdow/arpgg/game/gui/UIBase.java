package com.kirdow.arpgg.game.gui;

import com.kirdow.arpgg.GameTimer;
import com.kirdow.arpgg.game.Game;
import com.kirdow.arpgg.gfx.Font;
import com.kirdow.arpgg.gfx.Screen;

public abstract class UIBase {

    private String name;
    protected final Game game;

    public UIBase(String name) {
        this.name = name;
        this.game = Game.getGame();
    }

    public UIBase() {
        this("Undefined");
    }

    public abstract void tick();
    public abstract void draw(final Screen fb);

    public final void drawInfo(final Screen fb) {
        if (this instanceof UIGame) {
            UIGame uiGame = (UIGame)this;
            if (uiGame.isPaused()) {
                Font.draw("A-RPG-G", fb, 2, 2, 0xFFFFFF, 1);
            }
        }

        Font.draw(String.format("T/F %d/%d", GameTimer.CURRENT_TPS, GameTimer.CURRENT_FPS), fb, 2, fb.h - 10, 0xFFFFFF, 1);
    }

    public void init() {}
    public void dispose() {}
    public void keyTyped(char typedChar, int charCode) {}

    protected final void logKey(char typedChar, int charCode) {
        System.out.println(String.format("[%s] Typed Char; Char '%s' (0x%s) | Code (%s)", getClass().getName(), typedChar, Integer.toHexString(typedChar).toUpperCase(), charCode));
    }
}
