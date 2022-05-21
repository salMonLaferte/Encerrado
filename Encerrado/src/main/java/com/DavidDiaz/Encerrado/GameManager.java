package com.DavidDiaz.Encerrado;

import java.util.Random;


import com.DavidDiaz.Encerrado.Board.Player;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class GameManager {

    public static Board board = new Board(Player.Blue);
    public static boolean playingAgainstIA = true;
    public static Player iaPlayer = Player.Red;
    public static boolean iaMiniMaxMode = true;

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
     * Trys to make a move from the position from to the empty position, if is a valid move updates the visuals and checks for winner
     * @param from
     */
    public static void makeMove(int from){
        if(playingAgainstIA && board.currentTurn == iaPlayer)
            return;
        if(!board.makeMove(from)){
            return;
        }
        updateVisualsAndCheckForWinner();
        if(playingAgainstIA){
            PauseTransition p = new PauseTransition(Duration.millis(1000));
            p.play();
            p.setOnFinished(e->{
                makeIAMove();
            });
        }
    }

    /**
     * Makes the ia move
     */
    public static void makeIAMove(){
        int move;
        if(iaMiniMaxMode)
            move = getIAMinimaxMove();
        else
            move = getIARandomMove();
        board.makeMove(move);
        updateVisualsAndCheckForWinner();
    }

    /**
     * Updates visuals of the board and checks if game is over
     */
    public static void updateVisualsAndCheckForWinner(){
        App.updateTokens();
        App.updateTurnIndicator();
        Player winner = board.checkGAmeOver();
        if(winner != Player.None){
            App.showMessageToUser("Se termino el juego. Ganó: " + Board.playerToStr(winner));
        }
    }

    /**
     * Gets a Random move for the IA player.
     * @return
     */
    public static int getIARandomMove(){
        int[] moves = new int[2];
        moves[0] = -1;
        moves[1] = -1;
        int current = 0;
        for(int i=0; i<5; i++){
            if(board.positions[i] == iaPlayer && board.isValid(i)){
                moves[current] = i;
                current++;
            }
        }
        if(moves[0] != -1 && moves[1] != -1){
            Random random = new Random();
            return moves[random.nextInt(2)];
        }
        else{
            return moves[0];
        }
    }


    public static int getIAMinimaxMove(){
        Tree moves = new Tree(board, 10);
        int minimaxMove = moves.minimax(10);
        return minimaxMove;
    }

}
