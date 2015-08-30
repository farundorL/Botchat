package com.farundorl.android.botchat.Model;

public enum Gender {
    MEN("男性"),
    WOMEN("女性");

    private final String name;

    Gender(final String string) {
        this.name = string;
    }

    public String toString() {
        return name;
    }

}
