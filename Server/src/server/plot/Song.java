package server.plot;

import java.util.ArrayList;
import java.util.Arrays;

public class Song implements Randomasable, Comparable {
    private static int nextId = 1;
    private int id;
    private int ownerId;
    private Mood mood;
    private String text;
    private ArrayList<String> words = new ArrayList<>(Arrays.asList("кашка",
            "лодка", "сноска", "скалка", "кнопка", "метка", "маска", "точка", "стрелка",
            "ветка", "полка", "мишка", "лейка", "лапка", "ручка", "тучка", "чашка"));

    public Song(Mood md, String text, int id) {
        mood = md;
//        id = nextId;
//        nextId++;
        this.id = id;
        this.text = text;
        this.ownerId = 0;
    }

    public Song(Mood md, String text){
        this(md, text, 0);
    }

    public Song(Mood md, String text, int ownerId, int id) {
        mood = md;
        this.id = id;

//        id = nextId;
//        nextId++;
        this.text = text;
        this.ownerId = ownerId;
    }

    public Song(String mood, String text, int id) {
        this(Mood.getMoodByString(mood), text, id);
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Mood getMood() {
        return mood;
    }

    int getCoutnWords() {
        //Начальное количество слов равно 0
        int count = 0;
        String text = this.getText();

        //Если ввели хотя бы одно слово, тогда считать, иначе конец программы
        if (this.getText().length() != 0) {
            count++;
            //Проверяем каждый символ, не пробел ли это
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == ' ') {
                    //Если пробел - увеличиваем количество слов на 1
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        return true;
//        Song other = (Song) obj;
//        return mood.equals(other.mood);
    }

    @Override
    public int hashCode() {
        return mood.hashCode();
    }

    @Override
    public String toString() {
        return "id: " + id + " owner_id:" + ownerId + " mood: " + mood + " song:" + text;
    }

    public String toCSV() {
        return getMood() + "," + getText() + "\n";
    }

    @Override
    public int compareTo(Object obj) {
        if (equals(obj)) {
            Song song = (Song) obj;
            if (this.getCoutnWords() > song.getCoutnWords()) {
                return 1;
            } else {
                if (this.getCoutnWords() == song.getCoutnWords()) {
                    return 0;
                } else return -1;
            }
        } else {
            return -1;
        }
    }
}


