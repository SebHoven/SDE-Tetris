import Command.GameController;

/**
 * InputHandler - Processes user input commands
 */
public class InputHandler {
    private GameController controller;

    public InputHandler(GameController controller) {
        this.controller = controller;
    }

    public void handleInput(String input) {
        input = input.toLowerCase().trim();

        switch (input) {
            case "left":
            case "a":
                controller.moveLeft();
                break;
            case "right":
            case "d":
                controller.moveRight();
                break;
            case "down":
            case "s":
                controller.moveDown();
                break;
            case "rotate":
            case "w":
            case "r":
                controller.rotate();
                break;
            default:
                System.out.println("Unknown command: " + input);
                break;
        }
    }
}