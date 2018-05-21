package es.uma.informatica.rsd.chat.impl;

import java.io.IOException;
import java.net.*;

import es.uma.informatica.rsd.chat.ifaces.Comunicacion;
import es.uma.informatica.rsd.chat.ifaces.Controlador;
import es.uma.informatica.rsd.chat.impl.DialogoPuerto.PuertoAlias;

// Clase a implementar IP Profesor: 192.168.164.9, puerto: 10000. IP MULTICAS: 239.194.17.132
public class ComunicacionImpl implements Comunicacion {
    private DatagramSocket serverSocket;
    private String alias;
    private Controlador controller;

	@Override
	public void crearSocket(PuertoAlias pa) {
        try {
            this.serverSocket = new DatagramSocket(pa.puerto);
            this.alias = pa.alias;
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void setControlador(Controlador c) {
        this.controller = c;
	}

	@Override
	public void runReceptor() {
	}

	@Override
	public void envia(InetSocketAddress sa, String mensaje) {
	    String formattedMessage = "!" + this.alias + "!" + mensaje;
	    byte[] bytesToSend = formattedMessage.getBytes();
        DatagramPacket messageToSend = new DatagramPacket(bytesToSend, bytesToSend.length);
        PuertoAlias pa = new PuertoAlias();
        pa.alias = this.alias;
        pa.puerto = sa.getPort();
        crearSocket(pa);
        try {
            this.serverSocket.send(messageToSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void joinGroup(InetAddress multi) {
	}

	@Override
	public void leaveGroup(InetAddress multi) {
	}

}
