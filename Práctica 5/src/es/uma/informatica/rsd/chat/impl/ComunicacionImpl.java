package es.uma.informatica.rsd.chat.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;

import es.uma.informatica.rsd.chat.ifaces.Comunicacion;
import es.uma.informatica.rsd.chat.ifaces.Controlador;
import es.uma.informatica.rsd.chat.impl.DialogoPuerto.PuertoAlias;

/*  Class to implement
 *  Teacher IP:     192.168.164.9
 *  Port:           10000
 *  Multicast IP:   239.194.17.132
 */
public class ComunicacionImpl implements Comunicacion {
    private static final String IP = "192.168.164.32"; /** Change depending on the pc */
    private MulticastSocket socket;
    private String alias;
    private Controlador controller;

	@Override
	public void crearSocket(PuertoAlias pa) {
        try {
            this.socket = new MulticastSocket(new InetSocketAddress(IP, pa.puerto));
            this.alias = pa.alias;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void setControlador(Controlador c) {
        this.controller = c;
	}

	@Override
	public void runReceptor() {
        InetSocketAddress address;
        byte[] data= new byte[256];
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);

        while (true) {
            try {
                socket.receive(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fullMessage, nickName, message;
            String[] separatedMessage;
            InetSocketAddress ip;

            fullMessage = new String(datagramPacket.getData(), StandardCharsets.UTF_8);
            separatedMessage = fullMessage.split("!");
            nickName = separatedMessage[1];

            try {
                if(InetAddress.getByName(separatedMessage[0]).isMulticastAddress()) {
                    ip = new InetSocketAddress(separatedMessage[0], datagramPacket.getPort());
                    message = fullMessage.substring(nickName.length() + separatedMessage[0].length() + 2);
                } else {
                    ip = new InetSocketAddress(datagramPacket.getAddress(), datagramPacket.getPort());
                    message = fullMessage.substring((nickName.length()) + 2);
                }
                controller.mostrarMensaje(ip, nickName, message);
            } catch (UnknownHostException e) {
                System.err.println("Error receiving packet: " + e.getLocalizedMessage());
            }
        }
    }

	@Override
	public void envia(InetSocketAddress sa, String mensaje) {
        String formattedMessage;
        if (sa.getAddress().isMulticastAddress()) {
            formattedMessage = sa.getHostName() + "!" + this.alias + "!" + mensaje;
        } else {
            formattedMessage = "!" + this.alias + "!" + mensaje;
        }
	    byte[] bytesToSend = formattedMessage.getBytes();
        DatagramPacket messageToSend = new DatagramPacket(bytesToSend, bytesToSend.length, sa.getAddress(), sa.getPort());
        try {
            this.socket.send(messageToSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void joinGroup(InetAddress multi) {
        try {
            this.socket.joinGroup(new InetSocketAddress(multi.getHostAddress(), socket.getLocalPort()), NetworkInterface.getByName(IP));
            //this.socket.joinGroup(multi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void leaveGroup(InetAddress multi) {
        try {
            this.socket.leaveGroup(new InetSocketAddress(multi.getHostAddress(), socket.getLocalPort()), NetworkInterface.getByName(IP));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
