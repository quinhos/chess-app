package root.chess.movements; // Asegúrate de que el paquete esté correctamente nombrado.

import root.common.Board;
import root.common.Piece;
import root.common.Position;
import root.common.enums.Color;
import root.common.enums.Pieces;
import root.common.Interfaces.MovementValidator;

import java.util.Objects;

public class PawnEatDiagonal implements MovementValidator { // Convenio: Nombre de clase en CamelCase.

    private int xPos;
    private int xNeg;
    private int yPos;
    private int yNeg;

    public PawnEatDiagonal(int xPos, int xNeg, int yPos, int yNeg) { // Nombre del constructor en CamelCase.
        this.xPos = xPos;
        this.xNeg = xNeg;
        this.yNeg = yNeg;
        this.yPos = yPos;
    }

    public boolean pawnDiagonalMove(Position oldPos, Position newPos) {
        int x = oldPos.getX() - newPos.getX();
        int y = oldPos.getY() - newPos.getY();
        Piece piece = oldPos.getPiece();
        Piece piece2 = newPos.getPiece();
        if (pawnEatsDiagonalValid(x, y, piece)) {
            if (piece.getColor() == Color.WHITE && x > 0) {
                return validateDiagon(piece, piece2);
            } else if (piece.getColor() == Color.BLACK && x < 0) {
                return validateDiagon(piece, piece2);
            }
        }
        return false;
    }

    private static boolean pawnEatsDiagonalValid(int x, int y, Piece piece) {
        return piece.getName() == Pieces.PAWN && Math.abs(x) == 1 && Math.abs(y) == 1;
    }

    public boolean validateDiagon(Piece piece, Piece piece2) {
        if (piece2 != null) {
            return !Objects.equals(piece2.getColor(), piece.getColor());
        }
        return false;
    }

    @Override
    public boolean validateMove(Position oldPos, Position newPos) {
        if (pawnDiagonalMove(oldPos, newPos)) {
            if (newPos.hasPiece()) {
                return !Objects.equals(newPos.getColor(), oldPos.getColor());
            }
        }
        return false;
    }

    @Override
    public boolean obstacle(Position oldPos, Position newPos, Board board) {
        return false;
    }
}
