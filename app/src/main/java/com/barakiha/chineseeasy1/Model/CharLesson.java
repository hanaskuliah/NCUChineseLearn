package com.barakiha.chineseeasy1.Model;

/**
 * Created by hanaskuliah on 10/11/2016.
 */

public class CharLesson {

    private int tone;
    private String character;
    private String pinyin;
    private String mean;
    private String clue;
    private String use;

    public int getTone() {
        return tone;
    }

    public void setTone(int tone) {
        this.tone = tone;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getClue() {
        return clue;
    }

    public void setClue(String clue) {
        this.clue = clue;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    @Override
    public String toString() {
        return tone + ": " + character + "\n" + pinyin + "-" + mean
                + "\n" + clue+ "\n" + use;
    }
}