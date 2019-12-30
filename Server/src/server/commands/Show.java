package server.commands;

import server.CollectionSongs;

/**
 * выводит в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class Show extends AbstractCommand {

    public Show() {
        super();
    }

    @Override
    public void execute(CollectionSongs songs) {
        songs.setOut(this.getOut());
        songs.showCollection();
    }
}
