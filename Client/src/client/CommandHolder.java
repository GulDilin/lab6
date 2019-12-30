package client;

import java.io.Serializable;

public class CommandHolder implements Serializable {
    private String commandName;
    private String args;

    public CommandHolder(){
        commandName = "";
        args = "";
    }

    public CommandHolder(String s){
        commandName = s;
        args = "";
    }

    public CommandHolder(String n, String a){
        commandName = n;
        args = a;
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
