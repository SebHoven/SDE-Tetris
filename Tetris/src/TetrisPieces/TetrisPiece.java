package TetrisPieces;

public interface TetrisPiece {
    int[][] getShape();
    int getX();
    int getY();
    void moveDown();
    void moveLeft();
    void moveRight();
    void rotate();
}
