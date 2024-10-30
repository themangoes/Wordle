package com.mango.wordle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Main extends Application {

    public static Scene scene;
    public static Stage stage;
    static FXMLLoader fxmlLoader;
    @Override
    public void start(Stage stage1) throws IOException {
        stage = stage1;
        fxmlLoader = new FXMLLoader(Main.class.getResource("wordle.fxml"));
        scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Main.class.getResource("wordle.css").toExternalForm());
        wordleController c = fxmlLoader.getController();
        c.init();
        stage.setTitle("WORDLE!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}