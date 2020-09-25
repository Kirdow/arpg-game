package com.kirdow.arpgg.game;

import com.kirdow.arpgg.game.gui.UIBase;
import com.kirdow.arpgg.game.gui.UIMainMenu;
import com.kirdow.arpgg.gfx.Screen;

import java.util.Random;

public class Game {

    private static Game game;

    private UIBase currentGui;

    private Game() {
        Game.game = this;
    }

    public void tick() {
        if (currentGui == null) {
            setCurrentGui(new UIMainMenu());
        } else {
            currentGui.tick();
        }
    }

    public void draw(Screen fb) {
        if (currentGui != null) {
            currentGui.draw(fb);
            currentGui.drawInfo(fb);
        }
    }

    public void keyTyped(char typedChar, int charCode) {
        if (currentGui != null) {
            currentGui.keyTyped(typedChar, charCode);
        }
    }

    public void setCurrentGui(UIBase ui) {
        if (currentGui != null) {
            currentGui.dispose();
        }

        if (ui != null) {
            ui.init();
        }
        currentGui = ui;
    }

    public UIBase getCurrentGui() {
        return currentGui;
    }

    public static final Game getGame() {
        return game != null ? game : new Game();
    }

}
