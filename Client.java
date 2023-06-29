import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Client {
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String SECRET_KEY = "YourSecretKey";

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
                            System.out.println("\nServer response: " + receivedMessage);
                            printPrompt();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            responseThread.start();

            while (true) {
                System.out.print("Enter message: ");
                String userInput = readInput();
                if (userInput != null) {
                    String messageWithHmac = addHmac(userInput);
                    out.println(messageWithHmac);
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

    private static String addHmac(String message) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), HMAC_ALGORITHM);
        Mac hmac = Mac.getInstance(HMAC_ALGORITHM);
        hmac.init(secretKey);
        byte[] calculatedHmac = hmac.doFinal(message.getBytes());

        String calculatedHmacBase64 = Base64.getEncoder().encodeToString(calculatedHmac);
        return message + ":" + calculatedHmacBase64;
    }

    private static void printPrompt() {
        System.out.print("Enter message: ");
    }
}

