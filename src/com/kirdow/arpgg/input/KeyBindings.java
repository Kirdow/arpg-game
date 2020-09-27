package com.kirdow.arpgg.input;

import java.awt.event.KeyEvent;

public class KeyBindings {

    public static final KeyBinding SELECT = new KeyBinding(KeyEvent.VK_SPACE, "Select");
    public static final KeyBinding CANCEL = new KeyBinding(KeyEvent.VK_ESCAPE, "Escape");

    public static final KeyBinding MOVE_UP = new KeyBinding(KeyEvent.VK_W, "Move Up");
    public static final KeyBinding MOVE_DOWN = new KeyBinding(KeyEvent.VK_S, "Move Down");
    public static final KeyBinding MOVE_LEFT = new KeyBinding(KeyEvent.VK_A, "Move Left");
    public static final KeyBinding MOVE_RIGHT = new KeyBinding(KeyEvent.VK_D, "Move Right");
    public static final KeyBinding MOVE_UP_SECOND = new KeyBinding(KeyEvent.VK_W, "Move Up Secondary");
    public static final KeyBinding MOVE_DOWN_SECOND = new KeyBinding(KeyEvent.VK_S, "Move Down Secondary");
    public static final KeyBinding MOVE_LEFT_SECOND = new KeyBinding(KeyEvent.VK_A, "Move Left Secondary");
    public static final KeyBinding MOVE_RIGHT_SECOND = new KeyBinding(KeyEvent.VK_D, "Move Right Secondary");
    public static final KeyBinding MOVE_FAST = new KeyBinding(KeyEvent.VK_CONTROL, "Move Fast");
    public static final KeyBinding MOVE_STOP = new KeyBinding(KeyEvent.VK_SHIFT, "Move Stop");

    public static final KeyBinding MAIN_ATTACK = new KeyBinding(KeyEvent.VK_SPACE, "Main Attack");
    public static final KeyBinding SPELL_1 = new KeyBinding(KeyEvent.VK_1, "Spell 1");
    public static final KeyBinding SPELL_2 = new KeyBinding(KeyEvent.VK_2, "Spell 2");
    public static final KeyBinding SPELL_3 = new KeyBinding(KeyEvent.VK_3, "Spell 3");
    public static final KeyBinding SPELL_4 = new KeyBinding(KeyEvent.VK_4, "Spell 4");

    public static final KeyBinding POT_1 = new KeyBinding(KeyEvent.VK_Z, "Pot 1");
    public static final KeyBinding POT_2 = new KeyBinding(KeyEvent.VK_X, "Pot 2");
    public static final KeyBinding POT_3 = new KeyBinding(KeyEvent.VK_C, "Pot 3");
    public static final KeyBinding POT_4 = new KeyBinding(KeyEvent.VK_V, "Pot 4");

    public static final boolean isSelectDown() {
        return SELECT.isKeyDown();
    }

    public static boolean isCancelDown() {
        return CANCEL.isKeyDown();
    }

    public static boolean isMoveUp() {
        return MOVE_UP.isKeyDown() || MOVE_UP_SECOND.isKeyDown();
    }

    public static boolean isMoveDown() {
        return MOVE_DOWN.isKeyDown() || MOVE_DOWN_SECOND.isKeyDown();
    }

    public static boolean isMoveLeft() {
        return MOVE_LEFT.isKeyDown() || MOVE_LEFT_SECOND.isKeyDown();
    }

    public static boolean isMoveRight() {
        return MOVE_RIGHT.isKeyDown() || MOVE_RIGHT_SECOND.isKeyDown();
    }

    public static boolean isMoveFast() {
        return MOVE_FAST.isKeyDown();
    }

    public static boolean isMoveStop() {
        return MOVE_STOP.isKeyDown();
    }

}
