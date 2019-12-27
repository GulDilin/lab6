package winnie.commands;

import winnie.CollectionSongs;

import java.io.IOException;

public interface Command {
    void execute(CollectionSongs songs) throws IOException;
}
