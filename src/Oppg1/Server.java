package Oppg1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server extends Thread {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public Server() throws SocketException {
        socket = new DatagramSocket(4445);
    }

    public void run() {
        running = true;

        while (running) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                System.out.println("Packet received in server");
            } catch (IOException e) {
                e.printStackTrace();
            }
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String received = new String(packet.getData(), 0, packet.getLength());

            if (received.equals("end")) {
                running = false;
                continue;
            }

            // Parse the equation sent by the client
            String[] parts = received.split(",");
            String num1 = parts[0];
            String num2 = parts[1];
            String operator = parts[2];

            // Calculate the result
            double result = 0;
            try {
                double n1 = Double.parseDouble(num1);
                double n2 = Double.parseDouble(num2);

                if (operator.equals("+")) {
                    result = n1 + n2;
                } else if (operator.equals("-")) {
                    result = n1 - n2;
                } else if (operator.equals("*")) {
                    result = n1 * n2;
                } else if (operator.equals("/")) {
                    result = n1 / n2;
                } else {
                    throw new Exception("Ugyldig operator");
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = Double.NaN; // Set result to NaN in case of error
            }

            // Send the result back to the client
            String resultStr = String.valueOf(result);
            buf = resultStr.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, address, port);
            try {
                socket.send(sendPacket);
                System.out.println("Packet sent from Oppg1.Server");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new Server().start();
    }
}
