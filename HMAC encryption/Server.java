import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Server {
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String SECRET_KEY = "YourSecretKey";

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server is running. Waiting for connections...");

            Socket clientSocket1 = serverSocket.accept();
            System.out.println("Client 1 connected.");
            BufferedReader in1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
            PrintWriter out1 = new PrintWriter(clientSocket1.getOutputStream(), true);

            Socket clientSocket2 = serverSocket.accept();
            System.out.println("Client 2 connected.");
            BufferedReader in2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));
            PrintWriter out2 = new PrintWriter(clientSocket2.getOutputStream(), true);

            Thread thread1 = new Thread(() -> handleClient(in1, out2, "Client 1"));
            Thread thread2 = new Thread(() -> handleClient(in2, out1, "Client 2"));

            thread1.start();
            thread2.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(BufferedReader in, PrintWriter out, String clientName) {
        try {
            while (true) {
                String message = in.readLine();
                if (message != null) {
                    System.out.println("Received from " + clientName + ": " + message);
                    if (verifyHmac(message)) {
                        out.println(message);
                        System.out.println("Sent to " + (clientName.equals("Client 1") ? "Client 2" : "Client 1") + ": " + message);
                    } else {
                        System.out.println("Invalid HMAC from " + clientName + ".");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean verifyHmac(String message) throws Exception {
        String[] parts = message.split(":");
        String text = parts[0];
        String receivedHmac = parts[1];

        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), HMAC_ALGORITHM);
        Mac hmac = Mac.getInstance(HMAC_ALGORITHM);
        hmac.init(secretKey);
        byte[] calculatedHmac = hmac.doFinal(text.getBytes());

        String calculatedHmacBase64 = Base64.getEncoder().encodeToString(calculatedHmac);
        return calculatedHmacBase64.equals(receivedHmac);
    }
}

