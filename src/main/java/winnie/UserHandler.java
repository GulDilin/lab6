//package winnie;
//
//import org.json.simple.parser.ParseException;
//import server.CollectionSongs;
//import server.commands.Command;
//import server.commands.InputFile;
//import server.util.CommandParser;
//import server.util.NoSuchCommandException;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Scanner;
//import java.util.Set;
//
///**
// * класс для обработки данных введенных пользователем
// */
//class UserHandler {
//
//    private CollectionSongs songs;
//    private CommandParser parser;
//
//    UserHandler() {
//        songs = new CollectionSongs();
//        parser = new CommandParser(new String[]{"save", "import", "info", "add", "load",
//                "help", "show", "remove", "remove_lower", "start", "exit", "clear"});
//    }
//
//    void handler() throws IOException, ParseException {
//        getInput();
//    }
//
//    void init() {
//        new InputFile("").execute(songs);
//    }
//
//    void getInput() throws IOException, ParseException {
//        Command command = null;
//        String args;
//        String commandName;
//
//        init();
//
//        final String[] SET_VALUES = new String[]{"save", "import", "info", "add", "load",
//                "help", "show", "remove", "remove_lower", "start", "exit", "clear"};
//        final Set<String> COMMAND_NAMES = new HashSet<>(Arrays.asList(SET_VALUES));
//
//        while (true) {
//
//            Scanner in = new Scanner(System.in);
//            System.out.print("Введите команду: ");
//            String[] s = in.nextLine().split(" ", 2);
//
//            commandName = s[0];
//
//            try {
//                args = s[1];
//            } catch (ArrayIndexOutOfBoundsException e) {
//                args = "";
//            }
//
//            try {
//                command = parser.parse(commandName, args);
//            } catch (NoSuchCommandException e){
//                System.out.println("Нет такой команды, попробуйте ввести help");
//            }
//
//            if (command != null) {
//                command.execute(songs);
//            }
//        }
//    }
//}
