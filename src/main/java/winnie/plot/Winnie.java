package winnie.plot;

import winnie.CollectionSongs;

import java.util.ArrayList;

public class Winnie extends Character implements Randomasable, Talkable {
    private CollectionSongs songs;


    public Winnie(String n, Mood m, int st, CollectionSongs songs) {
        super(n, m, st);
        this.songs = songs;
    }

    /**
     * Получить случайную песню из массива исполненных
     */
    void getRandSong(){
        System.out.println(songs.getText());
    }

    /**
     * Поет одну шумелку
     */
    void sing(){
        System.out.println(getName() +": "+songs.getText());
//            song = new Song(getMood());
//            System.out.println(getName() +": " + song.getText());
//            songs.add(song.getText());
    }

}


