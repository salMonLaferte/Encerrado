package com.DavidDiaz.Encerrado;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class App extends Application {
    public static void main(String args[]){
        launch();
        System.out.println("Hello world");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);
        Stage s = new Stage();
        s.setScene(scene);
        s.show();
    }    
    
}
