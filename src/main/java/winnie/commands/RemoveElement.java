package winnie.commands;

import winnie.CollectionSongs;

/**
 * удаляет элемент из коллекции по его значению
 */
public class RemoveElement implements Command{
    private String id;
    public RemoveElement(String id){
        this.id = id;
    }
    @Override
    public void execute(CollectionSongs songs) {
        songs.removeElement(id);
    }
}
