package com.DavidDiaz.Encerrado;

import com.DavidDiaz.Encerrado.Board.Player;

public class Tree {
    Node root;
    
    private class Node{
        Board value;
        int maxVal;
        int minVal;
        int move;
        Node left;
        Node right; 
        Node(Board val){
            value = val;
            left = null;
            right = null;
        }
    }

    public Tree( Board board, int depth){
        root = buildTree(board, depth);
    }

    public Node buildTree(Board board, int depth){
        if(depth == 0)
            return null;
        Node root = new Node(board);
        Board[] posibleMoves = board.availableMoves();
        if(board)//CONSTRUIR SOLO LO NECESARIO
        root.left = buildTree(posibleMoves[0], depth-1);
        root.right =buildTree(posibleMoves[1], depth-1);
        return root;
    }

    public int minimax(int depth){
        boolean maximizingPlayer = true;
        if(root.value.currentTurn == Player.Red)
            maximizingPlayer = false;
        int k =minimax(root, 10, maximizingPlayer);
        if(maximizingPlayer){
            if( root.left != null && root.left.maxVal == k){
                return root.left.move;  
            }
            else
                return root.right.move;
        }
        else{
            if( root.left != null && root.left.minVal == k){
                return root.left.move;  
            }
            else
                return root.right.move;
        }
    } 
    // NO CHECAR HIJOS NULOS
    private int minimax(Node pos, int depth, boolean maximizingPlayer){
        if(depth == 0 || pos.value.checkGAmeOver() != Player.None){
            return pos.value.evaluate();
        }
        if(maximizingPlayer){
            int lval = minimax(pos.left, depth -1, false);
            int rval = minimax(pos.right, depth-1 , false); 
            pos.maxVal = Math.max(lval, rval);
            return pos.maxVal;
        }
        else{
            int lval = minimax(pos.left, depth -1, true);
            int rval = minimax(pos.right, depth-1 , true); 
            pos.minVal = Math.min(lval, rval);
            return pos.minVal;
        }
    }
}
