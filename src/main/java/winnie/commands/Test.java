package winnie.commands;

import winnie.CollectionSongs;

import java.io.IOException;

public class Test extends AbstractCommand {
    @Override
    public void execute(CollectionSongs songs) throws IOException {
        getOut().println("Connected");
    }
}
