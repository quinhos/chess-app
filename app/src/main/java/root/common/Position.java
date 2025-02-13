package root.common;

import root.common.enums.Color;

public class Position {
    private final int x;
    private final int y;
    private Piece piece;

    public Position(int x, int y, Piece piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }
    public Position (int x, int y){
        this.x = x;
        this.y = y;
        this.piece = null;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasPiece(){
        return piece != null;
    }
    public Color getColor (){
        return piece.getColor();
    }
    public Position delta(Position newpos){
        return new Position(newpos.getX() - this.getX(), newpos.getY() - this.getY());
    }

}
