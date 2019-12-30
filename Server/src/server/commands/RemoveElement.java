package server.commands;

import server.CollectionSongs;

/**
 * удаляет элемент из коллекции по его значению
 */
public class RemoveElement extends AbstractCommand {
    private int id;
    public RemoveElement(String arg){
        try {
            id = Integer.parseInt(arg);
        } catch (NumberFormatException e){
            id = 0;
        }
    }

    @Override
    public void execute(CollectionSongs songs) {
        songs.setOut(this.getOut());
        songs.removeElement(id, getUserId(), getPasswordHash());
    }
}
