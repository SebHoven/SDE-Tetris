package Command;

import facade.GameFacade;

public class MoveRightCommand implements Command {
    private final GameFacade facade;

    public MoveRightCommand(GameFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.moveRight();
    }
}