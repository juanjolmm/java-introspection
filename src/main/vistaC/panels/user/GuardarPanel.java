package tfc.vistaC.panels.user;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import tfc.modelo.Analyzer;
import tfc.vistaC.InfoPanel;
import tfc.vistaC.MainWindow;

/**
 * @author jjlopez && EnriqueV: GuardarPanel es la clase que muestra un diálogo
 *         para permitir al usuario guardar en el contenedor los objetos que se
 *         devuelven al ejecutar algún método.
 */
public class GuardarPanel extends JPanel
{
	private static final long	serialVersionUID	= -6906042786774968526L;
	private MainWindow			ventana;
	private Analyzer			cargador;
	private Object				objeto;
	private JTextField			nombreObj			= new JTextField(13);

	/**
	 * Constructor que inicializa un objeto de tipo GuardarPanel, necesario para
	 * dar la opción al usuario de guardar los objetos que son devueltos al
	 * ejecutar algún método.
	 * 
	 * @param parent
	 *            Ventana principal de la aplicación.
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 * @param valorDevuelto
	 *            Objeto devuelto por algún método y que se pregunta si se desea
	 *            guardar.
	 * @param nombreMetodo
	 *            Nombre del método que ha sido ejecutado y que devuelve el
	 *            objeto a guardar.
	 */
	public GuardarPanel(MainWindow parent, Analyzer carga, Object valorDevuelto, String nombreMetodo)
	{
		super();
		ventana = parent;
		cargador = carga;
		objeto = valorDevuelto;
		showGuardarDialog(nombreMetodo + ": ¿Desea guardar el objeto devuelto?");
	}

	/**
	 * Genera un panel con las características necesarias para mostrar un
	 * diálogo de información de la ventana principal. Dicho panel debe tener un
	 * JLabel oculto para obtener el título del diálogo, un panel donde se
	 * encuentra lo que se quiere mostrar y un botón opcional que pasaría a ser
	 * el botón por defecto del diálogo. En este caso se añaden los tres
	 * elementos, con lo que se mostrará un diálogo con el título igual al texto
	 * del JLabel, un panel principal mostrando la información relativa al
	 * objeto devuelto y que se desea guardar y un cuadro de texto para
	 * introducir el nombre del nuevo objeto y, por último, un botón por defecto
	 * que permite guardar el objeto en el contenedor.
	 */
	private void showGuardarDialog(String titulo)
	{
		setLayout(new BorderLayout());
		JLabel etiTitulo = new JLabel(titulo);
		add(etiTitulo, BorderLayout.NORTH);
		etiTitulo.setVisible(false);

		add(initContenido(), BorderLayout.CENTER);

		JButton guardar = new JButton("Guardar");
		guardar.addActionListener(new ControladorGuardarObj());
		add(guardar, BorderLayout.SOUTH);

		ventana.initDialog(this);
	}

	/**
	 * Devuelve el panel principal que se muestra en el diálogo que sirve para
	 * guardar un objeto en el contenedor. Dicho panel muestra la información
	 * relativa al objeto y un cuadro de texto que permite al usuario introducir
	 * el nombre con el que se hará referencia a dicho objeto.
	 * 
	 * @return Devuelve el panel principal que se muestra en el diálogo que
	 *         sirve para guardar un objeto en el contenedor.
	 */
	private JPanel initContenido()
	{
		JPanel retorno = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(2, 2, 2, 2);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		JLabel etiqueta = new JLabel("Nombre del objeto");
		etiqueta.setFont(new Font("Tahoma", Font.BOLD, 12));
		retorno.add(etiqueta, constraints);
		constraints.gridx = 1;
		retorno.add(nombreObj, constraints);

		JLabel etiqueta2 = new JLabel("Descripcion del objeto: ");
		etiqueta2.setFont(new Font("Tahoma", Font.BOLD, 12));
		constraints.gridx = 0;
		constraints.gridy = 1;
		retorno.add(etiqueta2, constraints);

		String textoDescrip = null;
		if (objeto.toString().length() > 50)
			textoDescrip = objeto.toString().substring(0, 50) + "...";
		else
			textoDescrip = objeto.toString();
		JLabel descrip = new JLabel(textoDescrip);
		descrip.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 12));
		constraints.gridx = 1;
		retorno.add(descrip, constraints);

		return retorno;
	}

	// ///////////////////////////////////////////////////////////////
	// //////CLASES INTERNAS CONTROLADORAS DE EVENTOS
	// /////////////////////////////////////////////////////////////

	/**
	 * @author jjlopez && enriqueV: Clase que controla, una vez metido un nombre
	 *         para el nuevo objeto, si el usuario pulsa un botón para guardar
	 *         el objeto. Cuando el usuario pulsa el botón, se comprueba que se
	 *         haya introducido un nombre correcto y si es asi, se guarda el
	 *         objeto en el contenedor y se añade una nueva ficha a la ventana
	 *         principal con la información del nuevo objeto.
	 */
	class ControladorGuardarObj implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			if (nombreObj.getText().length() != 0 && !cargador.existeObjeto(nombreObj.getText()))
				try
				{
					if (cargador.meterObjeto(nombreObj.getText(), objeto))
					{
						InfoPanel panel = new InfoPanel(ventana, cargador, nombreObj.getText());
						ventana.addPestania(objeto.getClass().getName() + ":" + nombreObj.getText(), panel);
						ventana.closeDialog();
						ventana.refreshStatus("Nuevo objeto guardado correctamente");
					}
					else
						ventana.showInfo("Error al guardar el objeto", "Error", JOptionPane.ERROR_MESSAGE, true);
				}
				catch (Exception e)
				{
					ventana.showInfo("Error al guardar el objeto: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE, true);
				}
			else
				ventana.showInfo("Indique un nombre diferente", "Aviso", JOptionPane.WARNING_MESSAGE, true);
		}
	}
}
