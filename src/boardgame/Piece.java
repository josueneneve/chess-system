package boardgame;

public class Piece {

	/**
	 * This type of position is not yet the position of chess, it is a simple
	 * position of matrix. Has an association with the board.
	 */

	protected Position position;
	private Board board;

	/**
	 * Only the board is passed at the time of creating the piece. The position of a
	 * piece newly created, going to be null.
	 * 
	 * @param board
	 */

	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}

}
