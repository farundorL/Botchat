package com.farundorl.android.botchat.Model;

public enum Character {
    DEFAULT(0, "デフォルト"),
    NANIWA(20, "関西弁"),
    BABY(30, "赤ちゃん");

    private final int code;
    private final String name;

    Character(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Character valueOfName(String name) {
        for (Character character : values()) {
            if (character.name.equals(name)) {
                return character;
            }
        }
        throw new IllegalArgumentException("no such enum object for the id: " + name);
    }

}
