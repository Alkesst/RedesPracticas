package es.uma.informatica.rsd.chat.impl;

import java.io.IOException;
import java.net.*;

import es.uma.informatica.rsd.chat.ifaces.Comunicacion;
import es.uma.informatica.rsd.chat.ifaces.Controlador;
import es.uma.informatica.rsd.chat.impl.DialogoPuerto.PuertoAlias;

// Clase a implementar IP Profesor: 192.168.164.9, puerto: 10000. IP MULTICAS: 239.194.17.132
public class ComunicacionImpl implements Comunicacion {
    private static final String IP = "192.168.164.32";
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
        while (true) {
            byte[] data = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
            try {
                socket.receive(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fullMessage = new String(datagramPacket.getData());
            String[] messagePacket = fullMessage.split("!");
            InetSocketAddress address;
            if(datagramPacket.getAddress().isMulticastAddress()){
                address = new InetSocketAddress(messagePacket[0], datagramPacket.getPort());
            }else {
                address = new InetSocketAddress(datagramPacket.getAddress(), datagramPacket.getPort());
            }
            String nickName = messagePacket[1];
            String message = fullMessage.substring(nickName.length() + 2);
            if (!nickName.equals(alias)){
                controller.mostrarMensaje(address, nickName, message);
            }
        }
    }

	@Override
	public void envia(InetSocketAddress sa, String mensaje) {
        String formattedMessage;
        if (sa.getAddress().isMulticastAddress()) { // TODO encontrar una manera de saber que es IP amulticast!!!
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
