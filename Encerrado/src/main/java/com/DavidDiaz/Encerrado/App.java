package com.DavidDiaz.Encerrado;


import java.util.Optional;

import com.DavidDiaz.Encerrado.Board.Player;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class App extends Application {

    static Scene scene;
    Group root;
    static Stage mainWindow;
    static Circle[] circles;
    static Text turnIndicator;
    static Text configInfo;
    static Text anuncio;

    public static void main(String args[]){
        launch();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new Group();
        scene = new Scene(root, Color.BLACK);
        Stage s = new Stage();
        s.setScene(scene);
        Image board = new Image("Tablero.png");
        ImageView imageView = new ImageView(board);
        
        circles = new Circle[5];
        for(int i=0; i<5; i++){
            circles[i] = new Circle();
            circles[i].setRadius(50);
        }

        circles[0].setCenterX(104);
        circles[0].setCenterY(72);
        circles[0].setOnMouseClicked((event) -> {
                GameManager.makeMove(0);
        });

        circles[1].setCenterX(664);
        circles[1].setCenterY(72);
        circles[1].setOnMouseClicked((event) -> {
                GameManager.makeMove(1);
        });

        circles[3].setCenterX(101);
        circles[3].setCenterY(506);
        circles[3].setOnMouseClicked((event) -> {
                GameManager.makeMove(3);
        });

        circles[2].setCenterX(385);
        circles[2].setCenterY(300);
        circles[2].setOnMouseClicked((event) -> {
                GameManager.makeMove(2);
        });

        circles[4].setCenterX(668);
        circles[4].setCenterY(506);
        circles[4].setOnMouseClicked((event) -> {
                GameManager.makeMove(4);
        });

        turnIndicator = new Text();
        turnIndicator.setTranslateX(300);
        turnIndicator.setTranslateY(30);
        configInfo = new Text();
        configInfo.setTranslateX(300);
        configInfo.setTranslateY(400);
        configInfo.setText(getConfigInfo());
        anuncio = new Text();
        anuncio.setTranslateX(300);
        anuncio.setTranslateY(500);

        root.getChildren().add(imageView);
        for(int i=0; i<5; i++){
            root.getChildren().add(circles[i]); 
        }
        root.getChildren().add(turnIndicator);
        root.getChildren().add(configInfo);
        root.getChildren().add(anuncio);
        s.show();


        updateTokens();


    }



    /**
     * Ask for user input
     * @param question
     * @param formatDescription
     * @param defaultValue
     * @return
     */
    public static String askForUserInput(String question,String formatDescription, String defaultValue){
        TextInputDialog textInputDialog = new TextInputDialog(defaultValue);
        textInputDialog.setHeaderText(question);
        textInputDialog.setContentText(formatDescription);
        Optional<String> result = textInputDialog.showAndWait();
        if(result.isPresent() ){
            String resultMayus = result.get().toUpperCase();
            return resultMayus;
        }
        else 
            return "";
    }

    /**
     * Show relevant info to the user
     * @param title
     * @param message
     */
    public static void showMessageToUser(String message){
        anuncio.setText(message);
    }

    /**Updates colors of tokens in the board */
    public static void updateTokens(){
        for(int i=0; i<5; i++){
            if(GameManager.board.positions[i] == Player.Red)
                circles[i].setFill(Color.RED);
            if(GameManager.board.positions[i] == Player.Blue)
                circles[i].setFill(Color.BLUE);
            if(GameManager.board.positions[i] == Player.None)
                circles[i].setFill(Color.TRANSPARENT);
        }
    }

    /**
     * Updates turn indicator
     */
    public static void updateTurnIndicator(){
        if(GameManager.board.currentTurn == Player.Blue){
            turnIndicator.setText("Turno actual: Jugador azul");
            turnIndicator.setFill(Color.BLUE);
        }            
        else{
            turnIndicator.setText("Turno actual: Rojo");
            turnIndicator.setFill(Color.RED);
        }
    }

    public static String getConfigInfo(){
        String info = "";
        if(GameManager.playingAgainstIA){
            info += "Jugando contra IA \n";
            info += "Color de la IA: " + Board.playerToStr(GameManager.iaPlayer);
        }    
        else
            info += "Jugando localmente";
        return info;
    }
    
}
