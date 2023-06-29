# SecurityandEthics
# Secure Chat Application

This project demonstrates a secure chat application implemented in Java, using encryption AES and HMAC (Hash-based Message Authentication Code) for secure communication between a server and multiple clients. The chat messages are encrypted and sent over a network, and the clients receive and display the decrypted messages.

## Features

- Server-client architecture for multiple clients to connect to the server.
- Encryption of messages to ensure confidentiality.
- HMAC for message integrity and authentication.
- Immediate printing of server responses in the client terminals.
- Formatting adjustments to print server responses and prompts on separate lines.

## Prerequisites

- Java Development Kit (JDK) installed on the system.

## Usage

1. Clone the repository to your local machine:

git clone https://github.com/maihientran/SecurityandEthics.git

2. Open a terminal and navigate to the project directory.

3. Compile the Java source files:

```javac *.java```

4. Open three separate terminals - one for the server and two for the clients.

5. In the server terminal, start the server:
```java Server```

6. In each client terminal, start the clients:
```java Client```


7. Enter messages in the client terminals and observe the encrypted messages being sent and decrypted responses being printed.

## Acknowledgements

This project was created as a demonstration of secure communication using AES encryption and HMAC in Java.
 It is meant for educational purposes and is based on the client-server model.

