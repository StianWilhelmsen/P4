package Oppg1;

import java.io.IOException;

public class UDP {
    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> {
            try {
                Server.main(null);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        Thread clientThread = new Thread(() ->  {
            try {
                Client.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        serverThread.start();
        clientThread.start();
    }


}
