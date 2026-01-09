package Decorator;


import Board.Board;
import TetrisPieces.*;


import java.util.Random;

/**
 * Factory that creates decorated pieces
 * Demonstrates the Decorator Pattern by wrapping pieces with various decorators
 */
public class DecoratedPieceFactory implements PieceFactory {
    private static Random rand = new Random();
    private Board board; // Needed for ghost piece
    private boolean enableGhost;
    private boolean enableColors;
    private boolean enableBonus;

    public DecoratedPieceFactory(Board board, boolean enableGhost, boolean enableColors, boolean enableBonus) {
        this.board = board;
        this.enableGhost = enableGhost;
        this.enableColors = enableColors;
        this.enableBonus = enableBonus;
    }

    @Override
    public TetrisPiece createTetrisPiece() {
        int startX = 4;
        int startY = 0;

        // Create base piece
        TetrisPiece piece = createBasePiece(startX, startY);

        // Randomly apply decorators based on settings
        piece = applyDecorators(piece);

        return piece;
    }

    /**
     * Create the base piece without decorators
     */
    private TetrisPiece createBasePiece(int startX, int startY) {
        int choice = rand.nextInt(7);

        switch (choice) {
            case 0: return new IPiece(startX, startY);
            case 1: return new OPiece(startX, startY);
            case 2: return new TPiece(startX, startY);
            case 3: return new SPiece(startX, startY);
            case 4: return new ZPiece(startX, startY);
            case 5: return new JPiece(startX, startY);
            case 6: return new LPiece(startX, startY);
            default: throw new IllegalStateException("Unexpected value");
        }
    }

    /**
     * Apply decorators to the piece
     * This demonstrates how decorators can be stacked
     */
    private TetrisPiece applyDecorators(TetrisPiece piece) {
        // Apply ghost decorator
        if (enableGhost) {
            piece = new GhostPieceDecorator(piece, board);
        }

        return piece;
    }

    // Setters to enable/disable decorators on the fly
    public void setEnableGhost(boolean enable) {
        this.enableGhost = enable;
    }

    public void setEnableColors(boolean enable) {
        this.enableColors = enable;
    }

    public void setEnableBonus(boolean enable) {
        this.enableBonus = enable;
    }
}