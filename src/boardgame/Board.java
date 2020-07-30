package boardgame;

public class Board {

	private int rows;
	private int columns;

	/**
	 * A matrix of pieces, going to be instantiated with amount of rows and columns
	 * reported.
	 */

	private Piece[][] pieces;

	/**
	 * Number of rows and columns in the board.
	 * 
	 * @param rows
	 * @param columns
	 */

	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	/**
	 * Returns a piece given a row and column.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */

	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}

	/**
	 * Returns a position.
	 * 
	 * @param position
	 * @return
	 */

	public Piece piece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}

	/**
	 * Assign a piece at a certain position of the piece matrix, going from null to
	 * past position.
	 * 
	 * @param piece
	 * @param position
	 */

	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	/**
	 * Remove a piece in board.
	 * 
	 * @param position
	 * @return
	 */
	
	public Piece removePiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		if (piece(position) == null) {
			return null;
		}
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}

	/**
	 * Checks if the position exists, returning true or false.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */

	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	/**
	 * Checks if the position exists, returning true or false, reusing the method
	 * above.
	 * 
	 * @param position
	 * @return
	 */

	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	/**
	 * Checks if have a piece in that position.
	 * 
	 * @param position
	 * @return
	 */

	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null;
	}

}
