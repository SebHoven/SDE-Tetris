import Board.Board;
import Command.GameController;
import Decorator.DecoratedPieceFactory;
import Decorator.GhostPieceDecorator;
import Decorator.PieceDecorator;
import Observer.LeaderboardManager;
import Observer.ScoreDisplay;
import TetrisPieces.PieceFactory;
import TetrisPieces.TetrisPiece;
import facade.GameFacade;
import singleton.GameManager;

import java.util.Scanner;

public class GameLoop {

    public static void main(String[] args) {
        Board board = new Board(10, 20);

        // Use DecoratedPieceFactory instead of AbstractPieceFactory
        // This demonstrates the Decorator Pattern!
        PieceFactory factory = new DecoratedPieceFactory(
                board,
                true,  // Enable ghost pieces
                true,  // Enable colors
                true   // Enable bonus pieces
        );

        GameController controller = new GameController(board, factory);
        GameFacade game = new GameFacade(board, controller);
        InputHandler inputHandler = new InputHandler(game);

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

        TetrisPiece piece = controller.getCurrentPiece();

        // Draw ghost piece if decorator is present
        if (piece instanceof GhostPieceDecorator) {
            GhostPieceDecorator ghostPiece = (GhostPieceDecorator) piece;
            if (ghostPiece.shouldShowGhost()) {
                drawGhostPiece(buffer, ghostPiece, width, height);
            }
        }

        // Draw current falling piece
        drawPiece(buffer, piece, width, height, '@');

        // Clear console (simple version)
        System.out.print("\n".repeat(20));

        // Print buffer with colors if available
        printBuffer(buffer, piece);

        // Display game stats and special effects
        System.out.println("═══════════════════");
        System.out.println("Score: " + GameManager.getInstance().getScore());
        System.out.println("Level: " + GameManager.getInstance().getLevel());
        System.out.println("Lines: " + GameManager.getInstance().getLinesCleared());

        // Show special effects from decorators
        if (piece instanceof PieceDecorator) {
            PieceDecorator decorator = (PieceDecorator) piece;
            System.out.println("Effect: " + decorator.getSpecialEffect());
        }

        System.out.println("═══════════════════");
        System.out.println("Commands: left, right, down, rotate, quit");
    }

    private static void drawGhostPiece(char[][] buffer, GhostPieceDecorator ghostPiece, int width, int height) {
        int[][] shape = ghostPiece.getShape();
        int ghostY = ghostPiece.getGhostY();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == 1) {
                    int drawX = ghostPiece.getX() + col;
                    int drawY = ghostY + row;

                    if (drawY >= 0 && drawY < height &&
                            drawX >= 0 && drawX < width) {
                        buffer[drawY][drawX] = '░'; // Ghost character
                    }
                }
            }
        }
    }

    private static void drawPiece(char[][] buffer, TetrisPiece piece, int width, int height, char symbol) {
        int[][] shape = piece.getShape();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == 1) {
                    int drawX = piece.getX() + col;
                    int drawY = piece.getY() + row;

                    if (drawY >= 0 && drawY < height &&
                            drawX >= 0 && drawX < width) {
                        buffer[drawY][drawX] = symbol;
                    }
                }
            }
        }
    }

    private static void printBuffer(char[][] buffer, TetrisPiece piece) {
        String colorCode = "";
        String resetCode = "\u001B[0m";
        for (int y = 0; y < buffer.length; y++) {
            for (int x = 0; x < buffer[y].length; x++) {
                char cell = buffer[y][x];

                if (cell == '@') {
                    // Color the active piece
                    System.out.print(colorCode + cell + resetCode);
                } else {
                    System.out.print(cell);
                }
            }
            System.out.println();
        }
    }
}