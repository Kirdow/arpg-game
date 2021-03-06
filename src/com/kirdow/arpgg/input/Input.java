package com.kirdow.arpgg.input;

import com.kirdow.arpgg.game.Game;

import java.awt.event.*;

public class Input implements MouseListener, KeyListener, MouseMotionListener {

    private static final boolean[] KEYS = new boolean[256];
    private static final boolean[] BUTTONS = new boolean[256];
    private static int M_X, M_Y;

    public static boolean isKeyDown(int kc) {
        if (kc < 0 || kc > 255)
            return false;

        return KEYS[kc];
    }

    public static boolean isKeyUp(int kc) {
        if (kc < 0 || kc > 255)
            return true;

        return !KEYS[kc];
    }

    public static boolean isButtonDown(int bc) {
        if (bc < 0 || bc > 255)
            return false;

        return BUTTONS[bc];
    }

    public static boolean isButtonUp(int bc) {
        if (bc < 0 || bc > 255)
            return true;

        return !BUTTONS[bc];
    }

    public static int getMouseX() {
        return M_X;
    }

    public static int getMouseY() {
        return M_Y;
    }

    private static void setKeys(int ch, int kc, boolean st) {
        if (ch == 0xffff) {
            if (kc >= 16 && kc <= 20) {
                KEYS[kc] = st;
                return;
            }
        } else if ((ch >= 1 && ch <= 26) && (kc >= 'A' && kc <= 'Z')) {
            ch += 96;
        }

        if (ch < 0 || ch > 255)
            return;

        if (ch >= 'A' && ch <= 'Z')
            KEYS[ch + 0x20] = st;
        else if (ch >= 'a' && ch <= 'z')
            KEYS[ch - 0x20] = st;

        KEYS[ch] = st;
    }

    private static void setButtons(int bc, boolean st) {
        if (bc < 0 || bc > 255)
            return;

        BUTTONS[bc] = st;
    }

    private static void setMouse(int x, int y) {
        M_X = x;
        M_Y = y;
    }

    public Input() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int bc = e.getButton();
        setButtons(bc, true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int bc = e.getButton();
        setButtons(bc, false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        char ch = e.getKeyChar();
        int kc = e.getKeyCode();

        Game game = Game.getGame();
        game.keyTyped(ch, kc);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int ch = e.getKeyChar();
        int kc = e.getKeyCode();

        setKeys(ch, kc, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int ch = e.getKeyChar();
        int kc = e.getKeyCode();

        setKeys(ch, kc, false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        setMouse(mx, my);
    }
}
