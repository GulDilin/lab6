package winnie.commands;

import winnie.CollectionSongs;

import java.io.IOException;
import java.io.PrintStream;

public interface Command {
    void execute(CollectionSongs songs) throws IOException;
    void setOut(PrintStream out);
}
