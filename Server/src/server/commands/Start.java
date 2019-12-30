package server.commands;

import server.CollectionSongs;
import server.plot.Mood;
import server.plot.Piglet;
import server.plot.Walk;
import server.plot.Winnie;

import java.io.IOException;

public class Start extends AbstractCommand {
    @Override
    public void execute(CollectionSongs songs) throws IOException {
        Winnie person1;
        Piglet person2;
        person1 = new Winnie("Винни", Mood.GOOD, 150, songs);
        person2 = new Piglet("Пятачок", Mood.SAD, 200);
        Walk walk = new Walk();
        walk.setOut(this.getOut());
        walk.talk(person1, person2);
    }
}
