import Board.Board;
import Command.GameController;
import Observer.GameObserver;
import Observer.LeaderboardManager;
import Observer.ScoreDisplay;
import TetrisPieces.AbstractPieceFactory;
import TetrisPieces.PieceFactory;
import TetrisPieces.TetrisPiece;
import singleton.GameManager;

import java.util.Scanner;

public class GameLoop {

    public static void main(String[] args) {
        Board board = new Board(10, 20);
        PieceFactory factory = new AbstractPieceFactory();
        GameController controller = new GameController(board, factory);
        InputHandler inputHandler = new InputHandler(controller);

        // Create all observers
        ScoreDisplay scoreDisplay = new ScoreDisplay();
        LeaderboardManager leaderboard = new LeaderboardManager();


        Scanner scanner = new Scanner(System.in);

        // Register all observers with GameManager
        GameManager.addObserver(scoreDisplay);
        GameManager.addObserver(leaderboard);

        boolean gameRunning = true;

        while (gameRunning) {
            render(board, controller);
            String input = scanner.nextLine();

            // Check for quit command
            if (input.equalsIgnoreCase("quit")) {
                GameManager.getInstance().gameOver();
                gameRunning = false;
                break;
            }

            inputHandler.handleInput(input);

            // Example: Check if game is over (you'd implement this logic properly)
            // if (controller.isGameOver()) {
            //     GameManager.getInstance().gameOver();
            //     gameRunning = false;
            // }
        }

        scanner.close();
        System.out.println("Game ended!");
    }

    private static void render(Board board, GameController controller) {
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