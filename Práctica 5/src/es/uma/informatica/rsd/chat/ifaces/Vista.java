package es.uma.informatica.rsd.chat.ifaces;

import java.net.InetSocketAddress;

import es.uma.informatica.rsd.chat.impl.DialogoPuerto.PuertoAlias;

/**
 * Interfaz de la vista.
 *
 */

public interface Vista
{	
	public static final String PROPIO ="propio";
	public static final String AJENO ="ajeno";
	public static final String TITLE = "Chat UDP";
	
	
	/**
	 * Este m�todo se utiliza para crear ventanas de conversaci�n.
	 * @param nombre Nombre de la ventana de conversaci�n. Debe ser �nico.
	 * @return Si todo fue correcto devuelve true. En caso de que ya existiera una 
	 * ventana con ese nombre devuelve false.
	 */
	public boolean crearPanel(String nombre);
	
	/**
	 * Este m�todo se utiliza para cerrar ventanas de conversaci�n. 
	 * @param nombre Nombre de la ventana de conversaci�n a cerrar.
	 * @return Devuelve true si todo fue bien y false si la ventana no existe.
	 */
	public boolean cerrarPanel(String nombre);
	
	/**
	 * Este m�todo lo invca el controlador para escribir un mensaje en la ventana de
	 * conversaci�n indicada como primer argumento.
	 * @param nombre Nombre de la ventana de conversaci�n.
	 * @param mensaje Mensaje a escribir en la ventana.
	 * @param estilo Estilo del mensaje. Pueder ser "propio" o "ajeno" para indicar 
	 * de qui�n procede el texto.
	 */
	public void mostrarMensaje(String nombre, String mensaje, String estilo);
	
	/**
	 * Este m�todo se llama al inicio para conectar la vista al controlador
	 * @param al Controlador.
	 */
	public void setControlador(Controlador al);
	
	/**
	 * Este m�todo se usar� al comienzo de la sesi�n para preguntar al usuario el puerto
	 * en el que escuchar� el programa.
	 * @return Devuelve el n�mero de puerto introducido por el usuario.
	 */
	public PuertoAlias getPuertoEscuchaAlias();
	
	/**
	 * Este m�todo se usar� cada vez que el usuario solicite crear una nueva ventana de 
	 * conversaci�n para preguntar por la direcci�n IP y el puerto al que se deben enviarse
	 * los mensajes.
	 * @return Devuelve la direcci�n de socket a la que hay que enviar los mensajes.
	 */
	public InetSocketAddress getIPPuerto();
	
	/**
	 * Este m�todo se utiliza para obtener el mensaje que el usuario escribi� en una 
	 * determinada ventana de conversaci�n.
	 * @param nombre Nombre de la ventana de conversaci�n.
	 * @return
	 */
	public String getMensaje(String nombre);
	
	/**
	 * Muestra un mensaje de advertencia en una ventana modal.
	 * @param titulo T�tulo de la ventana.
	 * @param mensaje Mensaje.
	 */
	public void warning(String titulo, String mensaje);
}
