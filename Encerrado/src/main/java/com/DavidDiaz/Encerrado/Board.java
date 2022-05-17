package com.DavidDiaz.Encerrado;

class Board {
    Player[] positions;
    
    public enum Player{
        Blue, Red, None
    }
    

    public static boolean[][] boardGraph = {{false, true, true, true, false},
                                            {true, false, true, false, true},
                                            {true, true, true, true, true},
                                            {true, false, true, false, false},
                                            {false, true, true, false, false}};

    public Board(Player[] positions){
        this.positions = positions;
    }
    
    public Board(){
        positions = new Player[5];
        positions[0] = Player.Red;
        positions[1] = Player.Blue;
        positions[2] = Player.None;
        positions[3] = Player.Blue;
        positions[4] = Player.Red;
    }

    public boolean isValid(int from){
        if(from < 0 || from > 4 )
            return false;
        int emptyCell = getAvailablePosition();
        if(positions[from] == GameManager.currentTurn && boardGraph[from][emptyCell])  //ensure that the player is moving one of their tokens to an empty position
            return true;
        return false;
    }

    public boolean makeMove(int from){
        if( !isValid(from) )
            return false;
        positions[getAvailablePosition()] = GameManager.currentTurn;
        positions[from] = Player.None;
        return true;
    }

    public int getAvailablePosition(){
        for(int i=0; i<5; i++){
            if(positions[i] == Player.None)
                return i;
        }
        return -1;
    }
}
