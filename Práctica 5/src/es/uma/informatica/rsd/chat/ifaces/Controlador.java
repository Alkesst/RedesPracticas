package es.uma.informatica.rsd.chat.ifaces;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.net.SocketAddress;

/**
 * Interfaz del controlador.
 *
 */

public interface Controlador extends ActionListener
{
	// Cadenas de comando para botones
	public static final String NUEVO = "nuevo";
	public static final String PREFIJO_ENVIAR = "enviar";
	public static final String PREFIJO_CERRAR = "cerrar";
	
	/**
	 * Este m�todo es invocado por el componente de comunicaci�n para indicar que se ha recibido
	 * un mensaje de un determinado proceso cuya direcci�n de socket se indica.
	 * @param sa Direcci�n de socket del proceso que envi� el mensaje.
	 * @param mensaje Mensaje que envi� dicho proceso.
	 */
	public void mostrarMensaje(SocketAddress sa, String alias, String mensaje);
}
