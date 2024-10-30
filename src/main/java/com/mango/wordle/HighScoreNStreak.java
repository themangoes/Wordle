package com.mango.wordle;

import java.io.*;
import java.util.Properties;

public class HighScoreNStreak {
    static String filePath = "src/main/java/com/mango/wordle/scores.properties";
    static Properties properties;
    static FileOutputStream propertiesFileOutputStream;
    static FileInputStream propertiesFileInputStream;

    public static void init() throws IOException {
        properties = new Properties();
        propertiesFileInputStream = new FileInputStream(filePath);
        properties.load(propertiesFileInputStream);
    }

    public static int getHighScore(){
        return Integer.parseInt(properties.getProperty("highscore"));
    }

    public static int getHighStreak(){
        return Integer.parseInt(properties.getProperty("highstreak"));
    }

    public static void setHighScore(int score){
        try {
            propertiesFileOutputStream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String scoreString = String.valueOf(score);
        properties.setProperty("highscore", scoreString);
        properties.setProperty("highstreak", properties.getProperty("highstreak"));
        try {
            properties.store(propertiesFileOutputStream, "");
            properties.load(propertiesFileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setHighStreak(int streak){
        try {
            propertiesFileOutputStream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String streakString = String.valueOf(streak);
        properties.setProperty("highstreak", streakString);
        properties.setProperty("highscore", properties.getProperty("highscore"));
        try {
            properties.store(propertiesFileOutputStream, "");
            properties.load(propertiesFileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
