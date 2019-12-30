package server.commands;

import server.DataBaseManager;

import java.io.PrintStream;

public abstract class AbstractCommand implements Command {
    private PrintStream out;
    private DataBaseManager manager;
    private int userId;
    private String passwordHash;

    AbstractCommand(){
        this.out = System.out;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public PrintStream getOut() {
        return out;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public DataBaseManager getDataBaseManager() {
        return manager;
    }

    public void setDataBaseManager(DataBaseManager manager) {
        this.manager = manager;
    }
}
