package com.DavidDiaz.Encerrado;

/**
 * Abstraction of the state of the board in the game
 * @author José David García Díaz
 */
public class Board {
    Player[] positions;
    public Player currentTurn = Player.Blue;

    public enum Player{
        Blue, Red, None
    }
    
    //Graph thats represents the board
    public static boolean[][] boardGraph = {{false, true, true, true, false},
                                            {true, false, true, false, true},
                                            {true, true, true, true, true},
                                            {true, false, true, false, false},
                                            {false, true, true, false, false}};

    public Board(Player[] positions, Player initialTurn){
        currentTurn = initialTurn;
        this.positions = positions;
    }
    
    public Board(Player initialTurn){
        currentTurn = initialTurn;
        positions = new Player[5];
        positions[0] = Player.Red;
        positions[1] = Player.Blue;
        positions[2] = Player.None;
        positions[3] = Player.Blue;
        positions[4] = Player.Red;
    }

    /**
     * Tells if moving token on the position is a valid move according with the current turn
     * @param from
     * @return
     */
    public boolean isValid(int from){
        if(from < 0 || from > 4 )
            return false;
        int emptyCell = getAvailablePosition();
        if(positions[from] == currentTurn && boardGraph[from][emptyCell])  //ensure that the player is moving one of their tokens to an empty position
            return true;
        return false;
    }

    /**
     * Move the token on the position from to the position that is empty
     * @param from
     * @return false if move was not valid, true if move is performed
     */
    public boolean makeMove(int from){
        if( !isValid(from) )
            return false;
        positions[getAvailablePosition()] = currentTurn;
        positions[from] = Player.None;
        changeTurn();
        return true;
    }


    /**
     * Return the index of the position that is empty
     * @return
     */
    public int getAvailablePosition(){
        for(int i=0; i<5; i++){
            if(positions[i] == Player.None)
                return i;
        }
        return -1;
    }

    /**
     * Checks if the game is over and returns its winner, returns none if game is not over
     * @return
     */
    public Player checkGAmeOver(){
        for(int i=0; i<5; i++){
            if( positions[i] == currentTurn && isValid(i)){
                return Player.None;
            }
        }
        return opositePlayer(currentTurn);
    }

    /**
     * Returns an array for the available moves for the current player, return null if theres no available moves
     * @return
     */
    public int[] availableMoves(){
        int count = 0;
        for(int i=0; i<5; i++){
            if( positions[i] == currentTurn && isValid(i)){
                count++;
            }
        }
        if(count == 0)
            return null;
        int[] moves = new int[count];
        int index = 0;
        for(int i=0; i<5; i++){
            if( positions[i] == currentTurn && isValid(i)){
                moves[index] = i;
                index++;
            }
        }
        return moves;
    }

    /**
     * Clones the current board, makes the move in that clone, and the resulting board is returned
     * @param from
     * @return
     */
    public Board makeBoardFromMove(int from){
        Board b = clone();
        b.makeMove(from);
        return b;
    }

    /**
     * Clones the board.
     */
    public Board clone(){
        Player[] pos = positions.clone();
        return new Board(pos, currentTurn);
    }

    /**
     * Returns the other player that is not p, returns none if player.none is passed
     * @param p
     * @return
     */
    static Player opositePlayer(Player p){
        if(p == Player.Blue)
            return Player.Red;
        if(p == Player.Red)
            return Player.Blue;
        return Player.None;
    }

    /**
     * Change the current turn to the other player
     */
    public void changeTurn(){
        currentTurn = opositePlayer(currentTurn);
    }

    /**
     * Function that evaluates the board for the minimax algorithm
     * @return
     */
    public int evaluate(){
        Player winner = checkGAmeOver();
        if( winner == Player.Red){
            return -1;
        }
        if( winner == Player.Blue){
            return 1;
        }
        return 0;
    }

    /**
     * Regresa una cadena de caracateres con el nombre del jugador
    */
    public static String playerToStr(Player p){
        if(p == Player.Red){
            return "Rojo";
        }
        if(p == Player.Blue){
            return "Azul";
        }
        return "Ninguno";
    }

    /**
     * Returns a string representation of the board.
     */
    public String toString(){
        String s = "";
        for(int i=0; i<5; i++){
            s+= Board.playerToStr(positions[i]) + " | ";
        }
        return s;
    }
}
