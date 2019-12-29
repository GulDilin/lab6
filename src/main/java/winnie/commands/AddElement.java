package winnie.commands;

import netscape.javascript.JSObject;
import org.json.simple.parser.ParseException;
import winnie.CollectionSongs;
import winnie.plot.Mood;
import winnie.plot.Song;

import java.io.PrintStream;

/**
 * добавляет новый элемент в коллекцию
 */
public class AddElement extends AbstractCommand {
    private String newHit;

    public AddElement(String newHit){
        super();
        this.newHit = newHit;
    }

    @Override
    public void execute(CollectionSongs songs) {
        PrintStream out = this.getOut();
        songs.setOut(out);
        try {
            songs.addElement(newHit);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

