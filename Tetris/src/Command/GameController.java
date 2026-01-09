package Command;

import Board.Board;
import TetrisPieces.PieceFactory;
import TetrisPieces.TetrisPiece;

public class GameController {

    private final Board board;
    private TetrisPiece currentPiece;
    private final PieceFactory factory;

    public GameController(Board board, PieceFactory factory) {
        this.board = board;
        this.factory = factory;
        this.currentPiece = factory.createTetrisPiece();
    }

    public void moveLeft() {
        tryMove(-1, 0);
    }

    public void moveRight() {
        tryMove(1, 0);
    }

    public void moveDown() {
        tryMove(0, 1);
    }

    public void rotate() {
        currentPiece.rotate();
        if (!board.canPlace(currentPiece,
                currentPiece.getX(),
                currentPiece.getY())) {
            // rollback rotation
            currentPiece.rotate();
            currentPiece.rotate();
            currentPiece.rotate();
        }
    }

    public void hardDrop() {
        while (board.canPlace(currentPiece, currentPiece.getX(), currentPiece.getY() + 1)) {
            currentPiece.setPosition(currentPiece.getX(), currentPiece.getY() + 1);
        }

        // Lock the piece in place
        lockPiece();
    }

    private void tryMove(int dx, int dy) {
        if (board.canPlace(currentPiece,
                currentPiece.getX() + dx,
                currentPiece.getY() + dy)) {
            currentPiece.setPosition(
                    currentPiece.getX() + dx,
                    currentPiece.getY() + dy
            );
        } else if (dy == 1) {
            lockPiece();
        }
    }

    private void lockPiece() {
        board.placePiece(currentPiece);
        board.clearFullLines();
        currentPiece = factory.createTetrisPiece();
    }

    public TetrisPiece getCurrentPiece() {
        return currentPiece;
    }

}
