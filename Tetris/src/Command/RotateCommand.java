package Command;

public class RotateCommand implements Command {

    private GameController controller;

    public RotateCommand(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.rotate();
    }
}
