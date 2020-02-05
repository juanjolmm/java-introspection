package tfc.vistaC.panels.parametros.arrays;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tfc.modelo.Analyzer;
import tfc.vistaC.InfoPanel;
import tfc.vistaC.MainWindow;

/**
 * @author jjlopez enriqueV: Clase que representa un panel en el que se da la
 *         oportunidad al usuario de crear un nuevo array, de un determinado
 *         tipo, indicando el núemro de elementos que tendrá el array.
 * 
 */
public class ArrayParamPanel extends JPanel
{
	private static final long	serialVersionUID	= -6906042786774968526L;
	private Analyzer			cargador;
	private MainWindow			ventana;
	private JPanel				panelPadre;
	private String				tipo;
	private JComponent			valorParam;
	private JFormattedTextField	longArray;

	/**
	 * Constructor que inicializa un objeto de tipo ArrayParamPanel, necesario
	 * para poder crear nuevos arrays e indicar la longitud de éstos.
	 * 
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 * @param mainV
	 *            Ventana principal de la aplicación.
	 * @param tipoArray
	 *            Cadena que indica el tipo del array.
	 * @param valorParametro
	 *            JComboBox que será actualizado despues de la creación del
	 *            nuevo array mostrando su nombre.
	 * @param parent
	 *            Panel contenedor de este panel y que debe volver a ser
	 *            mostrado despues de la creación del nuevo array.
	 */
	public ArrayParamPanel(Analyzer carga, MainWindow mainV, String tipoArray, JComponent valorParametro, JPanel parent)
	{
		super();
		cargador = carga;
		ventana = mainV;
		panelPadre = parent;
		tipo = tipoArray;
		valorParam = valorParametro;
		initArrayParamPanel();
	}

	/**
	 * Método para la inicialización del panel, dicho panel contiene un campo de
	 * texto para introducir la longitud del array y un boton que sirve para
	 * mostrar el panel de creación de nuevos arrays.
	 */
	public void initArrayParamPanel()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JLabel longitud = new JLabel(" Longitud: ");
		longitud.setFont(new Font("Lucida Console", Font.TRUETYPE_FONT, 12));
		longArray = new JFormattedTextField(new Integer(1));
		longArray.setColumns(3);

		add(valorParam);
		add(longitud);
		add(longArray);

		JButton creaArray = new JButton("Crear");
		creaArray.addActionListener(new ControladorNuevoArray());
		add(creaArray);
	}

	// ///////////////////////////////////////////////////////////////
	// //////CLASES INTERNAS CONTROLADORAS DE EVENTOS
	// /////////////////////////////////////////////////////////////

	/**
	 * @author jjlopez && enriqueV: Clase que controla si el usuario pulsa un
	 *         botón para solicitar crear un nuevo array de un determinado tipo.
	 *         Cuando el usuario pulsa el botón para crear un array, se obtiene
	 *         el número de elementos que contendrá el array y se muestra un
	 *         diálogo para especificar los componentes del array y guardar
	 *         dicho array.
	 */
	class ControladorNuevoArray implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int nCampos = Integer.parseInt(longArray.getValue().toString());
			String datos[] = cargador.datosTipo(tipo);
			if (nCampos > 0 && datos[2].equals("yes") && !datos[1].equals("yes") && cargador.getObjetosTipo(datos[3]).size() == 0)
			{
				ventana.showInfo("Debes crear primero un objeto de tipo " + datos[3] + " para crear el array", "Información", JOptionPane.INFORMATION_MESSAGE, true);
				int estado = cargador.abrirClase(datos[3]);
				if (estado == 1)
				{
					InfoPanel panel = new InfoPanel(ventana, cargador, datos[3]);
					ventana.addPestania(datos[3], panel);
					ventana.closeDialog();
				}
				else if (estado == -1)
					ventana.showInfo("No se encuentra la clase en el classpath", "Error", JOptionPane.ERROR_MESSAGE, true);
				else
				{
					ventana.closeDialog();
					if (!ventana.selectPestania(datos[3]))
					{
						InfoPanel panel = new InfoPanel(ventana, cargador, datos[3]);
						ventana.addPestania(datos[3], panel);
					}
				}
			}
			else if (Integer.parseInt(datos[4]) > 1)
				new NewArrayPanel(cargador, ventana, valorParam, datos[0].substring(1, datos[0].length()), nCampos, panelPadre);
			else
				new NewArrayPanel(cargador, ventana, valorParam, datos[3], nCampos, panelPadre);
		}
	}
}
