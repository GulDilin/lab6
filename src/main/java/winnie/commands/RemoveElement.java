package winnie.commands;

import winnie.CollectionSongs;

/**
 * удаляет элемент из коллекции по его значению
 */
public class RemoveElement extends AbstractCommand {
    private String id;
    public RemoveElement(String id){
        this.id = id;
    }
    @Override
    public void execute(CollectionSongs songs) {
        songs.setOut(this.getOut());
        songs.removeElement(id);
    }
}
