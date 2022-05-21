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
        turnIndicator.setTranslateX(30);
        turnIndicator.setTranslateY(30);

        
        root.getChildren().add(imageView);
        for(int i=0; i<5; i++){
            root.getChildren().add(circles[i]); 
        }
        root.getChildren().add(turnIndicator);
        s.show();


        updateTokens();

        //GameManager.mainLoop();
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
            if(resultMayus.equals("TERMINAR")){//Ends current game if user introduces TERMINAR
                terminar();
                return "";
            }
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
    public static void showMessageToUser(String title, String message){
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Imprime los resultados, el ganador y cierra la aplicacion
     */
    public static void terminar(){
        showMessageToUser("Partida finalizada", "");
        System.exit(0);
    }

    /**Updates colors of tokesn in the board */
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

    public static void updateTurnIndicator(){
        if(GameManager.board.currentTurn == Player.Blue)
            turnIndicator.setText("Azul");
        else
            turnIndicator.setText("Rojo");
    }
    
}
