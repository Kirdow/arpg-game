package com.kirdow.arpgg.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyBinding {

    private final int _default;
    private int keyCode;
    private String name;

    public KeyBinding(int _default, String name) {
        this.name = name;
        this._default = _default;
        this.keyCode = _default;
    }

    public int getDefaultKeyCode() {
        return _default;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public boolean isKeyDown() {
        return Input.isKeyDown(this.keyCode);
    }

    public boolean isKeyUp() {
        return Input.isKeyUp(this.keyCode);
    }

}
