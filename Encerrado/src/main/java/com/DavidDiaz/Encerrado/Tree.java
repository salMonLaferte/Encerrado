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

        @Override
        public String toString() {
            String l= "";
            String r = "";
            if(left != null)
                l = left.toString();
            if(right != null)
                r = right.toString();
            return   l + r + root.value.toString();
        }
    }

    /**
     * Builds the tree with all the posible moves from the current board with the specified depth
     * @param board
     * @param depth
     */
    public Tree( Board board, int depth){
        root = buildTree(board.clone(), depth+1, -1);
        //System.out.println(this.root);
    }

    /**
     * Builds the tree recursively
     * @param board
     * @param depth
     * @param lastMove
     * @return
     */
    Node buildTree(Board board, int depth, int lastMove){
        if(depth == 0)
            return null;
        Node r = new Node(board);
        r.move = lastMove;
        int[] posibleMoves = board.availableMoves();
        if(posibleMoves == null)
            return r;
        if(posibleMoves.length > 0){
            r.left = buildTree(board.makeBoardFromMove(posibleMoves[0]), depth-1, posibleMoves[0]);
            if(posibleMoves.length > 1)
                r.right =buildTree(board.makeBoardFromMove(posibleMoves[1]), depth-1, posibleMoves[1]);
        }
        return r;
    }

    /**
     * Using minimax algorithm selects what is the best move from the board on root
     * @param depth
     * @return
     */
    public int minimax(int depth){
        boolean maximizingPlayer = true;
        if(root.value.currentTurn == Player.Red)
            maximizingPlayer = false;
        int k = minimax(root, depth, maximizingPlayer);
        //System.out.println(k);
        if(root.left == null || root.right ==null){
            if(root.left == null && root.right ==null)
                return 0;
            if(root.left == null)
                return root.right.move;
            else
                return root.left.move;
        }
        if(maximizingPlayer){
            if( root.left != null && root.right != null){
                if(root.left.minVal > root.right.minVal){
                    return root.left.move;
                } //tomar el mas grande de los valores mas chicos  
                else{
                    return root.right.move;
                }
            }
        }
        else{
            if( root.left != null && root.right != null){
                if(root.left.maxVal < root.right.maxVal){
                    return root.left.move;
                } //tomar el mas chico de los valores mas grandes  
                else{
                    return root.right.move;
                }
            }
        }
        return 0;
    } 

    /**
     * Performs minimax algorithm
     * @param pos
     * @param depth
     * @param maximizingPlayer
     * @return
     */
    private int minimax(Node pos, int depth, boolean maximizingPlayer){
        if(depth == 0 || pos.value.checkGAmeOver() != Player.None){
            return pos.value.evaluate();
        }
        if(maximizingPlayer){
            int val = Integer.MIN_VALUE;
            if( pos.left != null)
                val = Math.max(minimax(pos.left, depth -1, false), val);
            if( pos. right != null)
                val = Math.max(minimax(pos.right, depth-1 , false), val) ; 
            pos.maxVal = val;
            return pos.maxVal;
        }
        else{
            int val = Integer.MAX_VALUE;
            if(pos.left != null)
                val = Math.min(minimax(pos.left, depth -1, true), val);
            if(pos.right != null)
                val = Math.min(minimax(pos.right, depth-1 , true), val); 
            pos.minVal = val;
            return pos.minVal;
        }
    }

}
