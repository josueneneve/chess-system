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
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	/**
	 * Returns a piece given a row and column.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */

	public Piece piece(int row, int column) {
		return pieces[row][column];
	}

	/**
	 * Returns a position.
	 * 
	 * @param position
	 * @return
	 */

	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}

	/**
	 * Assign a piece at a certain position of the piece matrix.
	 * 
	 * @param piece
	 * @param position
	 */

	public void placePiece(Piece piece, Position position) {
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

}
