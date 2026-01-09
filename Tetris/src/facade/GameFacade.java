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



}
