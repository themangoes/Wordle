package com.mango.wordle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.Text;

import java.util.ArrayList;
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

        private int charNum;
        private int guessNum;
        private ArrayList<javafx.scene.control.TextField> curGuess;
        private boolean initialized = false;
        private int streak;
        private int currentPoints;
        private int totalPoints;
        private int timesPlayed;
        private String word;

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
                gameEndOverlay.setVisible(false);
                wordsAnchorPane.setOnKeyPressed((KeyEvent e) -> typeChar(e));
                initialized = true;
        }

        @FXML
        private void checkButton(){

                boolean status = false;
                if (charNum == 4 && guessNum <= 6) {
                        status = checkIfMatches();
                        guessNum++;
                        charNum = -1;
                }
                if (status) guessedCorrectly();
                else if (!status && guessNum > 6){
                        guessedIncorrectly();
                }
        }

        private boolean checkIfMatches(){
                String guess = "";
                int correctCount = 0;
                int[] checks = {0,0,0,0,0};
                for (TextField t : curGuess){
                        guess += t.getText();
                }

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
                currentPoints = (guessNum == 6) ? 0 : 100 / guessNum;

                if (correctCount == 5) {
                        return true;
                }
                return false;

        }

        private void guessedCorrectly(){
                streak++;
                timesPlayed++;
                totalPoints += currentPoints;
                gameEndOverlay.setVisible(true);
                gameEndStatus.setText("Correct Guess!");
                setEndLabels();
        }

        private void guessedIncorrectly(){
                totalPoints += currentPoints;
                streak = 0;
                timesPlayed++;
                gameEndOverlay.setVisible(true);
                gameEndStatus.setText("Incorrect Guess.");
                setEndLabels();
        }

        private void setEndLabels(){
                pointsGainedLabel.setText(String.valueOf(currentPoints));
                totalPointsLabel.setText(String.valueOf(totalPoints));
                currentStreakLabel.setText(String.valueOf(streak));
                timesPlayedLabel.setText(String.valueOf(timesPlayed));
        }

        @FXML
        private void reset(){

                guessNum = 1;
                charNum = -1;
                currentPoints = 0;
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

        private void SomethingWentWrong() {
                try {
                        throw new Exception("Something Went Wrong");
                }
                catch (Exception e){
                        e.printStackTrace();
                }
        }

}
