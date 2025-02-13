package edu.austral.dissis.chess;

import root.common.enums.Color
import root.common.adapter.Adapter
import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.chess.gui.PlayerColor.BLACK
import edu.austral.dissis.chess.gui.PlayerColor.WHITE
import root.common.Interfaces.GameInterface
import root.factory.Game_factory

class MyGame: GameEngine {
//    private var myGame: GameInterface = game_factory.create_normal_game_checkers();
//    private var myGame: GameInterface = game_factory.create_alterantive_game_chess()
//    private var myGame: GameInterface = game_factory.create_extensive_game_chess()
//    private var myGame: GameInterface = game_factory.create_extensive_checkers()
        private var myGame: GameInterface = Game_factory.create_normal_game_chess();
    private var currentPlayer = getCurrentPlayer(myGame)

    override fun applyMove(move: Move): MoveResult {
        val movement = Adapter.toMovement(move, myGame.board)
        val fromPiece = movement.oldPos.piece
        val toPiece = movement.newPos.piece

        return if (fromPiece == null)
            InvalidMove("No piece in (${move.from.row}, ${move.from.column})")
        else if (Adapter.adaptColour(fromPiece.color) != currentPlayer)
            InvalidMove("Piece does not belong to the current player")
        else if (toPiece != null && Adapter.adaptColour(toPiece.color) == currentPlayer)
            InvalidMove("There is a piece in (${move.to.row}, ${move.to.column})")
        else {
            val myNewGame = myGame
            myGame = myGame.move(movement.oldPos, movement.newPos)
            if (myGame.board == myNewGame.board) {
                return InvalidMove("Invalid move")
            }
            if (myGame.validateVictory(myGame.chessPlayers, myGame.board)) {
                return GameOver(currentPlayer)
            } else {
                currentPlayer = getCurrentPlayer(myGame)
                NewGameState(Adapter.getPieces(myGame.board), currentPlayer)
            }
        }
    }

    override fun init(): InitialState {
        return InitialState(Adapter.adaptBoard(myGame.board), (Adapter.getPieces(myGame.board)), WHITE)
    }


    public fun getWinner(): PlayerColor {
        if (myGame.validateVictory(myGame.chessPlayers, myGame.board)) {
            if (currentPlayer == WHITE) BLACK
            else WHITE
        }
        return WHITE
    }
}
public fun getCurrentPlayer (myGame: GameInterface): PlayerColor{
    for (i in 0 until myGame.getChessPlayers().size) {
        if (myGame.getChessPlayers()[i].turn) {
            return if (myGame.getChessPlayers()[i].color == Color.WHITE) WHITE else BLACK
        }
    }
    return WHITE
}
