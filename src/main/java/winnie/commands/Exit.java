package winnie.commands;

import winnie.CollectionSongs;

import java.io.IOException;

public class Exit extends AbstractCommand {
    @Override
    public void execute(CollectionSongs songs) throws IOException {
        new OutputFile("").execute(songs);
    }
}
