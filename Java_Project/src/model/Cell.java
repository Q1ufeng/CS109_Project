package model;

import java.io.Serializable;
/**
 * This class describe the slot for Chess in Chessboard
 * */
public class Cell implements Serializable {
    // the position for chess
    private ChessPiece piece;


    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }

    //对格子进行的重载操作，实际上是判断两个格子的棋子是不是一样的
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cell)
            return this.getPiece().equals(((Cell) obj).getPiece());
        return false;
    }
}
