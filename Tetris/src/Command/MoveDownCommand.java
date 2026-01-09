package Command;

import facade.GameFacade;

public class MoveDownCommand implements Command {
    private final GameFacade facade;

    public MoveDownCommand(GameFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.moveDown();
    }
}
