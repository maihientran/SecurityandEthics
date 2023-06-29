import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

public class Server {
    private static final String SECRET_KEY = "YourSecretKey123";  // 16 bytes key

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started. Waiting for clients...");

            Socket client1Socket = serverSocket.accept();
            System.out.println("Client 1 connected: " + client1Socket.getInetAddress());
            BufferedReader client1In = new BufferedReader(new InputStreamReader(client1Socket.getInputStream()));
            PrintWriter client1Out = new PrintWriter(client1Socket.getOutputStream(), true);

            Socket client2Socket = serverSocket.accept();
            System.out.println("Client 2 connected: " + client2Socket.getInetAddress());
            BufferedReader client2In = new BufferedReader(new InputStreamReader(client2Socket.getInputStream()));
            PrintWriter client2Out = new PrintWriter(client2Socket.getOutputStream(), true);

            Thread client1Thread = new Thread(() -> {
                try {
                    while (true) {
                        String encryptedMessage = client1In.readLine();
                        if (encryptedMessage != null) {
                            String decryptedMessage = decryptMessage(encryptedMessage);
                            System.out.println("\nClient 1 encrypted message: " + encryptedMessage);
                            System.out.println("Client 1 decrypted message: " + decryptedMessage);
                            client2Out.println(decryptedMessage);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread client2Thread = new Thread(() -> {
                try {
                    while (true) {
                        String encryptedMessage = client2In.readLine();
                        if (encryptedMessage != null) {
                            String decryptedMessage = decryptMessage(encryptedMessage);
                            System.out.println("\nClient 2 encrypted message: " + encryptedMessage);
                            System.out.println("Client 2 message: " + decryptedMessage);
                            client1Out.println(decryptedMessage);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            client1Thread.start();
            client2Thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String decryptMessage(String encryptedMessage) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] decodedMessage = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedMessageBytes = cipher.doFinal(decodedMessage);

        return new String(decryptedMessageBytes);
    }
}
