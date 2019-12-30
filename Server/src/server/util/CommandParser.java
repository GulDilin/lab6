package server.util;

import server.commands.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CommandParser {
    private final String[] SET_VALUES;
    private final Set<String> COMMAND_NAMES;

    public CommandParser(String[] commandNames) {
        this.SET_VALUES = commandNames;
        this.COMMAND_NAMES =  new HashSet<>(Arrays.asList(SET_VALUES));
    }

    public Command parse(String name, String args) throws NoSuchCommandException{
        Command command = null;

        if (!COMMAND_NAMES.contains(name)){
            throw new NoSuchCommandException("No such command");
        }

        switch (name) {
            case "test":
                command = new Test();
                break;
            case "remove":
                command = new RemoveElement(args);
                break;

            case "start":
                command = new Start();
                break;

            case "save":
                command = new OutputFile(args);
                break;

            case "import":
                command = new InputFile(args);
                break;

            case "help":
                command = new Help(COMMAND_NAMES);
                break;

            case "remove_lower":
                command = new RemoveLower(args);
                break;

            case "load":
                command = new Load();
                break;

            case "info":
                command = new Info();
                break;

            case "show":
                command = new Show();
                break;

            case "add":
                command = new AddElement(args);
                break;

            case "exit":
                command = new Exit();
                break;
        }

        return command;
    }
}
