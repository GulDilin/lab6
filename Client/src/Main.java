import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Main {
    private static final int PORT = 1234;

    public static void main(String [] args) throws IOException {
        byte b[] = new byte[32600];
        String str = null;
        Scanner in = new Scanner(System.in);
        System.out.print("Введите что-то: ");
        str = in.nextLine();
        SocketAddress a = new InetSocketAddress("127.0.0.1",PORT);
        DatagramSocket s = new DatagramSocket(a);
        b = str.getBytes();
        DatagramPacket o = new DatagramPacket(b, str.getBytes().length, a);
        System.out.println(o);
//        System.out.println("before send");

        s.send(o);
        System.out.println("send");

        DatagramPacket i = new DatagramPacket(b, b.length);
        s.receive(i);
        System.out.println("receive");

        System.out.println(new String(b));
    }
}
