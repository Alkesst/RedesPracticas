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
            ServerSocket serverSocket = new ServerSocket(12345);                                                  // Creamos el Servior con el puerto 12345
            System.out.println("Port opened");
            for(;;){//Bucle infinito
                    Socket clientSocket = serverSocket.accept();                                                        // Aceptar la conexion
                    PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);                   // Creamos el PrintWriter para poder enviar
                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));       // Creamos el BufferedReader para leer (recibir)
                    pw.println("Binvenido al servicio de cifrado");                                                     // Enviamos al cliente el mensaje de bienvenida
                    String inetClientAddress = clientSocket.getInetAddress().toString();                                // Guardamos la IP del equipo que se ha conectado
                    System.out.println("Client " + inetClientAddress + " connected...");                                // Imprimimos la IP del cliente conectado
                    int cesarOffset;
                    String receivedMessage = "";
                    String messageToSend = "";
                    try {
                    clientSocket.setSoTimeout(40000);                                                                   // En caso de que no enviar nada, cerrar la conexion con el Timeout
                    do {
                        cesarOffset = Integer.parseInt(br.readLine());                                                  // Obtenemos la cantidad para hacer el offset
                        if (cesarOffset != 0) {                                                                         // Si es distinto de 0:
                            receivedMessage = br.readLine();                                                            // Leemos el mensaje enviado
                            messageToSend = codifyString(receivedMessage, cesarOffset);                                 // Codificamos el mensaje a enviar
                            pw.println(messageToSend);                                                                  // Enviamos el mensaje codificado
                            System.out.println("Client " + inetClientAddress + " sends offset: " + cesarOffset);        // Imprimimos el offset
                            System.out.println("Client " + inetClientAddress + " sends message: " + receivedMessage);   // Imprimimos el mensaje sin codificar
                            System.out.println("Message sent to client: " + messageToSend);                             // Imprimimos el mensaje codificado
                        }
                    } while (cesarOffset != 0);                                                                         // Bucle mientras el offset sea distinto de 0
                    pw.println("OK");                                                                                   // Le enviamos un OK al cliente para cerrar la conexion
                    clientSocket.close();                                                                               // Cerramos la conexion
                } catch (SocketTimeoutException e){                                                                     // En caso de que haya saltado el timeout
                    System.err.println("Connection timeout with " + clientSocket.getInetAddress());                     // Imprimimos por pantalla un mensaje de error
                    clientSocket.close();                                                                               // Cerramos la conexion
                }
            }// fin for
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param receivedMessage
     * @param offset
     */
    private static String codifyString(String receivedMessage, int offset){                                             // Funcion para codificar el mensaje
        StringBuilder codified =  new StringBuilder();
        char currentChar;
        int numLetras = 'Z' - 'A' + 1;
        offset %= numLetras;                                                                                            // Si el offset se pasa de 26, lo recalculamos con el módulo
        for(int i = 0; i < receivedMessage.length(); i++){
            currentChar = receivedMessage.charAt(i);                                                                    // Cogemos el char para hacer la codificación
            if(Character.isLetter(currentChar)){                                                                        // Si es una letra:
                if((Character.isLowerCase(currentChar) && currentChar + offset > 'z')                                   // Si la letra está antes de la z o Z
                        || (Character.isUpperCase(currentChar) && currentChar + offset > 'Z')){
                    currentChar -= numLetras;                                                                           // Recalculamos el char actual
                }
                currentChar += offset;                                                                                  // Aplicamos la codificación
            }
            codified.append(currentChar);                                                                               // Añadimos el char a la cadena
        }
        return codified.toString();
    }
}
