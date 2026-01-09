package Command;

import Board.Board;
import Observer.TickObserver;
import TetrisPieces.PieceFactory;
import TetrisPieces.TetrisPiece;

/**
 * GameController now implements TickObserver
 * This allows it to automatically move pieces down when notified by GameTimer
 */
public class GameController implements TickObserver {

    private final Board board;
    private TetrisPiece currentPiece;
    private final PieceFactory factory;
    private boolean gameOver;

    public GameController(Board board, PieceFactory factory) {
        this.board = board;
        this.factory = factory;
        this.currentPiece = factory.createTetrisPiece();
        this.gameOver = false;
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

    /**
     * Hard drop - instantly drop piece to the bottom
     */
    public void hardDrop() {
        if (gameOver) {
            return;
        }

        // Keep moving down until we can't
        while (board.canPlace(currentPiece,
                currentPiece.getX(),
                currentPiece.getY() + 1)) {
            currentPiece.setPosition(
                    currentPiece.getX(),
                    currentPiece.getY() + 1
            );
        }

        // Lock the piece
        lockPiece();
    }

    private void tryMove(int dx, int dy) {
        if (gameOver) {
            return;
        }

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

        // Check if new piece can be placed (game over condition)
        if (!board.canPlace(currentPiece, currentPiece.getX(), currentPiece.getY())) {
            gameOver = true;
            System.out.println("\n🎮 GAME OVER! 🎮");
            singleton.GameManager.getInstance().gameOver();
        }
    }

    public TetrisPiece getCurrentPiece() {
        return currentPiece;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    // ===== TickObserver Implementation =====

    /**
     * Called automatically by GameTimer - makes piece fall
     */
    @Override
    public void onTick() {
        if (!gameOver) {
            moveDown(); // Automatically move piece down
        }
    }

    /**
     * Called when game speed changes (level up)
     */
    @Override
    public void onSpeedChange(long newTickSpeed) {
        System.out.println("⚡ Speed increased! New tick: " + newTickSpeed + "ms");
    }
}