package root.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board implements root.common.Interfaces.Board {
    private Position[][] tablero;

    private int row;
    private int column;

    public Board(int row, int column) {
        this.row = row;
        this.column = column;
        tablero = new Position[row][column];
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                tablero[x][y] = new Position(x, y, null);
            }
        }
    }

    public Position[][] getBoard() {
        return tablero;
    }

    public Piece getPiece(int x, int y) {
        return tablero[x][y].getPiece();
    }

    public Position getPosition(int x, int y) {
        if (x >=0 && x < row && y >=0 && y < column) {
            return tablero[x][y];
        }
        return null;
    }

    public Position getPositionWithPiece(Piece piece) {
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                Position currentPosition = tablero[x][y];
                if (currentPosition.getPiece() != null && currentPosition.getPiece() == piece) {
                    return currentPosition;
                }
            }
        }
        return null;
    }

    public boolean hasPiece(Position position) {
        return tablero[position.getX()][position.getY()].hasPiece();
    }

    public Board movePiece(Position oldPosition, Position newPosition, Piece piece) {
        if (newPosition.hasPiece()) {
            if (differentTeams(newPosition, piece)) {
                return move(oldPosition, newPosition, piece);
            }
        } else if (!newPosition.hasPiece()) {
            return move(oldPosition, newPosition, piece);
        }
        return this;
    }

    private static boolean differentTeams(Position newPosition, Piece piece) {
        return !Objects.equals(newPosition.getPiece().getColor(), piece.getColor());
    }

    public Board move(Position oldPosition, Position newPosition, Piece piece) {
        if (piece.moveValidator(oldPosition, newPosition, this)) {
            Piece piece1 = pieceFirstMove(piece);
            Board newTablero = this.copy();
            newTablero.getBoard()[oldPosition.getX()][oldPosition.getY()] = new Position(oldPosition.getX(),oldPosition.getY());
            newTablero.getBoard()[newPosition.getX()][newPosition.getY()] = new Position(newPosition.getX(),newPosition.getY(), piece1);
            return newTablero;
        }
        return this;
    }
    private Piece pieceFirstMove (Piece piece){
        if (piece.isFirstMove()){
            return new Piece(piece.getName(),piece.getColor(),piece.getMovements(), piece.getId(), false);
        }
        return piece;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public List<Piece> getPiecies() {
        List<Piece> pieces = new ArrayList<>();
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                if (tablero[x][y].hasPiece()) {
                    pieces.add(tablero[x][y].getPiece());
                }
            }
        }
        return pieces;
    }
    public Board copy (){
        Board newBoard = new Board(row, column);
        for (int x=0; x<row; x++){
            for (int y=0; y<column; y++){
                newBoard.getBoard()[x][y] = new Position(x,y,tablero[x][y].getPiece());
            }
        }
        return newBoard;
    }
}
