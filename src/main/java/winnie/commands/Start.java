package winnie.commands;

import winnie.CollectionSongs;
import winnie.plot.Mood;
import winnie.plot.Piglet;
import winnie.plot.Walk;
import winnie.plot.Winnie;

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
