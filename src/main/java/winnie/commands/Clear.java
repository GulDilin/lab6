package winnie.commands;

import winnie.CollectionSongs;

/**
 * Очитка коллекции
 */
public class Clear extends AbstractCommand  {
    @Override
    public void execute(CollectionSongs songs) {
        songs.setOut(this.getOut());
        songs.clearCollectrion();
    }
}
