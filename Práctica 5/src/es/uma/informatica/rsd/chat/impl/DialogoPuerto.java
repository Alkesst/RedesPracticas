package es.uma.informatica.rsd.chat.impl;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;


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
public class DialogoPuerto extends javax.swing.JDialog {
	
	/**
	 * Esta clase contiene los datos que se piden al usuario al inicio del Chat:
	 * el puerto de escucha y el alias del usuario.
	 *
	 */
	
	public static class PuertoAlias
	{
		public int puerto;
		public String alias;
	}
	
	
	private JLabel jLabel1;
	private JTextField puerto;
	private JButton aceptar;
	private JLabel jLabel2;
	private JTextField alias;

	private PuertoAlias resultado=null;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				DialogoPuerto inst = new DialogoPuerto(frame);
				inst.setVisible(true);
			}
		});
	}
	
	public DialogoPuerto(Frame frame) {
		super(frame,"Puerto",true);
		initGUI();
	}
	
	private void initGUI() {
		
		ActionListener al = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				resultado = new PuertoAlias();
				
				try
				{
					resultado.puerto = Integer.parseInt(puerto.getText());
				}
				catch (NumberFormatException e)
				{
					resultado.puerto=-1;
				}
				resultado.alias = alias.getText();
				setVisible(false);
			}
		};
		
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			{
				jLabel1 = new JLabel();
				jLabel1.setText("Puerto para escuchar");
			}
			{
				puerto = new JTextField();
				puerto.addActionListener(al);
			}
			{
				aceptar = new JButton();
				aceptar.setText("Aceptar");
				aceptar.addActionListener(al);
			}
			{
				jLabel2 = new JLabel();
				jLabel2.setText("Alias");
			}
			{
				alias = new JTextField();
				alias.addActionListener(al);
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(puerto, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(alias, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 1, Short.MAX_VALUE)
				.addComponent(aceptar, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(thisLayout.createSequentialGroup()
				        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 0, Short.MAX_VALUE))
				    .addGroup(thisLayout.createSequentialGroup()
				        .addComponent(puerto, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 0, Short.MAX_VALUE))
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 74, Short.MAX_VALUE))
				    .addGroup(thisLayout.createSequentialGroup()
				        .addComponent(alias, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 0, Short.MAX_VALUE))
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addGap(36)
				        .addComponent(aceptar, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 29, Short.MAX_VALUE)))
				.addContainerGap(22, 22));
			this.setSize(182, 176);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PuertoAlias getPuertoAlias()
	{
		resultado = null;
		setVisible(true);
		return resultado;
	}

}
