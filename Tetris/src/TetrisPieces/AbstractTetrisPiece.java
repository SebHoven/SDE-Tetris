package TetrisPieces;

public abstract class AbstractTetrisPiece implements TetrisPiece {

    protected int x;
    protected int y;
    protected int[][] shape;

    public AbstractTetrisPiece(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    @Override
    public int[][] getShape() {
        return shape;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void moveDown() {
        y++;
    }

    @Override
    public void moveLeft() {
        x--;
    }

    @Override
    public void moveRight() {
        x++;
    }

    @Override
    public void rotate() {
        shape = rotateMatrix(shape);
    }

    protected int[][] rotateMatrix(int[][] matrix) {
        int size = matrix.length;
        int[][] rotated = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                rotated[j][size - 1 - i] = matrix[i][j];
            }
        }
        return rotated;
    }
}
