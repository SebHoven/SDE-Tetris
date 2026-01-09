package Decorator;

import TetrisPieces.TetrisPiece;

/**
 * Base Decorator class for TetrisPiece
 * This is a structural design pattern that allows adding new behaviors
 * to pieces dynamically without modifying the original piece classes
 */
public abstract class PieceDecorator implements TetrisPiece {
    protected TetrisPiece decoratedPiece;

    public PieceDecorator(TetrisPiece piece) {
        this.decoratedPiece = piece;
    }

    // Delegate all basic methods to the decorated piece
    @Override
    public int[][] getShape() {
        return decoratedPiece.getShape();
    }

    @Override
    public int getX() {
        return decoratedPiece.getX();
    }

    @Override
    public int getY() {
        return decoratedPiece.getY();
    }

    @Override
    public void moveDown() {
        decoratedPiece.moveDown();
    }

    @Override
    public void moveLeft() {
        decoratedPiece.moveLeft();
    }

    @Override
    public void moveRight() {
        decoratedPiece.moveRight();
    }

    @Override
    public void rotate() {
        decoratedPiece.rotate();
    }

    @Override
    public void setPosition(int x, int y) {
        decoratedPiece.setPosition(x, y);
    }

    // Subclasses can add their own special behaviors
    public abstract String getSpecialEffect();
}