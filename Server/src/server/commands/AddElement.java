package server.commands;

import server.CollectionSongs;

import java.io.PrintStream;

/**
 * добавляет новый элемент в коллекцию
 */
public class AddElement extends AbstractCommand {
    private String newHit;

    public AddElement(String newHit) {
        super();
        this.newHit = newHit;
    }

    @Override
    public void execute(CollectionSongs songs) {
        PrintStream out = this.getOut();
        songs.setOut(out);
        songs.addElement(newHit, getUserId());
    }
}

