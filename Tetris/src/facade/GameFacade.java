package facade;

import Command.GameController;
import Board.Board;
import TetrisPieces.TetrisPiece;
import singleton.GameManager;

public class GameFacade {

    private final GameController controller;
    private final Board board;

    public GameFacade(Board board, GameController controller) {
        this.board = board;
        this.controller = controller;
    }

    // Movement methods (optional for demo)
    public void moveLeft() {
        controller.moveLeft();
    }

    public void moveRight() {
        controller.moveRight();
    }

    public void rotate() {
        controller.rotate();
    }

    public void moveDown() {
        controller.moveDown();
    }

    public void hardDrop() {
        controller.hardDrop();
    }

    public static void render(Board board, GameController controller) {
        int width = board.getWidth();
        int height = board.getHeight();

        char[][] buffer = new char[height][width];

        // Fill with empty cells
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                buffer[y][x] = '.';
            }
        }

        // Draw locked blocks from board
        int[][] grid = board.getGrid();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x] == 1) {
                    buffer[y][x] = '#';
                }
            }
        }

        // Draw current falling piece
        TetrisPiece piece = controller.getCurrentPiece();
        int[][] shape = piece.getShape();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == 1) {
                    int drawX = piece.getX() + col;
                    int drawY = piece.getY() + row;

                    if (drawY >= 0 && drawY < height &&
                            drawX >= 0 && drawX < width) {
                        buffer[drawY][drawX] = '@';
                    }
                }
            }
        }

        // Clear console (simple version)
        System.out.print("\n".repeat(20));

        // Print buffer
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(buffer[y][x]);
            }
            System.out.println();
        }

        // Display game stats
        System.out.println("═══════════════════");
        System.out.println("Score: " + GameManager.getInstance().getScore());
        System.out.println("Level: " + GameManager.getInstance().getLevel());
        System.out.println("Lines: " + GameManager.getInstance().getLinesCleared());
        System.out.println("═══════════════════");
        System.out.println("Commands: left, right, down, rotate, quit");
    }

}
