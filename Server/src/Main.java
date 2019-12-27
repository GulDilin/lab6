import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class Main {
    private static final int PORT = 1234;

    public static void main(String [] args) throws IOException {
        byte b[] = new byte[32600];
        SocketAddress a = new InetSocketAddress(PORT);
        DatagramSocket s = new DatagramSocket(a);

        while (true){
            DatagramPacket i = new DatagramPacket(b, b.length);
            DatagramPacket o = new DatagramPacket(b, b.length, i.getAddress(), i.getPort());

            s.receive(i);
            new ServerThread(o, s).run();
        }



        System.out.println(i);
        System.out.println("before receive");

        System.out.println("receive");

    }
}

class ServerThread implements Runnable{
    private DatagramSocket s;
    private DatagramPacket p;

    public ServerThread(DatagramPacket DP, DatagramSocket DS){
        this.s = DS;
        this.p = DP;
    }

    @Override
    public void run() {
        try {

            s.send(p);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
