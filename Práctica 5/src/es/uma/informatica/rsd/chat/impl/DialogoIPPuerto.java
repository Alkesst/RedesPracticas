package es.uma.informatica.rsd.chat.impl;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
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
public class DialogoIPPuerto extends javax.swing.JDialog {
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JRadioButton multicast;
	private JRadioButton unicast;
	private ButtonGroup difusion;
	private JButton cancelar;
	private JButton aceptar;
	private JTextField puerto;
	private JTextField ip;
	
	
	private int puertoEscucha;
	private InetSocketAddress resultado;
	

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				DialogoIPPuerto inst = new DialogoIPPuerto(frame,0);
				inst.setVisible(true);
			}
		});
	}
	
	public DialogoIPPuerto(Frame frame, int puertoEscucha) {
		super(frame,"IP y puerto",true);
		initGUI();
		setListeners();
		this.puertoEscucha = puertoEscucha;
		unicast.setSelected(true);
	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			{
				jLabel1 = new JLabel();
				jLabel1.setText("Dirección IP");
			}
			{
				ip = new JTextField();
			}
			{
				jLabel2 = new JLabel();
				jLabel2.setText("Puerto");
			}
			{
				unicast = new JRadioButton();
				unicast.setText("Unicast");
			}
			{
				multicast = new JRadioButton();
				multicast.setText("Multicast");
			}
			{
				difusion = new ButtonGroup();
				difusion.add(unicast);
				difusion.add(multicast);
			}
			{
				puerto = new JTextField();
				puerto.setText("");
			}
			{
				aceptar = new JButton();
				aceptar.setText("Aceptar");
			}
			{
				cancelar = new JButton();
				cancelar.setText("Cancelar");
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(jLabel1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(jLabel2, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(ip, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(puerto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(unicast, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(aceptar, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(cancelar, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(multicast, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(31, 31));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(thisLayout.createSequentialGroup()
				        .addGroup(thisLayout.createParallelGroup()
				            .addComponent(ip, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
				            .addComponent(jLabel1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addGroup(thisLayout.createParallelGroup()
				            .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                .addComponent(puerto, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
				                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
				                .addGroup(thisLayout.createParallelGroup()
				                    .addComponent(multicast, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
				                    .addComponent(unicast, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)))
				            .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
				                .addGap(0, 84, Short.MAX_VALUE))))
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addGap(21)
				        .addComponent(aceptar, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				        .addComponent(cancelar, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 110, Short.MAX_VALUE)))
				.addContainerGap());

			this.setSize(320, 136);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setListeners()
	{
		ActionListener al = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				resultado = new InetSocketAddress(ip.getText(),Integer.parseInt(puerto.getText()));
				if (multicast.isSelected() && !resultado.getAddress().isMulticastAddress())
				{
					JOptionPane.showMessageDialog(GUIUtils.getOwningFrame(DialogoIPPuerto.this),
						    "La dirección IP no es de un grupo multicast",
						    "Multicast",
						    JOptionPane.WARNING_MESSAGE);
				}
				else if (unicast.isSelected() && resultado.getAddress().isMulticastAddress())
				{
					JOptionPane.showMessageDialog(GUIUtils.getOwningFrame(DialogoIPPuerto.this),
						    "La dirección IP no es unicast",
						    "Unicast",
						    JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					setVisible(false);
				}
			}
		};
		aceptar.addActionListener(al);
		ip.addActionListener(al);
		puerto.addActionListener(al);
		cancelar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				resultado = null;
				setVisible(false);
			}
		});
		
		unicast.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				puerto.setEnabled(true);
			}	
		});
		
		multicast.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				puerto.setText(""+puertoEscucha);
				puerto.setEnabled(false);
			}	
		});
		
	}
	
	public InetSocketAddress muestra()
	{
		setVisible(true);
		return resultado;
	}
	
	

}
