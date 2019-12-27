package winnie.commands;

import winnie.CollectionSongs;

/**
 * выводит в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class Show implements Command{
    @Override
    public void execute(CollectionSongs songs) {
        songs.showCollection();
    }
}
