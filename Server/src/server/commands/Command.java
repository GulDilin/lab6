package server.commands;

import server.DataBaseManager;
import server.CollectionSongs;

import java.io.IOException;
import java.io.PrintStream;

public interface Command {
    void execute(CollectionSongs songs) throws IOException;
    void setOut(PrintStream out);
    void setUserId(int userId);
    void setDataBaseManager(DataBaseManager manager);
}
