package winnie;

import org.json.simple.parser.ParseException;
import winnie.plot.Mood;
import winnie.plot.Song;
import winnie.util.JsonObject;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Коллекция песен Винни
 */
public class CollectionSongs {
    private ArrayDeque<Song> songs;
    private Date date;
    private String file;
    private JsonObject jsOb;
    private PrintStream out;

    public CollectionSongs() {
        songs = new ArrayDeque<>();
        date = new Date();
        file = null;
        jsOb = new JsonObject();
        out = System.out;
    }

    public void setOut(PrintStream out) {
        System.out.println(out);
        this.out = out;
    }

    public void addElement(String str) throws ParseException, NullPointerException {
        if (isEmpty(str)) {
            out.println("Необходимо ввести элемент в формате json");
        } else {
//            Song song1 = jsOb.getJson(str);
//            if(song1.equals(null)){
//                System.out.println("Ошибка ввода");
//            }else {
//                songs.add(song1);
//            }
            try {
                songs.add(jsOb.getJson(str));
            } catch (NullPointerException ex) {
                out.println("Ошибка ввода");
            }
        }
    }


    public void showCollection() {
        songs.forEach(song -> out.println(song.toString()));
    }

    public void clearCollectrion() {
        songs.clear();
    }

    public void info() {
        out.println("дата инициализации: " + date + "; тип: Song; количество элементов: " + songs.size());
    }

    public void inputFile(String filePath) {

        if (isEmpty(filePath)) {
            filePath = "resources/defaultInput.csv";
        }
        file = filePath;
        // входной поток, получается откуда-то извне
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             BufferedInputStream is = new BufferedInputStream(fis)) {
            // буфер для чтения, разумного объема
            byte[] buffer = new byte[32768];
            // Выходной поток, ByteArrayOutputStream используется только для примера
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // цикл чтения
            while (true) {
                // читаем данные в буфер
                int readBytesCount = is.read(buffer);
                if (readBytesCount == -1) {
                    // данные закончились
                    break;
                }
                if (readBytesCount > 0) {
                    // данные были считаны - есть, что записать
                    baos.write(buffer, 0, readBytesCount);
                }
            }
            baos.flush();
//            baos.close();
            byte[] data = baos.toByteArray();
            String string = new String(data);
            getCSV(string);
            this.showCollection();
        } catch (FileNotFoundException ex) {
            out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void outputFile(String path) throws IOException {
        if (path.equals("")) {
            path = "resources/defaultOutput.csv";
        }
        // открываем поток ввода в файл
        try (FileOutputStream outputStream = new FileOutputStream(path)) {

            // записываем данные в файл, но
            // пока еще данные не попадут в файл,
            // а просто будут в памяти
            outputStream.write(this.toCSV().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        inputFile(file);
    }

    public void removeElement(String id) {
        if (isEmpty(id)) {
            out.println("Необходимо ввести id элемента");
        } else {
            songs.forEach(song -> {
                if (song.getId() == Integer.parseInt(id)) {
                    songs.remove(song);
                }
            });
        }
    }

    public void removeLower(String s) {

        if (isEmpty(s)) {
            out.println("Необходимо ввсести элемент");
        } else {
            songs.forEach(song -> {
                if (song.getId() == Integer.parseInt(s)) {
                    songs.forEach(sg -> {
                        if (sg.compareTo(song) < 0) {
                            if (song.getId() != sg.getId()) songs.remove(sg);
                        }
                    });
                    return;
                }
            });
        }
    }

    private String toCSV() {
        StringBuilder builder = new StringBuilder();
        songs.forEach(song -> builder.append(song.toCSV()));
        return builder.toString();
    }


    private boolean isEmpty(String text) {
        return text.equals("");
    }

    public String getText() {

        Random random = new Random();
        int index = random.nextInt(songs.size());
        String text = null;
        for (Song song : songs) {
            int i = 0;
            if (index == i) {
                text = song.getText();
            }
            i++;
        }

        return text;
    }

    private void getCSV(String str) {
        songs.clear();
        Arrays.asList(str.split("\n")).forEach(s -> {
            String[] splited = s.split(",");
            String md = splited[0];
            String text = splited[1];
            songs.add(new Song(Mood.getMoodByString(md), text));
        });
    }

}
