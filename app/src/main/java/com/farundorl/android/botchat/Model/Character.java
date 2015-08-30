package com.farundorl.android.botchat.Model;

public enum Character {
    DEFAULT("", "デフォルトキャラ"),
    NANIWA("20", "関西弁キャラ"),
    BABY("30", "赤ちゃんキャラ");

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

}
