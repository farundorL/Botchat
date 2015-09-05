package com.farundorl.android.botchat.Helper;

import java.util.regex.Pattern;

public class ValidationHelper {

    private static final String PATTERN_NICK_NAME = "[a-zA-ZＡ-ｚ　-熙]{1,10}";
    private static final String PATTERN_NICK_NAME_YOMI = "[ァ-ヶ]{1,20}";

    public static boolean checkNickName(CharSequence nickname) {
        return Pattern.matches(PATTERN_NICK_NAME, nickname);
    }

    public static boolean checkNickNameYomi(CharSequence nickname) {
        return Pattern.matches(PATTERN_NICK_NAME_YOMI, nickname);
    }

}
