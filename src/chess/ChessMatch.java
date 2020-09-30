package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	/**
	 * Rules of chess game.
	 */

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	/**
	 * Receives a dimension of board.
	 */

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	/**
	 * Returns a matrix of pieces chess, corresponding the ChessMatch.
	 * 
	 * @return
	 */

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

		/**
		 * For each piece traversed from the matrix, do a downcasting for ChessPiece.
		 */

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	/**
	 * Print possible positions to the piece if move.
	 * 
	 * @param sourcePosition
	 * @return
	 */
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	/**
	 * Converts the two position to positions in the matrix. 
	 * Source - start position
	 * Target - end position
	 * 
	 * @param sourcePosition
	 * @param targetPosition
	 * @return
	 */
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);	
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		
		/**
		 * Forehead has put a player in check.
		 */
		
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		
		/**
		 * Forehead if opponent put in check.
		 */
		
		check = (testCheck(opponent(currentPlayer))) ?  true : false;
		
		nextTurn();
		return (ChessPiece)capturedPiece;
	}
	
	/**
	 * Performs the movement of the piece.
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	
	private Piece makeMove(Position source, Position target) {
		
		/**
		 * Remove the piece from the source position.
		 */
		
		Piece p = board.removePiece(source);
		
		/**
		 * Remove a possible piece at the target position.
		 */
		
		Piece capturedPiece  = board.removePiece(target);
		
		board.placePiece(p, target);
		
		/**
		 * Remove a piece on the board and put in the list capturedPiece.
		 */
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
			
		return capturedPiece;
	}
	
	/**
	 * Undo the movement of the piece.
	 * 
	 * @param source
	 * @param target
	 * @param capturedPiece
	 */
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target);
		board.placePiece(p, source);
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
	/**
	 * Validation the positions of piece.
	 * 
	 * @param position
	 */
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		
		/**
		 * Throw an exception if the player moves a  wrong piece.
		 */
		
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("The chose piece is not yours");
		}
		
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chose piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	/**
	 * Changed turn.
	 */
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	/**
	 * Return the opponent's color
	 * 
	 * @param color
	 * @return
	 */
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	/**
	 * Finds the king of a certain color.
	 * 
	 * @param color
	 * @return
	 */
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.parallelStream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + "king on the board");
	}
	
	/**
	 * Test if the king of this color is in check.
	 * 
	 * @param color
	 * @return
	 */
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces =  piecesOnTheBoard.parallelStream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Receives a column and Row, instantiating a ChessPosition and converting to a toPosition.
	 * 
	 * @param column
	 * @param row
	 * @param piece
	 */
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	/**
	 * Placing the chess piece on the board.
	 */
	
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
