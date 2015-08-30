package com.farundorl.android.botchat.Model;

public enum Mode {
    DIALOG("対話", "dialog"),
    SRTR("しりとり", "srtr");

    private final String name;
    private final String code;

    Mode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public static Mode valueOfName(String name) {
        for (Mode mode : values()) {
            if (mode.name.equals(name)) {
                return mode;
            }
        }
        throw new IllegalArgumentException("no such enum object for the id: " + name);
    }
}
