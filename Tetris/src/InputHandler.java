import Command.Command;
import Command.MoveLeftCommand;
import Command.MoveRightCommand;
import Command.MoveDownCommand;
import Command.RotateCommand;
import Command.InstantDropCommand;
import facade.GameFacade;

import java.util.HashMap;
import java.util.Map;

/**
 * InputHandler - Processes user input commands
 */
public class InputHandler {
    private final Map<String, Command> commands = new HashMap<>();


    public InputHandler(GameFacade game) {
        commands.put("a", new MoveLeftCommand(game));
        commands.put("d", new MoveRightCommand(game));
        commands.put("s", new MoveDownCommand(game));
        commands.put("r", new RotateCommand(game));
        commands.put(" ", new InstantDropCommand(game));
    }

    public void handleInput(String input) {
        Command command = commands.get(input);
        if (command != null) {
            command.execute();
        }
    }
}