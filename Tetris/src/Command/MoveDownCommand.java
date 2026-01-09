package Command;

public class MoveDownCommand implements Command {

    private GameController controller;

    public MoveDownCommand(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.moveDown();
    }
}
