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
 *         primitivos, o indicando el nombre de los objetos que ser�n los
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
	 * compondr�n.
	 * 
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 * @param mainV
	 *            Ventana principal de la aplicaci�n.
	 * @param valorParametro
	 *            JComboBox que ser� actualizado despues de la creaci�n del
	 *            nuevo array mostrando su nombre.
	 * @param tipoDatosArray
	 *            Cadena que indica el tipo de los datos del array.
	 * @param numCampos
	 *            N�mero de elementos que tiene el nuevo array que se desea
	 *            crear.
	 * @param parent
	 *            Panel contenedor de este panel y que debe volver a ser
	 *            mostrado despues de la creaci�n del nuevo array.
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
	 * M�todo para la inicializaci�n del panel, inicializa el panel con las
	 * caracter�sticas necesarias para mostrar un di�logo de informaci�n de la
	 * ventana principal. Dicho panel debe tener un JLabel oculto para obtener
	 * el t�tulo del di�logo, un panel donde se encuentra lo que se quiere
	 * mostrar y un bot�n opcional que pasar�a a ser el bot�n por defecto del
	 * di�logo. En este caso se a�aden los tres elementos con lo que se mostrar�
	 * un di�logo con el t�tulo igual al texto del JLabel, un panel principal
	 * con los elementos del array, si el array es tipo primitivo o del tipo de
	 * los objetos que envuelven a los tipos primitivos, a�ade cajas de texto
	 * para introducir el valor, si el tipo del array es de cualquier otro tipo
	 * o tiene mas de una dimensi�n, a�ade JComboBox para indicar el nombre de
	 * los objetos que formaran parte del array. Por �ltimo a�ade un bot�n por
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
	 *         bot�n para crear y guardar un nuevo array. Cuando el usuario
	 *         pulsa el bot�n para crear un array, se obtienen los valores que
	 *         formar�n parte del nuevo array, si el array es de tipo primitivo
	 *         o de objetos que envuelven a los tipos primitivos, o se obtienen
	 *         los nombres de los elementos que formar�n parte del nuevo array
	 *         si �ste es de cualquier otro tipo. En caso de estar todo correcto
	 *         guarda el array en el contendor y actualiza el JComboBox donde se
	 *         solicit� a creaci�n de dicho array.
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
