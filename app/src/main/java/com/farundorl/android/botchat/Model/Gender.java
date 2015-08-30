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

    public static Gender valueOfName(String name) {
        for (Gender gender : values()) {
            if (gender.name.equals(name)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("no such enum object for the id: " + name);
    }

}
