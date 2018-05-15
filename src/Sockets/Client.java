package Sockets;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner consoleInput = new Scanner(System.in);
            String currentInput;
            String cesarOffset;
            System.out.println("Enter the host IP: ");
            String host = consoleInput.nextLine();
            System.out.println("Enter the port: ");
            int port = consoleInput.nextInt();
            Socket sock = new Socket(host, port);
            System.out.println("Connected to " + host + " at port " + port);
            PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            System.out.println(br.readLine());
            consoleInput.nextLine();
            do {
                System.out.println("Enter the offset of the codification!: ");
                cesarOffset = consoleInput.nextLine();
                if (!cesarOffset.equals("0")) {
                    System.out.println("Enter the string to codificar!: ");
                    currentInput = consoleInput.nextLine();
                    pw.print(cesarOffset + "\r\n");
                    pw.println(currentInput);
                    System.out.println(br.readLine());
                }
            } while (!cesarOffset.equals("0"));
            if(br.readLine().equals("OK")){
                sock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
