package es.uma.informatica.rsd.chat.impl;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.SwingUtilities;

public class GUIUtils {

	  /**
	   * Return the owning <CODE>Frame</CODE> for the passed component of <CODE>null</CODE>
	   * if it doesn't have one.
	   * 
	   * @throws IllegalArgumentException
	   *           If <TT>wind</TT> is <TT>null</TT>.
	   */
	  public static Frame getOwningFrame(Component comp) {
	    if (comp == null) {
	      throw new IllegalArgumentException("null Component passed");
	    }

	    if (comp instanceof Frame) {
	      return (Frame) comp;
	    }
	    return getOwningFrame(SwingUtilities.windowForComponent(comp));
	  }
	}
