package server;

import com.sun.jmx.remote.internal.ArrayQueue;
import server.plot.Mood;
import server.plot.Song;
import server.util.JsonObject;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Коллекция песен Винни
 */
public class CollectionSongs {
    private ArrayQueue<Song> songs;
    private Date date;
    private String file;
    private JsonObject jsonConverter;
    private PrintStream out;
    private DataBaseManager manager;

    public CollectionSongs(DataBaseManager manager) {
        this.manager = manager;
        songs = manager.getSongs();
        date = new Date();
        file = null;
        jsonConverter = new JsonObject();
        out = System.out;

    }

    public CollectionSongs(ArrayQueue<Song> songs) {
        this.songs = songs;
        date = new Date();
        file = null;
        file = null;
        jsonConverter = new JsonObject();
        out = System.out;
        manager = null;
    }

    public void setOut(PrintStream out) {
        System.out.println(out);
        this.out = out;
    }

    public void addElement(Song song) throws NullPointerException {
        songs.add(song);
    }

    public void addElement(String str, int ownerId) throws NullPointerException {
        if (isEmpty(str)) {
            out.println("Необходимо ввести элемент в формате json");
        } else {
            try {
                Song song = jsonConverter.getFromJson(str);
                manager.addSong(song, ownerId);
            } catch (NullPointerException ex) {
                out.println("Ошибка ввода");
            }
        }
        songs = manager.getSongs();
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

    public void save(int ownerId) {
        songs.forEach(song -> manager.addSong(song, ownerId));
    }

    public void load() {
        songs = manager.getSongs();
    }

    public void removeElement(int id, int userId, String passwordHash) {
        songs.forEach(song -> {
            if (song.getId() == id) {
                manager.deleteById(id, userId, passwordHash);
            }
        });
        songs = manager.getSongs();

    }

    public void removeLower(String id, int ownerId, String hash) {

        if (isEmpty(id)) {
            out.println("Необходимо ввсести id элемента");
        } else {
            int idToRemove = -1;
            try {
                idToRemove = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                out.println("Неверный id элемента");
                return;
            }
            int finalIdToRemove = idToRemove;
            songs.forEach(song -> {
                if (song.getId() == finalIdToRemove) {
                    songs.forEach(sg -> {
                        if (sg.compareTo(song) < 0) {
                            if (song.getId() != sg.getId()) manager.deleteSong(sg, ownerId, hash);
                        }
                    });
                    return;
                }
            });
        }
        songs = manager.getSongs();
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
            songs.add(new Song(Mood.getMoodByString(md), text, 0));
        });
    }

}
