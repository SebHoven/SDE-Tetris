import Board.Board;
import Command.GameController;
import Decorator.DecoratedPieceFactory;
import Decorator.GhostPieceDecorator;
import Decorator.PieceDecorator;
import Observer.*;
import TetrisPieces.PieceFactory;
import TetrisPieces.TetrisPiece;
import facade.GameFacade;
import singleton.GameManager;

import java.util.Scanner;

public class GameLoop {

    private static volatile boolean needsRender = true;
    private static volatile boolean running = true;

    public static void main(String[] args) {
        Board board = new Board(10, 20);

        // Use DecoratedPieceFactory
        PieceFactory factory = new DecoratedPieceFactory(
                board,
                true  // Enable ghost pieces
        );

        GameController controller = new GameController(board, factory);
        GameFacade game = new GameFacade(board, controller);
        InputHandler inputHandler = new InputHandler(game);

        // ===== OBSERVER PATTERN FOR AUTOMATIC FALLING =====
        // Create GameTimer (Subject) - pieces fall every 1000ms (1 second) initially
        GameTimer gameTimer = new GameTimer(1000);

        // Register GameController as observer - it will move pieces down automatically
        gameTimer.addObserver(controller);

        // Add observer that triggers rendering on each tick
        gameTimer.addObserver(new TickObserver() {
            @Override
            public void onTick() {
                needsRender = true; // Signal that we need to re-render
            }

            @Override
            public void onSpeedChange(long newTickSpeed) {
                // Speed changed
            }
        });

        // ===== OBSERVER PATTERN FOR GAME EVENTS =====
        ScoreDisplay scoreDisplay = new ScoreDisplay();
        LeaderboardManager leaderboard = new LeaderboardManager();

        // Create LevelSpeedObserver to adjust timer speed based on level
        LevelSpeedObserver levelSpeedObserver = new LevelSpeedObserver(gameTimer, 1000);

        // Register all observers with GameManager
        GameManager.addObserver(scoreDisplay);
        GameManager.addObserver(leaderboard);
        GameManager.addObserver(levelSpeedObserver); // Watches for level changes

        Scanner scanner = new Scanner(System.in);

        // Start the automatic falling timer
        System.out.println("🎮 Starting Tetris - Pieces will fall automatically!");
        System.out.println("⏱️  Initial speed: 1000ms per tick");
        System.out.println("Commands: left, right, down, rotate, space (hard drop), pause, quit\n");

        gameTimer.start();

        // Create input thread to handle user commands without blocking
        Thread inputThread = new Thread(() -> {
            while (running && !controller.isGameOver()) {
                try {
                    if (scanner.hasNextLine()) {
                        String input = scanner.nextLine();

                        // Check for quit command
                        if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("q")) {
                            System.out.println("Quitting game...");
                            GameManager.getInstance().gameOver();
                            running = false;
                            break;
                        }

                        // Check for pause command
                        if (input.equalsIgnoreCase("pause") || input.equalsIgnoreCase("p")) {
                            if (gameTimer.isRunning()) {
                                gameTimer.pause();
                                System.out.println("⏸️  Game Paused - Type 'pause' again to resume");
                            } else {
                                gameTimer.resume();
                                System.out.println("▶️  Game Resumed");
                            }
                            needsRender = true;
                        } else {
                            // Use InputHandler for all other commands
                            inputHandler.handleInput(input);
                            needsRender = true;
                        }
                    }
                    Thread.sleep(10); // Small delay to prevent CPU spinning
                } catch (Exception e) {
                    break;
                }
            }
        });
        inputThread.setDaemon(true);
        inputThread.start();

        // Main render loop
        while (running && !controller.isGameOver()) {
            if (needsRender) {
                render(board, controller, gameTimer);
                needsRender = false;
            }

            try {
                Thread.sleep(50); // Small delay to prevent excessive CPU usage
            } catch (InterruptedException e) {
                break;
            }
        }

        gameTimer.stop();
        running = false;

        System.out.println("\n🎮 Game ended! Final Score: " + GameManager.getInstance().getScore());
        System.out.println("Press Enter to exit...");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore
        }

        scanner.close();
        System.exit(0);
    }



    private static void render(Board board, GameController controller, GameTimer gameTimer) {
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

        // Clear console
        clearScreen();

        // Print buffer
        printBuffer(buffer);

        // Display game stats
        System.out.println("═══════════════════════════════");
        System.out.println("Score: " + GameManager.getInstance().getScore());
        System.out.println("Level: " + GameManager.getInstance().getLevel());
        System.out.println("Lines: " + GameManager.getInstance().getLinesCleared());
        System.out.println("Speed: " + gameTimer.getTickSpeed() + "ms");
        System.out.println("Status: " + (gameTimer.isRunning() ? "▶️ Running" : "⏸️ Paused"));

        // Show special effects from decorators
        if (piece instanceof PieceDecorator) {
            PieceDecorator decorator = (PieceDecorator) piece;
            System.out.println("Effect: " + decorator.getSpecialEffect());
        }

        System.out.println("═══════════════════════════════");
        System.out.println("Commands: left/a, right/d, down/s, rotate/w/r");
        System.out.println("          space (hard drop), pause/p, quit/q");
    }

    private static void clearScreen() {
        try {
            // Try ANSI escape codes first (works on most terminals)
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            // Fallback to newlines
            System.out.print("\n".repeat(50));
        }
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
                        buffer[drawY][drawX] = '░';
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

    private static void printBuffer(char[][] buffer) {
        for (int y = 0; y < buffer.length; y++) {
            for (int x = 0; x < buffer[y].length; x++) {
                System.out.print(buffer[y][x]);
            }
            System.out.println();
        }
    }
}