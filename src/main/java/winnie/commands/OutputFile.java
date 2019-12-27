package winnie.commands;

import winnie.CollectionSongs;

import java.io.IOException;

/**
 * Запись в файл
 */
public class OutputFile implements Command{
    private String path;
    public OutputFile(String path){
        this.path = path;
    }
    @Override
    public void execute(CollectionSongs songs) throws IOException {
        songs.outputFile(path);
    }
}
