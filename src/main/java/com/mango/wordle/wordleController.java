package com.mango.wordle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class wordleController{

        @FXML
        ArrayList<javafx.scene.control.TextField> GuessOne;
        @FXML
        ArrayList<javafx.scene.control.TextField> GuessTwo;
        @FXML
        ArrayList<javafx.scene.control.TextField> GuessThree;
        @FXML
        ArrayList<javafx.scene.control.TextField> GuessFour;
        @FXML
        ArrayList<javafx.scene.control.TextField> GuessFive;
        @FXML
        ArrayList<javafx.scene.control.TextField> GuessSix;

        @FXML
        private AnchorPane wordsAnchorPane;
        @FXML
        private Pane gameEndOverlay;
        @FXML
        private Label gameEndStatus;
        @FXML
        private Label pointsGainedLabel;
        @FXML
        private Label totalPointsLabel;
        @FXML
        private Label highestScoreLabel;
        @FXML
        private Label currentStreakLabel;
        @FXML
        private Label highestStreakLabel;
        @FXML
        private Label timesPlayedLabel;
        @FXML
        private Label cheat;
        @FXML
        private Button cheatButton;
        @FXML
        private Label invalidWord;

        private int charNum;
        private int guessNum;
        private ArrayList<javafx.scene.control.TextField> curGuess;
        private boolean initialized = false;
        private int streak;
        private int currentPoints;
        private int totalPoints;
        private int timesPlayed;
        private int highestScore;
        private int highestStreak;
        private String word;
        private HashSet<String> currentGuesses;

        public wordleController(){
        }

        @FXML
        public void init(){
                if (initialized) return;
                word = randomWordGenerator.randomWord();
                charNum = -1;
                guessNum = 1;
                curGuess = GuessOne;
                streak = 0;
                timesPlayed = 1;
                currentGuesses = new HashSet<>();
                highestScore = HighScoreNStreak.getHighScore();
                highestStreak = HighScoreNStreak.getHighStreak();
                gameEndOverlay.setVisible(false);
                wordsAnchorPane.setOnKeyPressed((KeyEvent e) -> typeChar(e));
                initialized = true;
                cheatButton.setVisible(true);
        }

        @FXML
        private void checkButton() {

                int status = -1;
                if (charNum == 4 && guessNum <= 6) {
                        status = checkIfMatches();
                        if (status == -1) return;
                        guessNum++;
                        charNum = -1;
                }
                if (status == 1) guessedCorrectly();
                else if (status == 0 && guessNum > 6){
                        guessedIncorrectly();
                }
        }

        private int checkIfMatches(){
                String guess = "";
                int correctCount = 0;
                int[] checks = {0,0,0,0,0};
                for (TextField t : curGuess) {
                        guess += t.getText();
                }
                if (!randomWordGenerator.allWords.contains(guess.toLowerCase())) {
                        invalidWord.setText("Invalid Word!");
                        return -1;
                }
                else if (currentGuesses.contains(guess)){
                        invalidWord.setText("Word Used!");
                        return -1;
                }
                invalidWord.setText("");

                for (int i = 0; i < 5; i++){
                        if (word.charAt(i) == guess.charAt(i)){
                                checks[i] = 2;
                                correctCount++;
                        }
                        else{
                                for (int j = 0; j < 5; j++){
                                        if (j == i || checks[j] >= 1) continue;
                                        if (word.charAt(i) == guess.charAt(j)) checks[j] = 1;
                                }
                        }
                }

                for (int i = 0; i < 5; i++){
                        if (checks[i] == 2){
                                curGuess.get(i).setStyle("-fx-background-color : green");
                        }
                        else if (checks[i] == 1){
                                curGuess.get(i).setStyle("-fx-background-color : yellow");
                        }
                        else {
                                curGuess.get(i).setStyle("-fx-background-color : gray");
                        }
                }
                currentGuesses.add(guess);
                currentPoints = (guessNum == 6) ? 0 : 100 / guessNum;

                if (correctCount == 5) {
                        return 1;
                }
                return 0;

        }

        private void guessedCorrectly() {
                streak++;
                totalPoints += currentPoints;
                gameEndOverlay.setVisible(true);
                gameEndStatus.setText("Correct Guess!");
                setEndLabels();
                if (totalPoints > highestScore){
                        highestScore = totalPoints;
                        HighScoreNStreak.setHighScore(totalPoints);
                }
                if (streak > highestStreak){
                        highestStreak = streak;
                        HighScoreNStreak.setHighStreak(streak);
                }
        }

        private void guessedIncorrectly(){
                totalPoints += currentPoints;
                streak = 0;
                gameEndOverlay.setVisible(true);
                gameEndStatus.setText("Incorrect Guess.");
                setEndLabels();
        }

        private void setEndLabels(){
                pointsGainedLabel.setText(String.valueOf(currentPoints));
                totalPointsLabel.setText(String.valueOf(totalPoints));
                currentStreakLabel.setText(String.valueOf(streak));
                timesPlayedLabel.setText(String.valueOf(timesPlayed));
                highestScoreLabel.setText(String.valueOf(highestScore));
                highestStreakLabel.setText(String.valueOf(highestStreak));
        }

        @FXML
        private void reset(){
                timesPlayed++;
                guessNum = 1;
                charNum = -1;
                currentPoints = 0;
                currentGuesses.clear();
                word = randomWordGenerator.randomWord();
                gameEndOverlay.setVisible(false);
                for (TextField t : GuessSix){
                        t.setText("");
                        t.setStyle("-fx-background-color : #1");
                }
                for (TextField t : GuessFive){
                        t.setText("");
                        t.setStyle("-fx-background-color : #1");
                }
                for (TextField t : GuessFour){
                        t.setText("");
                        t.setStyle("-fx-background-color : #1");
                }
                for (TextField t : GuessThree){
                        t.setText("");
                        t.setStyle("-fx-background-color : #1");
                }
                for (TextField t : GuessTwo){
                        t.setText("");
                        t.setStyle("-fx-background-color : #1");
                }
                for (TextField t : GuessOne){
                        t.setText("");
                        t.setStyle("-fx-background-color : #1");
                }
        }

        @FXML
        private void typeChar(KeyEvent e){
                if (gameEndOverlay.isVisible()) return;

                switch(guessNum){
                        case 1 :
                                curGuess = GuessOne;
                                break;
                        case 2 :
                                curGuess = GuessTwo;
                                break;
                        case 3 :
                                curGuess = GuessThree;
                                break;
                        case 4 :
                                curGuess = GuessFour;
                                break;
                        case 5 :
                                curGuess = GuessFive;
                                break;
                        case 6 :
                                curGuess = GuessSix;
                                break;
                        default :
                                SomethingWentWrong();
                                break;
                }
                if (e.getCode().getCode() == 8 && charNum >= 0){
                        curGuess.get(charNum--).setText("");
                }
                else if (e.getCode().getCode() == 10 && charNum == 4){
                        checkButton();
                }
                else if (charNum < 4){
                        if (e.getCode().getCode() >= 65 && e.getCode().getCode() <= 90){
                                curGuess.get(++charNum).setText(e.getText().toUpperCase());
                        }
                }
        }

        @FXML
        private void cheat(ActionEvent e){
                cheat.setText(word);
        }

        private void SomethingWentWrong() {
                try {
                        throw new Exception("Something Went Wrong");
                }
                catch (Exception e){
                        e.printStackTrace();
                }
        }

}
