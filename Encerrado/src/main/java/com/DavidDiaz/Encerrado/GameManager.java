package com.DavidDiaz.Encerrado;

import com.DavidDiaz.Encerrado.Board.Player;

public class GameManager {

    public static Board board = new Board(Player.Blue);


    public static void mainLoop(){
        /*while(true){
            boolean succesfullMove = false;
            String input = App.askForUserInput("Introduce movimiento", "Porfi", "Andalesi");
            MyPair<Integer,Integer> move = GameManager.parseInput(input);
            if(move != null){
                succesfullMove = board.makeMove(move.first, move.second);
            }
            if(succesfullMove){
                changeTurn();
            }else{
                App.showMessageToUser("Formato o movimiento inválido", "Por favor introduce un movimiento válido");
            }
        }*/
    }

    public static MyPair<Integer, Integer> parseInput(String input){
        String[] numbers = input.split(":");
        if(numbers.length < 2)
            return null;
        int from = Integer.parseInt(numbers[0]);
        int to = Integer.parseInt(numbers[1]);
        MyPair<Integer, Integer> result = new MyPair<Integer, Integer>(from, to);
        return result;
    }

    /**
     * Trys to make a move from the position from to the empty position, if is a valid move updates the visuals
     * @param from
     */
    public static void makeMove(int from){
        if(!board.makeMove(from)){
            return;
        }
        App.updateTokens();
        App.updateTurnIndicator();
    }

}
