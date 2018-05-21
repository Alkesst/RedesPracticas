package Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.SQLOutput;

public class Server{
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Port opened");
            for(;;){
                    Socket clientSocket = serverSocket.accept();
                    PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    pw.println("Binvenido al servicio de cifrado");
                    String inetClientAddress = clientSocket.getInetAddress().toString();
                    System.out.println("Client " + inetClientAddress + " connected...");
                    int cesarOffset;
                    String receivedMessage = "";
                    String messageToSend = "";
                    try {
                    clientSocket.setSoTimeout(40000);
                    do {
                        cesarOffset = Integer.parseInt(br.readLine());
                        if (cesarOffset != 0) {
                            receivedMessage = br.readLine();
                            messageToSend = codifyString(receivedMessage, cesarOffset);
                            pw.println(messageToSend);
                            System.out.println("Client " + inetClientAddress + " sends offset: " + cesarOffset);
                            System.out.println("Client " + inetClientAddress + " sends message: " + receivedMessage);
                            System.out.println("Message sent to client: " + messageToSend);
                        }
                    } while (cesarOffset != 0);
                    pw.println("OK");
                    clientSocket.close();
                } catch (SocketTimeoutException e){
                    System.err.println("Connection timeout with " + clientSocket.getInetAddress());
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String codifyString(String receivedMessage, int offset){
        StringBuilder codified =  new StringBuilder();
        char currentChar;
        int numLetras = 'Z' - 'A' + 1;
        offset %= numLetras;
        for(int i = 0; i < receivedMessage.length(); i++){
            currentChar = receivedMessage.charAt(i);
            if(Character.isLetter(currentChar)){
                if((Character.isLowerCase(currentChar) && currentChar + offset > 'z')
                        || (Character.isUpperCase(currentChar) && currentChar + offset > 'Z')){
                    currentChar -= numLetras;
                }
                currentChar += offset;
            }
            codified.append(currentChar);
        }
        return codified.toString();
    }
}
