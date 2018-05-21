package es.uma.informatica.rsd.chat.impl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import es.uma.informatica.rsd.chat.ifaces.Controlador;
import es.uma.informatica.rsd.chat.ifaces.Vista;

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
public class VConversacion extends JPanel{
	
	private JTextPane dialogo;
	private JPanel jPanel1;
	private JButton enviar;
	private JButton cerrar;
	private JTextField entrada;
	private StyledDocument doc;
	
	private String nombre;
	
	private Controlador c;
	
	public VConversacion(String nombre)
	{
		super();
		this.nombre = nombre;
		initGUI();
	}

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new VConversacion("Prueba"));
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	private void addStyles(StyledDocument d)
	{
		Style def = StyleContext.getDefaultStyleContext().
	            getStyle(StyleContext.DEFAULT_STYLE);

		Style propio = d.addStyle(Vista.PROPIO, def);
		StyleConstants.setForeground(propio, Color.blue);

		Style ajeno = d.addStyle(Vista.AJENO, def);
		StyleConstants.setForeground(ajeno, Color.red);
	}
	
	
	
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			//this.setPreferredSize(new java.awt.Dimension(634, 300));
			{
				dialogo = new JTextPane();
				JScrollPane jScrollPane = new JScrollPane(dialogo);
				this.add(jScrollPane, BorderLayout.CENTER);
				dialogo.setText("");
				doc = (StyledDocument)dialogo.getDocument();
				addStyles(doc);
			}
			{
				jPanel1 = new JPanel();
				this.add(jPanel1, BorderLayout.SOUTH);
				{
					entrada = new JTextField();
					jPanel1.add(entrada);
					entrada.setText("");
					entrada.setPreferredSize(new java.awt.Dimension(562, 22));
					entrada.setActionCommand(c.PREFIJO_ENVIAR+nombre);
				}
				{
					enviar = new JButton();
					jPanel1.add(enviar);
					enviar.setText("Enviar");
					enviar.setActionCommand(c.PREFIJO_ENVIAR+nombre);
				}
				{
					cerrar = new JButton();
					jPanel1.add(cerrar);
					cerrar.setText("Cerrar");
					cerrar.setActionCommand(c.PREFIJO_CERRAR+nombre);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setControlador(Controlador c)
	{
		this.c = c;
		ActionListener limpiar = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				entrada.setText("");
			}
		};
		
		enviar.addActionListener(limpiar);
		entrada.addActionListener(limpiar);
		
		enviar.addActionListener(c);
		cerrar.addActionListener(c);
		entrada.addActionListener(c);
	}
	
	public void mostrarMensaje(String msg, String estilo)
	{
		//dialogo.setForeground(Color.GREEN);
		try
		{
			doc.insertString(doc.getLength(), msg+"\n", doc.getStyle(estilo));
		}
		catch (Exception e)
		{
			
		}
		//dialogo.append(msg+"\n");
	}
	
	public String getMensajeEntrada()
	{
		return entrada.getText();
	}

}
