package com.farundorl.android.botchat.Model;

public enum BloodType {
    A("A"),
    B("B"),
    O("O"),
    AB("AB");

    private final String name;

    BloodType(String string) {
        this.name = string;
    }

    public String toString() {
        return name;
    }

}
