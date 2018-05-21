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
        while (true) {
            byte[] data = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
            try {
                serverSocket.receive(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fullMessage = new String(datagramPacket.getData());
            String[] messagePacket = fullMessage.split("!");
            InetSocketAddress address = new InetSocketAddress(datagramPacket.getAddress(), datagramPacket.getPort());
            String nickName = messagePacket[1];
            String message = fullMessage.substring(nickName.length()+2);

            controller.mostrarMensaje(address, nickName, message);

        }
    }

	@Override
	public void envia(InetSocketAddress sa, String mensaje) {
	    String formattedMessage = "!" + this.alias + "!" + mensaje;
	    byte[] bytesToSend = formattedMessage.getBytes();
        DatagramPacket messageToSend = new DatagramPacket(bytesToSend, bytesToSend.length, sa.getAddress(), sa.getPort());
        try {
            System.out.println(formattedMessage);
            System.out.println(messageToSend);
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
