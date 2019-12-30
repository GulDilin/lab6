package server;


import server.util.CommandParser;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;

public class Server {
    private DatagramChannel channel;
    private InetAddress host;
    private CollectionSongs songs;
    final String[] SET_VALUES;
    private CommandParser parser;
    private DataBaseManager dataBaseManager;

    public Server(int port) throws IOException, SQLException {
        this.host = InetAddress.getLocalHost();
        this.channel = DatagramChannel.open().bind(new InetSocketAddress(host, port));
        dataBaseManager = new DataBaseManager("mydb", 5433);
        songs = new CollectionSongs(dataBaseManager);
//        songs.inputFile("");
        SET_VALUES = new String[]{"save", "import", "info", "add", "load",
                "help", "show", "remove", "remove_lower", "start", "exit", "clear", "test"};
        parser = new CommandParser(SET_VALUES);

        System.out.println("Server started");
        System.out.println("IP: " + host);
        System.out.println("port: " + port);
        //this.windows.importFromFile("default.json");
//        windows = dataBaseManager.getWindows();
    }

    private void listen() throws IOException {
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            buffer.clear();
            InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

            ResponseThread responseThread = new ResponseThread(channel, clientAddress, buffer, parser, songs);
            responseThread.start();
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                Server server = new Server(Integer.parseInt(args[0]));

                server.listen();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException ex) {
                System.out.println("Can't connect database");
                ex.printStackTrace();
            }
        } else System.out.println("Need to write port");
    }
}