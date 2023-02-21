package Oppg1;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public Client() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public String sendEquation(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }

    public void close() {
        socket.close();
    }

    public void run() throws IOException {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("Enter first number (or 'exit' to quit):");
            String num1 = s.nextLine();
            if (num1.equals("exit")) {
                break;
            }

            System.out.println("Enter second number:");
            String num2 = s.nextLine();
            System.out.println("Enter operator:");
            String operator = s.nextLine();
            String message = num1 + "," + num2 + "," + operator;
            String result = sendEquation(message);
            System.out.println("Result: " + result);
        }
        close();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }
}
