package winnie.commands;

import winnie.CollectionSongs;

/**
 * Чтение из файла
 */
public class InputFile extends AbstractCommand {
    private String path;
    public InputFile(String path){
        this.path = path;
    }
    @Override
    public void execute(CollectionSongs songs) {
        songs.setOut(this.getOut());
        System.out.println(path);
        songs.inputFile(path);
    }
}
