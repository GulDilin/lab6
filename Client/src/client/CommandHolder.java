package client;

import java.io.Serializable;

public class CommandHolder implements Serializable {
    private String commandName;
    private String args;
    private String user;
    private String password_hash;
    private int userId;

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

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public String getUser() {
        return user;
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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return getCommandName() + " " + getArgs();
    }
}
