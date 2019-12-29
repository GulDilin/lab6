package client;

import java.io.Serializable;

public class CommandHolder implements Serializable {
    private String commandName;
    private String args;

    CommandHolder(){
        commandName = "";
        args = "";
    }

    CommandHolder(String s){
        commandName = s;
        args = "";
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return getCommandName() + " " + getArgs();
    }
}
