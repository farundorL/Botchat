package com.farundorl.android.botchat.Model;

public enum Character {
    DEFAULT("", "デフォルト"),
    NANIWA("20", "関西弁"),
    BABY("30", "赤ちゃん");

    private final String code;
    private final String name;

    Character(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
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
