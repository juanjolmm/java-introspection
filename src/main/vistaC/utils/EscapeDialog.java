package tfc.vistaC.utils;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 * @author jjlopez && enriqueV: Clase que deriva de JDialog, cuya diferencia es
 *         que permite cerrar el JDialog pulsando la tecla ESCAPE.
 * 
 */
public class EscapeDialog extends JDialog
{
	private static final long	serialVersionUID	= 6590082099765340257L;

	public EscapeDialog(JFrame ventana, String titulo)
	{
		super(ventana, titulo, true);
	}

	protected JRootPane createRootPane()
	{
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action actionListener = new AbstractAction() {
			private static final long	serialVersionUID	= -5676510794171660705L;

			public void actionPerformed(ActionEvent actionEvent)
			{
				dispose();
			}
		};
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", actionListener);
		return rootPane;
	}
}