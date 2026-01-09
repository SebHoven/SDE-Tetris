import Board.Board;
import Command.GameController;
import Observer.LeaderboardManager;
import Observer.ScoreDisplay;
import TetrisPieces.AbstractPieceFactory;
import TetrisPieces.PieceFactory;
import facade.GameFacade;
import singleton.GameManager;

import java.util.Scanner;

public class GameLoop {

    public static void main(String[] args) {
        Board board = new Board(10, 20);
        PieceFactory factory = new AbstractPieceFactory();
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
            game.render(board, controller);
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


}