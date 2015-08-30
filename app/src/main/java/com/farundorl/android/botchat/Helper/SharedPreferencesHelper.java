package com.farundorl.android.botchat.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.farundorl.android.botchat.Model.*;
import com.farundorl.android.botchat.Model.Character;

import java.util.Date;

public class SharedPreferencesHelper {

    private static final String NICK_NAME = "nick_name";
    private static final String NICK_NAME_YOMI = "nick_name_yomi";
    private static final String GENDER = "gender";
    private static final String BLOOD_TYPE = "blood_type";
    private static final String BIRTHDAY = "birthday";
    private static final String AGE = "age";
    private static final String CONSTELLATIONS = "constellations";
    private static final String PLACE = "place";
    private static final String MODE = "mode";
    private static final String CHARACTER = "character";

    private SharedPreferences mPref;

    public SharedPreferencesHelper(Context context) {
        mPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void setNickName(String nickName) {
        mPref.edit().putString(NICK_NAME, nickName).apply();
    }

    public String getNickName() {
        return mPref.getString(NICK_NAME, "");
    }

    public void setNickNameYomi(String nickName) {
        mPref.edit().putString(NICK_NAME_YOMI, nickName).apply();
    }

    public String getNickNameYomi() {
        return mPref.getString(NICK_NAME_YOMI, "");
    }

    public void setGender(Gender gender) {
        mPref.edit().putString(GENDER, gender.toString()).apply();
    }

    public Gender getGender() {
        return Gender.valueOfName(mPref.getString(GENDER, Gender.MEN.toString()));
    }

    public void setBloodType(BloodType bloodType) {
        mPref.edit().putString(BLOOD_TYPE, bloodType.toString()).apply();
    }

    public BloodType getBloodType() {
        return BloodType.valueOfName(mPref.getString(BLOOD_TYPE, BloodType.A.toString()));
    }

    public void setBirthday(Date birthday) {
        mPref.edit().putLong(BIRTHDAY, birthday.getTime()).apply();
    }

    public Date getBirthday() {
        return new Date(mPref.getLong(BIRTHDAY, 0));
    }

    public void setAge(int age) {
        mPref.edit().putInt(AGE, age).apply();
    }

    public int getAge() {
        return mPref.getInt(AGE, 16);
    }

    public void setConstellations(Constellations constellations) {
        mPref.edit().putString(CONSTELLATIONS, constellations.toString()).apply();
    }

    public Constellations getConstellations() {
        return Constellations.valueOfName(mPref.getString(CONSTELLATIONS, Constellations.ARIES.toString()));
    }

    public void setPlace(Place place) {
        mPref.edit().putString(PLACE, place.toString()).apply();
    }

    public Place getPlace() {
        return Place.valueOfName(mPref.getString(PLACE, Place.AREA_53.toString()));
    }

    public void setMode(Mode mode) {
        mPref.edit().putString(MODE, mode.getName()).apply();
    }

    public Mode getMode() {
        return Mode.valueOfName(mPref.getString(MODE, Mode.DIALOG.getName()));
    }

    public void setCharacter(Character character) {
        mPref.edit().putString(CHARACTER, character.getName()).apply();
    }

    public Character getCharacter() {
        return Character.valueOfName(mPref.getString(CHARACTER, Character.DEFAULT.getName()));
    }

}
