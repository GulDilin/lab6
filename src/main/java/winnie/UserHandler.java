package winnie;

import org.json.simple.parser.ParseException;
import winnie.commands.*;
import winnie.plot.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * класс для обработки данных введенных пользователем
 */
class UserHandler {
    private Winnie person1;
    private Piglet person2;
    private CollectionSongs songs;
    UserHandler(){
        songs = new CollectionSongs();
        person1 = new Winnie("Винни", Mood.GOOD, 150, songs);
        person2 = new Piglet("Пятачок", Mood.SAD, 200);
    }
    void handler() throws IOException, ParseException {
        getInput();
    }
    void getInput() throws IOException, ParseException {
        Command command = null;
        String args;
        command = new InputFile("");
        command.execute(songs);
        final String[] SET_VALUES = new String[]{"save","import", "info", "add", "load",
                "help", "show", "remove", "remove_lower", "start", "exit", "clear"};
        final Set<String> COMMAND_NAMES = new HashSet<>(Arrays.asList(SET_VALUES));
        while (true) {

            Scanner in = new Scanner(System.in);
            System.out.print("Введите команду: ");
            String[] s = in.nextLine().split(" ", 2);
            while (!COMMAND_NAMES.contains(s[0])) {
                System.out.println("Нет такой команды, попробуйте ввести help");
                s = in.nextLine().trim().split(" ", 2);
            }
            try {
                args = s[1];
            }catch (ArrayIndexOutOfBoundsException e){
                args = "";
            }

            switch (s[0]) {
                case "remove":
                    command = new RemoveElement(args);
                    break;

                case "start":
                    Walk walk = new Walk();
                    walk.talk(person1, person2);
                    in.close();
                    return;

                case "save":
                    command = new OutputFile(args);
                    break;

                case "import":
                    command = new InputFile(args);
                    break;

                case "help":
                    for (String st: SET_VALUES) {
                        System.out.print(st + ", ");
                    }
                    command = null;
                    break;

                case "remove_lower":
                    command = new RemoveElement(args);
                    break;

                case "load":
                    command = new Load();
                    break;

                case "info":
                    command = new Info();
                    break;

                case "show":
                    command = new Show();
                    break;

                case "add":
                    command = new AddElement(args);
                    break;

                case "exit":
                    command = new OutputFile("");
                    command.execute(songs);
                    in.close();
                    return;
            }
            if (command != null){
                command.execute(songs);
            }
        }
    }
}
