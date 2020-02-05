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
 * @author jjlopez && EnriqueV: GuardarPanel es la clase que muestra un di�logo
 *         para permitir al usuario guardar en el contenedor los objetos que se
 *         devuelven al ejecutar alg�n m�todo.
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
	 * dar la opci�n al usuario de guardar los objetos que son devueltos al
	 * ejecutar alg�n m�todo.
	 * 
	 * @param parent
	 *            Ventana principal de la aplicaci�n.
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 * @param valorDevuelto
	 *            Objeto devuelto por alg�n m�todo y que se pregunta si se desea
	 *            guardar.
	 * @param nombreMetodo
	 *            Nombre del m�todo que ha sido ejecutado y que devuelve el
	 *            objeto a guardar.
	 */
	public GuardarPanel(MainWindow parent, Analyzer carga, Object valorDevuelto, String nombreMetodo)
	{
		super();
		ventana = parent;
		cargador = carga;
		objeto = valorDevuelto;
		showGuardarDialog(nombreMetodo + ": �Desea guardar el objeto devuelto?");
	}

	/**
	 * Genera un panel con las caracter�sticas necesarias para mostrar un
	 * di�logo de informaci�n de la ventana principal. Dicho panel debe tener un
	 * JLabel oculto para obtener el t�tulo del di�logo, un panel donde se
	 * encuentra lo que se quiere mostrar y un bot�n opcional que pasar�a a ser
	 * el bot�n por defecto del di�logo. En este caso se a�aden los tres
	 * elementos, con lo que se mostrar� un di�logo con el t�tulo igual al texto
	 * del JLabel, un panel principal mostrando la informaci�n relativa al
	 * objeto devuelto y que se desea guardar y un cuadro de texto para
	 * introducir el nombre del nuevo objeto y, por �ltimo, un bot�n por defecto
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
	 * Devuelve el panel principal que se muestra en el di�logo que sirve para
	 * guardar un objeto en el contenedor. Dicho panel muestra la informaci�n
	 * relativa al objeto y un cuadro de texto que permite al usuario introducir
	 * el nombre con el que se har� referencia a dicho objeto.
	 * 
	 * @return Devuelve el panel principal que se muestra en el di�logo que
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
	 *         para el nuevo objeto, si el usuario pulsa un bot�n para guardar
	 *         el objeto. Cuando el usuario pulsa el bot�n, se comprueba que se
	 *         haya introducido un nombre correcto y si es asi, se guarda el
	 *         objeto en el contenedor y se a�ade una nueva ficha a la ventana
	 *         principal con la informaci�n del nuevo objeto.
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
