import Command.Command;
import Command.GameController;
import Command.MoveLeftCommand;
import Command.MoveRightCommand;
import Command.MoveDownCommand;
import Command.RotateCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InputHandler {

    private final Map<String, Command> commands = new HashMap<>();

    public InputHandler(GameController controller) {
        commands.put("a", new MoveLeftCommand(controller));
        commands.put("d", new MoveRightCommand(controller));
        commands.put("s", new MoveDownCommand(controller));
        commands.put("r", new RotateCommand(controller));
    }

    public void handleInput(String input) {
        Command command = commands.get(input);
        if (command != null) {
            command.execute();
        }
    }
}

