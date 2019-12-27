package winnie.commands;

import winnie.CollectionSongs;

/**
 * Чтение из файла
 */
public class InputFile implements Command{
    private String path;
    public InputFile(String path){
        this.path = path;
    }
    @Override
    public void execute(CollectionSongs songs) {
        System.out.println(path);
        songs.inputFile(path);
    }
}
