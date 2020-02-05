package tfc.vistaC.panels.parametros.arrays;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import tfc.modelo.Analyzer;
import tfc.vistaC.MainWindow;
import tfc.vistaC.panels.parametros.ParametersPanel;

/**
 * @author jjlopez enriqueV: Clase que representa un panel en el que se puede
 *         crear un nuevo array, indicando el valor de los componentes, si el
 *         array es tipo primitivo o de objetos que envuelven a los tipos
 *         primitivos, o indicando el nombre de los objetos que serán los
 *         componentes del array.
 */
public class NewArrayPanel extends JPanel
{
	private static final long	serialVersionUID	= -6906042786774968526L;
	private Analyzer			cargador;
	private MainWindow			ventana;
	private JPanel				panelPadre;
	private String				tipoDatos;
	private JComponent			valorParam;
	private int					longArray;
	private JComponent[]		valoresArray;
	private JTextField			nombreArray;

	/**
	 * Constructor que inicializa un objeto de tipo NewArrayPanel, que permite
	 * crear y guardar nuevos arrays especificando los componentes que lo
	 * compondrán.
	 * 
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 * @param mainV
	 *            Ventana principal de la aplicación.
	 * @param valorParametro
	 *            JComboBox que será actualizado despues de la creación del
	 *            nuevo array mostrando su nombre.
	 * @param tipoDatosArray
	 *            Cadena que indica el tipo de los datos del array.
	 * @param numCampos
	 *            Número de elementos que tiene el nuevo array que se desea
	 *            crear.
	 * @param parent
	 *            Panel contenedor de este panel y que debe volver a ser
	 *            mostrado despues de la creación del nuevo array.
	 */
	public NewArrayPanel(Analyzer carga, MainWindow mainV, JComponent valorParametro, String tipoDatosArray, int numCampos, JPanel parent)
	{
		super();
		cargador = carga;
		ventana = mainV;
		panelPadre = parent;
		valorParam = valorParametro;
		tipoDatos = tipoDatosArray;
		longArray = numCampos;
		valoresArray = new JComponent[longArray];
		nombreArray = new JTextField(13);
		setLayout(new BorderLayout());
		initNewArrayPanel();
	}

	/**
	 * Método para la inicialización del panel, inicializa el panel con las
	 * características necesarias para mostrar un diálogo de información de la
	 * ventana principal. Dicho panel debe tener un JLabel oculto para obtener
	 * el título del diálogo, un panel donde se encuentra lo que se quiere
	 * mostrar y un botón opcional que pasaría a ser el botón por defecto del
	 * diálogo. En este caso se añaden los tres elementos con lo que se mostrará
	 * un diálogo con el título igual al texto del JLabel, un panel principal
	 * con los elementos del array, si el array es tipo primitivo o del tipo de
	 * los objetos que envuelven a los tipos primitivos, añade cajas de texto
	 * para introducir el valor, si el tipo del array es de cualquier otro tipo
	 * o tiene mas de una dimensión, añade JComboBox para indicar el nombre de
	 * los objetos que formaran parte del array. Por último añade un botón por
	 * defecto que permite guardar dicho array.
	 */
	private void initNewArrayPanel()
	{
		JLabel titulo = new JLabel("Crear array de longitud " + longArray);
		add(titulo, BorderLayout.NORTH);
		titulo.setVisible(false);

		String[] tiposParam = new String[longArray];
		for (int i = 0; i < longArray; i++)
			tiposParam[i] = tipoDatos;
		JPanel elementos = new ParametersPanel(cargador, ventana, tiposParam, valoresArray, nombreArray, this);
		add(elementos, BorderLayout.CENTER);

		JButton bGuardar = new JButton("Guardar");
		bGuardar.addActionListener(new ControladorGuardarArray());
		add(bGuardar, BorderLayout.SOUTH);

		ventana.initDialog(this);
	}

	// ///////////////////////////////////////////////////////////////
	// //////CLASES INTERNAS CONTROLADORAS DE EVENTOS
	// /////////////////////////////////////////////////////////////

	/**
	 * @author jjlopez && enriqueV: Clase que controla si el usuario pulsa un
	 *         botón para crear y guardar un nuevo array. Cuando el usuario
	 *         pulsa el botón para crear un array, se obtienen los valores que
	 *         formarán parte del nuevo array, si el array es de tipo primitivo
	 *         o de objetos que envuelven a los tipos primitivos, o se obtienen
	 *         los nombres de los elementos que formarán parte del nuevo array
	 *         si éste es de cualquier otro tipo. En caso de estar todo correcto
	 *         guarda el array en el contendor y actualiza el JComboBox donde se
	 *         solicitó a creación de dicho array.
	 */
	class ControladorGuardarArray implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (!cargador.existeObjeto(nombreArray.getText()) && nombreArray.getText().length() != 0)
			{
				boolean ok = true;
				String[] componentes = new String[longArray];
				for (int i = 0; i < longArray; i++)
				{
					if (valoresArray[i] instanceof JFormattedTextField)
						componentes[i] = ((JFormattedTextField) valoresArray[i]).getValue().toString();
					else
					{
						if (((JComboBox) valoresArray[i]).getSelectedItem().toString().equals("-Escoja un elemento-"))
						{
							ventana.showInfo("Revise los parametros", "Aviso", JOptionPane.WARNING_MESSAGE, true);
							ok = false;
							break;
						}
						else
							componentes[i] = ((JComboBox) valoresArray[i]).getSelectedItem().toString();
					}
				}
				try
				{
					if (ok)
					{
						cargador.meterArray(nombreArray.getText(), tipoDatos, componentes);
						((JComboBox) valorParam).addItem(nombreArray.getText());
						((JComboBox) valorParam).setSelectedItem(nombreArray.getText());
						ventana.initDialog(panelPadre);
					}
				}
				catch (Exception e1)
				{
					ventana.showInfo("Error en la creacion del array: " + e1.toString(), "Error", JOptionPane.ERROR_MESSAGE, true);
				}
			}
			else
				ventana.showInfo("Indique otro nombre", "Error en el nombre", JOptionPane.WARNING_MESSAGE, true);
		}
	}
}
