package es.uma.informatica.rsd.chat.impl;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

import es.uma.informatica.rsd.chat.ifaces.Comunicacion;
import es.uma.informatica.rsd.chat.ifaces.Controlador;
import es.uma.informatica.rsd.chat.impl.DialogoPuerto.PuertoAlias;

/** Class to implement
 *  Teacher IP:     192.168.164.9
 *  Port:           10000
 *  Multicast IP:   239.194.17.132
 */
public class ComunicacionImpl implements Comunicacion {
    private static final String IP = "192.168.164.152"; /**Change depending the PC*/
    private MulticastSocket socket;
    private String alias;
    private Controlador controller;

	@Override
	public void crearSocket(PuertoAlias pa) {
        try {
            this.socket = new MulticastSocket(new InetSocketAddress(IP, pa.puerto));                                    // Creamos del Socket usando la IP y el puerto que nos pasan
            this.alias = pa.alias;                                                                                      // Asignamos el alias (username) y lo guardamos para después
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void setControlador(Controlador c) {
        this.controller = c;                                                                                            // Asignamos el controlador
	}

    @Override
    public void runReceptor() {
        while(true) {
            byte[] data = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length);                                      // Creamos el datagrama udp usando un buffer de bytes de longitud 256

            try {
                socket.receive(datagramPacket);                                                                         // Guardamos en un datagrama el mensaje recibido
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fullMessage, nickName, message;
            String[] separatedMessage;
            InetSocketAddress ip;

            fullMessage = new String(datagramPacket.getData(), StandardCharsets.UTF_8);                                 // Pasamos el mensaje codificado en UTF-8 a java.lang.String
            separatedMessage = fullMessage.split("!");                                                           // Parseamos el mensaje recibido usando como separador "!"
            nickName = separatedMessage[1];                                                                             // Cogemos el nickname y lo guardamos.

            try {
                if(InetAddress.getByName(separatedMessage[0]).isMulticastAddress()) {                                   // Si es multicast:
                    ip = new InetSocketAddress(separatedMessage[0], datagramPacket.getPort());                          // Asignamos la IP a la que nos ha llegado desde el mensaje (1er elemento)
                    message = fullMessage.substring(nickName.length() + separatedMessage[0].length() + 2);              // Asignamos el mensaje calculando los desplazamientos
                } else {                                                                                                // Si no es multicast:
                    ip = new InetSocketAddress(datagramPacket.getAddress(), datagramPacket.getPort());                  // Asignamos la IP como la IP del equipo del que viene el datagrama
                    message = fullMessage.substring(nickName.length() + 2);                                             // Asignamos el mensaje calculando los desplazamientos
                }
                if (!nickName.equals(alias)) {                                                                          // Si el nickname es distinto del alias, es decir, es si no igual a si mismo
                    controller.mostrarMensaje(ip, nickName, message);                                                   // Mostramos el mensaje por la aplicacion
                }
            } catch (UnknownHostException e) {
                System.err.println("Error receiving packet: " + e.getLocalizedMessage());
            }
        }
    }

	@Override
	public void envia(InetSocketAddress sa, String mensaje) {
        String formattedMessage;
        if (sa.getAddress().isMulticastAddress()) {                                                                     // Si estamos en multicast:
            formattedMessage = sa.getHostName() + "!" + this.alias + "!" + mensaje;                                     // Formateamos el mensaje con el HostName
        } else {                                                                                                        // Si no estamos en multicast:
            formattedMessage = "!" + this.alias + "!" + mensaje;                                                        // Formateamos el mensaje sin IP al principio
        }
        byte[] bytesToSend = formattedMessage.getBytes();                                                               // Guardamos el mensaje en un array de Bytes
        DatagramPacket messageToSend = new DatagramPacket(bytesToSend,
                bytesToSend.length, sa.getAddress(), sa.getPort());                                                     // Guardamos el array en un Datagrama para poder mandarlo
        try {
            this.socket.send(messageToSend);                                                                            // Enviamos el mensaje
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void joinGroup(InetAddress multi) {
        try {
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getByName(IP));           // Creamos la interfaz de red y forzamos a que use la interfaz asociada a la IP
            socket.joinGroup(new InetSocketAddress(multi, socket.getLocalPort()), networkInterface);                    // Nos unimos al grupo con dicha interfaz
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void leaveGroup(InetAddress multi) {
        try {
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getByName(IP));           // Creamos la interfaz de red y forzamos a que use la interfaz asociada a la IP
            socket.leaveGroup(new InetSocketAddress(multi, socket.getLocalPort()), networkInterface);                   // Dejamos el grupo con dicha interfaz
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}