package es.uma.informatica.rsd.chat.impl;
import java.awt.FlowLayout;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import es.uma.informatica.rsd.chat.ifaces.Controlador;
import es.uma.informatica.rsd.chat.ifaces.Vista;
import es.uma.informatica.rsd.chat.impl.DialogoPuerto.PuertoAlias;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Paneles extends JPanel implements Vista{
	
	private DialogoIPPuerto dialogo;
	private DialogoPuerto escucha;
	private int puertoEscucha;
	
	private Map<String, VConversacion> map;
	
	private JTabbedPane paneles;
	private JPanel jPanel1;
	private JButton nuevo;
	private Controlador al;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new Paneles());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public Paneles() {
		super();
		initGUI();
		map = new HashMap<String,VConversacion>();
	}
	
	private void initGUI() {
		try {
			FlowLayout thisLayout = new FlowLayout();
			this.setLayout(thisLayout);
			{
				paneles = new JTabbedPane();
				this.add(paneles);
				paneles.setPreferredSize(new java.awt.Dimension(800, 500));
			}
			{
				jPanel1 = new JPanel();
				this.add(jPanel1);
				BoxLayout jPanel1Layout = new BoxLayout(jPanel1, BoxLayout.Y_AXIS);
				jPanel1.setLayout(jPanel1Layout);
				{
					nuevo = new JButton();
					jPanel1.add(nuevo);
					nuevo.setText("Nuevo");
					nuevo.setActionCommand("nuevo");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private DialogoIPPuerto getDialogoIPPuerto()
	{
		if (dialogo == null)
		{
			dialogo = new DialogoIPPuerto (GUIUtils.getOwningFrame(this),puertoEscucha);
		}
		return dialogo;
	}
	
	
	private DialogoPuerto getDialogoPuerto()
	{
		if (escucha == null)
		{
			escucha =  new DialogoPuerto (GUIUtils.getOwningFrame(this));
		}
		return escucha;
	}
	
	@Override
	public boolean crearPanel(String nombre)
	{	
		VConversacion jp = map.get(nombre);
		if (jp == null)
		{
			jp = new VConversacion(nombre);
			paneles.add(nombre, jp);
			map.put(nombre, jp);
			jp.setControlador(al);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean cerrarPanel(String nombre)
	{
		VConversacion vc = map.get(nombre);
		if (vc == null)
		{
			return false;
		}
		
		paneles.remove(vc);
		map.remove(nombre);
		
		return true;
	}

	@Override
	public void mostrarMensaje(String nombre, String mensaje, String estilo)
	{		
		VConversacion jp = map.get(nombre);
		jp.mostrarMensaje(mensaje, estilo);
	}

	@Override
	public void setControlador(Controlador al)
	{
		this.al= al;
		nuevo.addActionListener(al);
	}

	@Override
	public PuertoAlias getPuertoEscuchaAlias()
	{
		PuertoAlias pa = getDialogoPuerto().getPuertoAlias();
		if (pa != null)
		{
			puertoEscucha = pa.puerto;
			GUIUtils.getOwningFrame(this).setTitle(TITLE+":"+puertoEscucha);
		}
		return pa;
	}

	@Override
	public InetSocketAddress getIPPuerto()
	{
		return getDialogoIPPuerto().muestra();
	}
	
	@Override
	public String getMensaje(String nombre)
	{
		return map.get(nombre).getMensajeEntrada();
	}
	
	@Override
	public void warning (String titulo, String mensaje)
	{
		JOptionPane.showMessageDialog(GUIUtils.getOwningFrame(this),
			    mensaje,
			    titulo,
			    JOptionPane.WARNING_MESSAGE);
	}

}
