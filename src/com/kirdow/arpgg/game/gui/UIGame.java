package com.kirdow.arpgg.game.gui;

import com.kirdow.arpgg.GameTimer;
import com.kirdow.arpgg.game.level.Level;
import com.kirdow.arpgg.gfx.Screen;
import com.kirdow.arpgg.input.Input;
import com.kirdow.arpgg.input.KeyBindings;

import java.awt.event.KeyEvent;

public class UIGame extends UIBase {

    private UIPauseMenu pauseMenu;

    private Level level;

    public UIGame() {
        super("Game");
        pauseMenu = null;
    }

    @Override
    public void init() {
        super.init();

        level = new Level(64, 64);
    }

    @Override
    public void tick() {
        if (pauseMenu != null) {
            pauseMenu.tick();
            return;
        }

        if (level != null) {
            level.tick();
        }
    }

    @Override
    public void draw(Screen fb) {
        if (level != null) {
            level.draw(fb);
        }

        if (pauseMenu != null) {
            pauseMenu.draw(fb);
        }
    }

    @Override
    public void keyTyped(char typedChar, int charCode) {
        if (this.pauseMenu != null) {
            this.pauseMenu.keyTyped(typedChar, charCode);
            return;
        } else if (typedChar == KeyBindings.CANCEL.getKeyCode()) {
            this.pauseMenu = new UIPauseMenu(this);
            this.pauseMenu.init();
            return;
        }

        super.keyTyped(typedChar, charCode);
    }

    public void unpause() {
        if (pauseMenu != null) {
            pauseMenu.dispose();
            pauseMenu = null;
        }
    }

    public boolean isPaused() {
        return pauseMenu != null;
    }
}
