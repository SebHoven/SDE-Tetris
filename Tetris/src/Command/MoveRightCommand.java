package Command;

public class MoveRightCommand implements Command {

    private GameController controller;

    public MoveRightCommand(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.moveRight();
    }
}
