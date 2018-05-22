package es.uma.informatica.rsd.chat.impl;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Scanner;

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
        //es ejecución continua
        while(true) {
            //creación del datagrama
            byte[] buf = new byte[2048];
            DatagramPacket rec = new DatagramPacket(buf, 2048);

            System.out.println("Preparado para recibir");

            try {
                //guarda el datagrama
                s.receive(rec);

                System.out.println("Recibido");

                //creación de scanner y decodificación del datagrama
                String men = new String(buf, Charset.forName("UTF-8"));
                Scanner sc = new Scanner (men);
                sc.useDelimiter("!");

                System.out.println("Recibido: " + men);

                //preparación del vector donde guardamos los datos
                String[] partes = new String [3];

                if(!men.startsWith("!") && sc.hasNext()) {
                    partes[0] = sc.next();
                }

                //descomposición del mensaje
                for(int i=1; i<=2 && sc.hasNext(); i++) {
                    partes[i] = sc.next();
                }

                //creamos el socket del remitente
                InetSocketAddress remitente = new InetSocketAddress(InetAddress.getByName(partes[0]),rec.getPort());

                //si es unicast
                if (men.startsWith("!")){
                    //mostramos la información del datagrama
                    controller.mostrarMensaje(rec.getSocketAddress(), partes[1], partes[2]);
                } else {
                    //es multicast
                    if(partes[1].equals(alias)){
                        //si es un mensaje propio no hacemos nada
                    }else{
                        //corregimos el remitentec
                        controller.mostrarMensaje(remitente, partes[1], partes[2]);
                    }
                }

                //cerramos scanner
                sc.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

	@Override
	public void envia(InetSocketAddress sa, String mensaje) {
	    // runs
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
