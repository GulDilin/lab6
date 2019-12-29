package winnie.commands;

import org.omg.PortableInterceptor.INACTIVE;
import winnie.CollectionSongs;

/**
 *  удаляет из коллекции все элементы, меньшие, чем заданный
 */
public class RemoveLower extends AbstractCommand {
    private String number;
    public RemoveLower(String s) {
        number = s;
    }
    @Override
    public void execute(CollectionSongs songs) {
        songs.setOut(this.getOut());
        songs.removeLower(number);

    }
}
