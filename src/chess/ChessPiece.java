package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	/**
	 * Get a position and transforms into a ChessPosition.
	 * 
	 * @return
	 */
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	/**
	 * Check if there is a piece in that position.
	 * 
	 * @param position
	 * @return
	 */
	
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p.getColor() != color;
	}

}
