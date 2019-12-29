package server;

import client.CommandHolder;
import winnie.CollectionSongs;
import winnie.commands.Command;
import winnie.util.CommandParser;
import winnie.util.NoSuchCommandException;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ResponseThread extends Thread {

    private final InetSocketAddress clientAddress;
    //    private WindowsArrayList windowsArrayList;
    private CollectionSongs songs;
    private ByteBuffer buffer;
    private DatagramChannel channel;
    private CommandHolder commandHolder;
    private Command command;
    private CommandParser parser;

    /**
     * @param channel       канал
     * @param clientAddress адрес клиента
     * @param buffer        буффер
     * @param parser        парсер команд
     */
    public ResponseThread(DatagramChannel channel,
                          InetSocketAddress clientAddress,
                          ByteBuffer buffer,
                          CommandParser parser,
                          CollectionSongs songs) {
        super();
//        this.windowsArrayList = windowsArrayList;
        this.channel = channel;
        this.clientAddress = clientAddress;
        this.buffer = buffer;
        this.parser = parser;
        this.songs = songs;
    }

    public void run() {
        byte[] request = buffer.array();

        try (ByteArrayInputStream bais = new ByteArrayInputStream(request);
             ObjectInputStream ois = new ObjectInputStream(bais);

             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);

             ByteArrayOutputStream bao = new ByteArrayOutputStream();
             PrintStream printStream = new PrintStream(bao)) {

            commandHolder = (CommandHolder) ois.readObject();
            String response = "";

            System.out.println("Client command: " + commandHolder.toString());

            printStream.println();

            try {
                command = parser.parse(commandHolder.getCommandName(), commandHolder.getArgs());
                command.setOut(printStream);
                if (command != null) {
                    command.execute(songs);
                }
            } catch (NoSuchCommandException ex) {
                printStream.println(ex.getMessage());
            }

//            if (commandHolder.getCommandName().equals("test")) {
//                printStream.println("Connected");
//            } else {
//                printStream.println(commandHolder.toString());
//            }
            printStream.println();

            response = bao.toString().trim();
//            System.out.println(response);
//            command.toString();
            oos.writeObject(response);
            oos.flush();

            buffer.clear();
            buffer.put(baos.toByteArray());
            buffer.flip();

            channel.send(buffer, clientAddress);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
