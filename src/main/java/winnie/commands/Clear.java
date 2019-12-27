package winnie.commands;

import winnie.CollectionSongs;

/**
 * Очитка коллекции
 */
public class Clear implements Command {
    @Override
    public void execute(CollectionSongs songs) {
        songs.clearCollectrion();
    }
}
