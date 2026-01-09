import TetrisPieces.TetrisPiece;
import singleton.GameManager;

public class Board {

    private final int width;
    private final int height;
    private final int[][] grid;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new int[height][width];
    }

    public boolean isCellOccupied(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return true; // treat out-of-bounds as occupied
        }
        return grid[y][x] == 1;
    }

    public boolean canPlace(TetrisPiece piece, int newX, int newY) {
        int[][] shape = piece.getShape();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == 1) {
                    int boardX = newX + col;
                    int boardY = newY + row;

                    if (isCellOccupied(boardX, boardY)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void placePiece(TetrisPiece piece) {
        int[][] shape = piece.getShape();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == 1) {
                    grid[piece.getY() + row][piece.getX() + col] = 1;
                }
            }
        }
    }



    public int clearFullLines() {
        int cleared = 0;

        for (int row = 0; row < height; row++) {
            boolean full = true;
            for (int col = 0; col < width; col++) {
                if (grid[row][col] == 0) {
                    full = false;
                    break;
                }
            }

            if (full) {
                removeLine(row);
                cleared++;
            }
        }
        if (cleared > 0) {
            GameManager.getInstance().clearLines(cleared);
            GameManager.getInstance().addScore(cleared * 100);
        }
        return cleared;
    }

    private void removeLine(int rowToRemove) {
        for (int row = rowToRemove; row > 0; row--) {
            grid[row] = grid[row - 1].clone();
        }
        grid[0] = new int[width];
    }

    public int[][] getGrid() {
        return grid;
    }
}
