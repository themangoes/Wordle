package com.mango.wordle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class randomWordGenerator {
    static String allWords;

    public static void init(){
        try {
            URL url = new URL("https://random-word-api.herokuapp.com/word?length=5&number=20000");
            InputStream ip = url.openConnection().getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(ip));
            allWords = br.readLine();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String randomWord(){
        try{
            URL url = new URL("https://random-word-api.herokuapp.com/word?length=5");
            InputStream inputStream = url.openConnection().getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String wordWithBracketsNQuotes = br.readLine();
            String word = "";
            for (int i = 0; i < wordWithBracketsNQuotes.length(); i++){
                if (wordWithBracketsNQuotes.charAt(i) == '[' ||
                    wordWithBracketsNQuotes.charAt(i) == '"' ||
                    wordWithBracketsNQuotes.charAt(i) == ']') continue;
                word += wordWithBracketsNQuotes.charAt(i);
            }
            return word.toUpperCase();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "";
    }
}
