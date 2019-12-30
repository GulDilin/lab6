package server.commands;

import server.CollectionSongs;

import java.io.IOException;

/**
 * Запись в файл
 */
public class OutputFile extends AbstractCommand {
    private String path;
    public OutputFile(String path){
        this.path = path;
    }
    @Override
    public void execute(CollectionSongs songs) throws IOException {
        songs.setOut(this.getOut());
        songs.outputFile(path);
    }
}
