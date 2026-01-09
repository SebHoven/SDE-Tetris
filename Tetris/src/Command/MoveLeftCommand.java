package Command;

public class MoveLeftCommand implements Command {

    private GameController controller;

    public MoveLeftCommand(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.moveLeft();
    }
}
