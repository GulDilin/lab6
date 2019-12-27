package winnie.commands;

import winnie.CollectionSongs;

/**
 * перечитывает коллекцию из файла
 */
public class Load implements Command{
    @Override
    public void execute(CollectionSongs songs) {
        songs.load();
    }
}
