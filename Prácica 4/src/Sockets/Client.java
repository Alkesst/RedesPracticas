package Sockets;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner consoleInput = new Scanner(System.in);                                                              // Creamos un escaner para leer la IP y el puerto
            String currentInput;
            String cesarOffset;
            System.out.println("Enter the host IP: ");
            String host = consoleInput.nextLine();                                                                      // Leemos la IP por terminal
            System.out.println("Enter the port: ");
            int port = consoleInput.nextInt();                                                                          // Leemos el puerto
            Socket sock = new Socket(host, port);                                                                       // Creamos el cliente
            System.out.println("Connected to " + host + " at port " + port);                                            // Imprimimos por pantalla al servidor y puerto que nos hemos conectado
            PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);                                   // Creamos el printWriter para enviar
            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));                       // Creamos el bufferReader para recibir
            System.out.println(br.readLine());                                                                          // Leemos la primera línea (mensaje) enviado por el servidor (El de bienvenida)
            consoleInput.nextLine();
            do {
                System.out.println("Enter the offset of the codification!: ");
                cesarOffset = consoleInput.nextLine();                                                                  // Introduce el offset
                if (!cesarOffset.equals("0")) {                                                                         // Si el offset es distinto de 0:
                    System.out.println("Enter the string to codify!: ");
                    currentInput = consoleInput.nextLine();                                                             // Leemos el mensaje a codificar
                    pw.print(cesarOffset + "\r\n");                                                                     // Le enviamos el offser al servidor
                    pw.println(currentInput);                                                                           // Le enviamos el mensaje al servidor
                    System.out.println("Waiting server response...");
                    System.out.println(br.readLine());                                                                  // Recibimos la respuesta del servidor
                }
            } while (!cesarOffset.equals("0"));                                                                         // Seguir el bucle mientras el offset sea distinto de 0
            pw.println("0");                                                                                            // Enviar un "0" para terminar la conexion
            System.out.println("Waiting server response...");
            System.out.println(br.readLine());                                                                          // Recibe la respuesta para cerrar la conexion (OK)
            sock.close();                                                                                               // Conexion cerrada
        } catch (IOException e) {
            System.err.println("Server has closed.");                                                                   // Si el servidor se ha cerrado por timeout u otros motivos, el cliente
            System.exit(-1);                                                                                     // se cerrará tras intentar enviar algún mensaje.
        }
    }
}
