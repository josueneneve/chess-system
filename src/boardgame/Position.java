package boardgame;

public class Position {

	private int row;
	private int column;

	/**
	 * Representation a position on the board.
	 * 
	 * @param row
	 * @param column
	 */

	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	/**
	 * Receives a new row and a new column.
	 * 
	 * @param row
	 * @param column
	 */
	
	public void setValues(int row, int column) {
		this.row = row;
		this.column = column;
	}

	@Override
	public String toString() {
		return row + ", " + column;
	}

}
