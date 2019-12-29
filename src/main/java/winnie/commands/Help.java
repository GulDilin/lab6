package winnie.commands;

import winnie.CollectionSongs;

import java.io.IOException;
import java.util.Set;

public class Help extends AbstractCommand {
    private Set<String> COMMAND_NAMES;

    public Help(Set<String> COMMAND_NAMES){
        this.COMMAND_NAMES = COMMAND_NAMES;
    }

    @Override
    public void execute(CollectionSongs songs) throws IOException {
        for (String st: COMMAND_NAMES) {
            getOut().print(st + ", ");
        }

    }
}
