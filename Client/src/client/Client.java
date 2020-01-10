package client;

import server.DataBaseManager;

import javax.mail.internet.AddressException;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.Scanner;

public class Client {
    private static final int PORT = 1234;
    private DatagramSocket udpSocket; //сюда всё приходит
    private InetAddress serverAdress;
    private int port;
    private boolean isWorking; //флаг работы
    private String login = "";
    private String password = "";
    private int userId;


    public Client(String serverAdress, int port) throws IOException {
        isWorking = true;
        this.serverAdress = InetAddress.getByName(serverAdress);
        this.port = port;
        udpSocket = new DatagramSocket();

    }

    private void logIn() {
        boolean isLogin = false;
        System.out.print("Please autorizate in________" +
                "\n\tRegister | new user" +
                "\n\tSing In" +
                "\nType command: ");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine().toLowerCase();
        System.out.println(s);
        DataBaseManager dataBaseManager = null;
        try {
            dataBaseManager = new DataBaseManager("mydb", 5432);
        } catch (SQLException e){
            System.out.println("Cant load user list");
            System.exit(1);
        }
        while (!isLogin) {
            while (!(s.equals("register")) && (!s.equals("sing in"))) {
                if (!s.equals(" "))
                    System.out.println("No such command");
                System.out.println("Type command: ");
                s = in.nextLine().trim();
            }
            switch (s) {
                case "register":
                    s = " ";
                    System.out.println("\tType Login : ");
                    login = in.nextLine().toLowerCase().trim();

                    System.out.println("\tType EMAIL : ");
                    String email = in.nextLine().trim();
                    password = DataBaseManager.getRandomPassword(4);
                    System.out.println("Your password: " + password);
                    try{
                        EmailManager.sendEmail(email, "Registration", EmailManager.getPassMessage(login, password));
                    } catch (AddressException e){
                        System.err.println("Unable to send mail. Wrong address");
                    }
                    if (dataBaseManager.registerUser(login, email, password)) {
                        System.out.println("Successful registration");
                        isLogin = true;
                        userId = dataBaseManager.getUserID(login);
                    } else {
                        System.out.println("Registration failed. User already exist");
                    }
                    password = DataBaseManager.getMD5(password);
                    break;

                case "sing in":
                    s = " ";
                    System.out.print("\tType login: ");
                    login = in.nextLine().trim();
                    if (!dataBaseManager.getUserLogins().contains(login)) {
                        System.out.print("Wrong login\n");
                        break;
                    }
                    System.out.print("\tType password : ");
                    password = in.nextLine().trim();
                    if (dataBaseManager.checkPass(login, password)) {
                        System.out.println("Sing In success");
                        isLogin = true;
                        userId = dataBaseManager.getUserID(login);
                        password = DataBaseManager.getMD5(password);
                    } else {
                        System.out.println("Wrong password");
                    }
                    break;
            }
        }
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
            String[] s = str.split(" ", 2);

            String commandName = s[0];
            String args = "";

            try {
                args = s[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                args = "";
            }
            CommandHolder command = new CommandHolder(commandName, args);
            command.setUser(login);
            command.setUserId(userId);
            command.setPassword_hash(password);
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

    private static String getIP(){
        String ip = "127.0.0.1";
        System.out.println("Type IP: ");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public static void main(String[] args) throws IOException {
        Client sender = new Client(getIP(),PORT);

        byte[] b = new byte[32600];


        sender.testServerConnection();
        sender.logIn();
        sender.work();
    }
}
