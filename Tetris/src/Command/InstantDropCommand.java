package Command;

import facade.GameFacade;

public class InstantDropCommand implements Command {
    private final GameFacade facade;

    public InstantDropCommand(GameFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.hardDrop();
    }
}