package Decorator;

import Board.Board;
import TetrisPieces.TetrisPiece;

/**
 * Ghost Piece Decorator - shows where the piece will land
 * This is useful for players to see the landing position
 */
public class GhostPieceDecorator extends PieceDecorator {
    private Board board;
    private int ghostY;

    public GhostPieceDecorator(TetrisPiece piece, Board board) {
        super(piece);
        this.board = board;
        calculateGhostPosition();
    }

    /**
     * Calculate where the piece would land if dropped
     */
    private void calculateGhostPosition() {
        ghostY = decoratedPiece.getY();

        // Keep moving down until we hit something
        while (board.canPlace(decoratedPiece, decoratedPiece.getX(), ghostY + 1)) {
            ghostY++;
        }
    }

    /**
     * Get the Y position where the ghost should be displayed
     */
    public int getGhostY() {
        calculateGhostPosition(); // Recalculate each time
        return ghostY;
    }

    @Override
    public String getSpecialEffect() {
        return "👻 Ghost Preview";
    }

    /**
     * Check if we should show the ghost (only if it's below current position)
     */
    public boolean shouldShowGhost() {
        return ghostY > decoratedPiece.getY();
    }
}