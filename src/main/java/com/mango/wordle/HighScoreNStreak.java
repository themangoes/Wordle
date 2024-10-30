package com.mango.wordle;

import java.io.*;
import java.util.Properties;

public class HighScoreNStreak {
    static String filePath = "src/main/java/com/mango/wordle/scores.properties";
    static Properties properties;
    static FileOutputStream propertiesFileOutputStream;
    static FileInputStream propertiesFileInputStream;
    static boolean fileNotFound = false;
    static boolean initialized = false;

    public static void init(){
        try{
            FileInputStream test1 = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileNotFound = true;
            initialized = true;
            return;
        }
        properties = new Properties();
        try {
            propertiesFileInputStream = new FileInputStream(filePath);
            properties.load(propertiesFileInputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        initialized = true;
    }

    public static int getHighScore(){
        if (!initialized) init();
        if (fileNotFound) return -1;
        return Integer.parseInt(properties.getProperty("highscore"));
    }

    public static int getHighStreak(){
        if (!initialized) init();
        if (fileNotFound) return -1;
        return Integer.parseInt(properties.getProperty("highstreak"));
    }

    public static void setHighScore(int score){
        if (!initialized) init();
        if (fileNotFound) return;
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
        if (!initialized) init();
        if (fileNotFound) return;
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
