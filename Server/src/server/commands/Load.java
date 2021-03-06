package server.commands;

import server.CollectionSongs;

/**
 * перечитывает коллекцию из файла
 */
public class Load extends AbstractCommand {
    @Override
    public void execute(CollectionSongs songs) {
        songs.setOut(this.getOut());
        songs.load();
    }
}
