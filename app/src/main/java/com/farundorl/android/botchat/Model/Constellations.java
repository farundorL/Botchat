package com.farundorl.android.botchat.Model;

public enum Constellations {
    ARIES("牡羊座"),
    TAURUS("牡牛座"),
    GEMINI("双子座"),
    CANCER("蟹座"),
    LEO("獅子座"),
    VIRGO("乙女座"),
    LIBRA("天秤座"),
    SCORPIO("蠍座"),
    SAGITTARIUS("射手座"),
    CAPRICORN("山羊座"),
    AQUARIUS("水瓶座"),
    PISCES("魚座");

    private final String name;

    Constellations(String string) {
        this.name = string;
    }

    public String toString() {
        return name;
    }

    public static Constellations valueOfName(String name) {
        for (Constellations constellations : values()) {
            if (constellations.name.equals(name)) {
                return constellations;
            }
        }
        throw new IllegalArgumentException("no such enum object for the id: " + name);
    }

}
