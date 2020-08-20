package boardgame;

public abstract class Piece {

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
	
	public abstract boolean[][] possibleMoves();
	
	/**
	 * Checks if a piece can move to a certain position,
	 * returning true or false.
	 * 
	 * @param position
	 * @return
	 */
	
	public boolean possibleMove(Position position) {
		
		/**
		 * Hook methods, is a method that makes a hook with the sub-class.
		 */
		
		return possibleMoves() [position.getRow()][position.getColumn()];
	}

	
	/**
	 * Checks if exists a possible move to the piece.
	 * 
	 * @return
	 */
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
}
