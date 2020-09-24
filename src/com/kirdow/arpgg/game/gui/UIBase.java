package com.kirdow.arpgg.game.gui;

import com.kirdow.arpgg.game.Game;
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

    public void init() {}
    public void dispose() {}
    public void keyTyped(char typedChar, int charCode) {}

    protected final void logKey(char typedChar, int charCode) {
        System.out.println(String.format("[%s] Typed Char; Char '%s' (0x%s) | Code (%s)", getClass().getName(), typedChar, Integer.toHexString(typedChar).toUpperCase(), charCode));
    }
}
