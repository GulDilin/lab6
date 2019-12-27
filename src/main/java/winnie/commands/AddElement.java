package winnie.commands;

import netscape.javascript.JSObject;
import org.json.simple.parser.ParseException;
import winnie.CollectionSongs;
import winnie.plot.Mood;
import winnie.plot.Song;

/**
 * добавляет новый элемент в коллекцию
 */
public class AddElement implements Command {
    private String newHit;

    public AddElement(String newHit){
        this.newHit = newHit;
    }

    @Override
    public void execute(CollectionSongs songs) {
        try {
            songs.addElement(newHit);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

