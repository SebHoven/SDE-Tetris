package Command;

import facade.GameFacade;

public class MoveLeftCommand implements Command {
    private final GameFacade facade;

    public MoveLeftCommand(GameFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.moveLeft();
    }
}