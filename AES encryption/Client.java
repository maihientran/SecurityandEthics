import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

public class Client {
    private static final String SECRET_KEY = "YourSecretKey123";  // 16 bytes key

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Thread responseThread = new Thread(() -> {
                try {
                    while (true) {
                        String response = in.readLine();
                        if (response != null) {
                            String[] parts = response.split(":");
                            String receivedMessage = parts[0];
                            if (!receivedMessage.isEmpty()) {
                                System.out.println("\nThe other: " + receivedMessage);
                                printPrompt();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            responseThread.start();

            System.out.print("You: ");
            while (true) {
                String userInput = readInput();
                if (userInput != null) {
                    String encryptedMessage = encryptMessage(userInput);
                    out.println(encryptedMessage);
                    System.out.print("You: ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readInput() {
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            return stdIn.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String encryptMessage(String message) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] encryptedMessageBytes = cipher.doFinal(message.getBytes());
        String encryptedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);

        return encryptedMessage;
    }

    private static void printPrompt() {
        System.out.print("You: ");
    }
}
