package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final int PORT = 1234;
    private DatagramSocket udpSocket;
    private InetAddress serverAdress;
    private int port;
    private String defaultFilename;
    private boolean isWorking;
//    private HashSet<String> user_logins = new HashSet<>();
//    private DataBaseManager dataBaseManager;
//    private Tunnel tunnel;

    public Client(String serverAdress, int port) throws IOException {
        isWorking = true;
//        dataBaseManager = new DataBaseManager();
        this.serverAdress = InetAddress.getByName(serverAdress);
        this.port = port;
//        tunnel = new Tunnel("helios.se.ifmo.ru",
//                "s264449",
//                "cfv571",
//                2222,
//                "localhost",
//                port,
//                port);
////        tunnel.connect();
        udpSocket = new DatagramSocket();

    }

    private DatagramPacket createDatagramPacket(CommandHolder command) throws IOException {
        byte[] sending;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {

            oos.writeObject(command);
            oos.flush();
            sending = outputStream.toByteArray();

        } catch (NotSerializableException e) {
            throw new NotSerializableException("Datagram Serializable Creating Error");
        }
        return new DatagramPacket(sending, sending.length, serverAdress, port);
    }

    public void testServerConnection() throws IOException {
        System.out.print("Try to connect server ");
        DatagramPacket check = createDatagramPacket(new CommandHolder("test"));

        byte[] buf = new byte[1024];
        DatagramPacket testResponse = new DatagramPacket(buf, buf.length);

        boolean connected = false;
        udpSocket.setSoTimeout(1428);
        String connectString = "";
        for (int i = 0; i < 7; i++) {
            udpSocket.send(check);
            try {
                udpSocket.receive(testResponse);
                System.out.println("Response: " + connectString);
            } catch (SocketTimeoutException e) {
                System.out.print(".");
                continue;
            }
            connectString = getServerResponse(check);

            if (connectString.equals("Connected")) {
                connected = true;
                break;
            }
        }

        if (connected) {
            System.out.println("Conection complete");
        } else {
            System.err.println("Can't connect server");
            System.exit(1);
        }
    }

    private String getServerResponse(DatagramPacket senderDPacket) throws IOException {
        String response = "";
        if (senderDPacket != null) {
            udpSocket.send(senderDPacket);

            byte[] respBuf = new byte[4096];
            DatagramPacket responsePacket = new DatagramPacket(respBuf, respBuf.length);
            try {
                this.udpSocket.receive(responsePacket);

                try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(respBuf))) {
                    response = (String) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (SocketTimeoutException e) {
                System.err.println("Timeout end");
                System.err.println("Lost connection");
                testServerConnection();
            }
        }
        return response;
    }

    public void work(){
        boolean isWork = true;
        String str = "";
        Scanner in = new Scanner(System.in);
        while (isWork) {
            System.out.print("Введите команду: ");
            str = in.nextLine();
            CommandHolder command = new CommandHolder(str);
            try {
                DatagramPacket o = createDatagramPacket(command);
                String response = getServerResponse(o);
                System.out.println(response);
            } catch (SocketTimeoutException e){
                System.out.println("Timeout error. Попробуйте позже");
                isWork = false;
            } catch (IOException ex) {
                System.out.println("Datagram creating error");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Client sender = new Client("192.168.31.182",PORT);

        byte[] b = new byte[32600];


        sender.testServerConnection();

        sender.work();
//        while (isWork) {
//            System.out.print("Введите команду: ");
//            str = in.nextLine();
//
//            SocketAddress a = new InetSocketAddress("127.0.0.1", PORT);
//            DatagramSocket s = new DatagramSocket(a);
//
//            b = str.getBytes();
//            CommandHolder command = new CommandHolder(str);
//            DatagramPacket o = ;
//            System.out.println(o);
////        System.out.println("before send");
//
//            s.send(o);
//            System.out.println("send");
//
//            DatagramPacket i = new DatagramPacket(b, b.length);
//            s.receive(i);
//            System.out.println("receive");
//
//            String response = new String(b);
//            System.out.println(response);
//        }
    }
}
