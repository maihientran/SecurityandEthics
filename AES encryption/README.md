# Encrypted Messaging Program

This program enables secure communication between a server and multiple clients. The messages exchanged between the clients and the server are encrypted using the Advanced Encryption Standard (AES) algorithm.

## Features

- Secure communication between clients and the server using AES encryption.
- Real-time messaging: Clients can send messages to the server, and the server relays the messages to the other clients.
- Automatic encryption and decryption of messages using a shared secret key.

## Prerequisites

- Java Development Kit (JDK) installed on your machine.
- Basic understanding of Java Socket programming and encryption algorithms.

## Usage

1. Start the server by running the `Server` class.
2. Start multiple client instances by running the `Client` class.
3. Enter messages in the client terminals, and the messages will be encrypted and sent to the server.
4. The server will relay the messages to the other clients, decrypting them before displaying.

## Configuration

### Server

- The server listens on port `12345`. You can modify the port number in the `Server` class if desired.

### Client

- The client connects to the server using the hostname `localhost` and port `12345`. Adjust these values in the `Client` class if required.
- The shared secret key used for encryption is specified as `SECRET_KEY` in the `Client` class. Modify it to your preferred key.
