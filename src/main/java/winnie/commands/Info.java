package winnie.commands;

import winnie.CollectionSongs;

/**
 * выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
 */
public class Info implements Command {
    @Override
    public void execute(CollectionSongs songs) {
        songs.info();
    }
}
