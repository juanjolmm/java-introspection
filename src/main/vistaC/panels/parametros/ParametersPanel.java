package tfc.vistaC.panels.parametros;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import tfc.modelo.Analyzer;
import tfc.vistaC.MainWindow;
import tfc.vistaC.panels.parametros.arrays.ArrayParamPanel;

/**
 * @author jjlopez enriqueV: Clase que representa un panel utilizado para
 *         inicializar todo tipo de parámetros, ya sean valores, objetos o
 *         arrays de cualquier tipo. Es utilizado por un constructor, por un
 *         método, para modificar el valor de un atributo o para inicializar un
 *         array.
 */
public class ParametersPanel extends JPanel
{
	private static final long	serialVersionUID	= -6906042786774968526L;
	private Analyzer			cargador;
	private MainWindow			ventana;
	private JPanel				panelPadre;
	private String[]			tiposParametros;
	private JComponent[]		valorParametros;
	private GridBagConstraints	constraints			= new GridBagConstraints();

	/**
	 * Constructor que inicializa un objeto de tipo ParametersPanel, necesario
	 * para poder especificar cualquier tipo de parámetros que necesite un
	 * método, o para modificar el valor de un atributo o para inicializar un
	 * array.
	 * 
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 * @param mainV
	 *            Ventana principal de la aplicación.
	 * @param parametros
	 *            Array de tipo String con los tipos de cada uno de los
	 *            parámetros.
	 * @param valores
	 *            JComponent necesarios para especificar los valores de los
	 *            parámetros, si se trata de valores de tipo primitivo u objetos
	 *            que envuelven a valores de tipo primitivo, se utilizan
	 *            JFormattedTextField y en caso contrario se utilizan JComboBox
	 *            para indicar el nombre del objeto que se requiere.
	 * @param parent
	 *            Panel padre en el que está insertado dicho panel, es necesario
	 *            para poder volver al panel anterior en caso de la creación de
	 *            un array.
	 */
	public ParametersPanel(Analyzer carga, MainWindow mainV, String[] parametros, JComponent[] valores, JPanel parent)
	{
		super();
		cargador = carga;
		ventana = mainV;
		panelPadre = parent;
		tiposParametros = parametros;
		valorParametros = valores;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(2, 2, 2, 2);
		constraints.anchor = GridBagConstraints.WEST;
		setLayout(new GridBagLayout());
		initParamPanel(0);
	}

	/**
	 * Constructor que inicializa un objeto de tipo ParametersPanel, necesario
	 * para poder especificar cualquier tipo de parámetros que necesite un
	 * constructor asi como el nombre de la nueva instancia.
	 * 
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 * @param mainV
	 *            Ventana principal de la aplicación.
	 * @param parametros
	 *            Array de tipo String con los tipos de cada uno de los
	 *            parámetros.
	 * @param valores
	 *            JComponent necesarios para especificar los valores de los
	 *            parámetros, si se trata de valores de tipo primitivo u objetos
	 *            que envuelven a valores de tipo primitivo, se utilizan
	 *            JFormattedTextField y en caso contrario se utilizan JComboBox
	 *            para indicar el nombre del objeto que se requiere.
	 * @param nombreInst
	 *            Utilizado sólo para los parámetros de los constructores, sirve
	 *            para indicar el nombre de la nueva instancia que se desea
	 *            crear.
	 * @param parent
	 *            Panel padre en el que está insertado dicho panel, es necesario
	 *            para poder volver al panel anterior en caso de la creación de
	 *            un array.
	 */
	public ParametersPanel(Analyzer carga, MainWindow mainV, String[] parametros, JComponent[] valores, JTextField nombreInst, JPanel parent)
	{
		super();
		cargador = carga;
		ventana = mainV;
		panelPadre = parent;
		tiposParametros = parametros;
		valorParametros = valores;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(2, 2, 2, 2);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.setLayout(new GridBagLayout());
		JLabel etiqueta = new JLabel("Nombre Instancia");
		etiqueta.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.add(etiqueta, constraints);
		constraints.gridx = 1;
		this.add(nombreInst, constraints);
		initParamPanel(1);
	}

	/**
	 * Inicializa el panel de parámetros. Cada uno de los parámetros aparece uno
	 * debajo del otro. Si el parámetro es de tipo primitivo o de un objeto que
	 * envuelve a un tipo primitivo, muestra un JFormattedTextField para
	 * introducir el nuevo valor. Si el parámetro es un array muestra un
	 * JComboBox para poder seleccionar un objeto de dicho tipo y un panel
	 * auxiliar para poder crear un nuevo array de dicho tipo. Si el parámetro
	 * es un objeto de cualquier otro tipo muestra un JComboBox para poder
	 * seleccionar un objeto de dicho tipo y un panel auxiliar para dar la
	 * posibilidad de analizar la clase de dicho tipo para que, usando los
	 * constructores de la clase, se pueda crear un nuevo objeto de dicho tipo.
	 * 
	 * @param y
	 *            Parámetro utilizado sólamente para saber cuando se trata de un
	 *            panel de parámetros para un constructor. Cuando se trata de un
	 *            panel de parámetros para un constructor, a parte de todos los
	 *            parámetros hay que añadir tambien un JTextField para indicar
	 *            el nombre del nuevo objeto que se pretende crear.
	 */
	private void initParamPanel(int y)
	{
		Font fuente = new Font("Lucida Console", Font.TRUETYPE_FONT, 12);
		for (int i = 0; i < tiposParametros.length; i++)
		{
			String[] datosT = cargador.datosTipo(tiposParametros[i]);
			String tipoP = null;
			if (datosT[2].equals("yes"))
			{
				String corchetes = "";
				for (int k = 0; k < Integer.parseInt(datosT[4]); k++)
					corchetes = corchetes + "[]";
				tipoP = datosT[3] + corchetes;
			}
			else
				tipoP = tiposParametros[i];
			JLabel tipo = new JLabel(String.valueOf(i + 1) + "-" + tipoP);
			tipo.setFont(fuente);
			constraints.gridx = 0;
			constraints.gridy = i + y;
			this.add(tipo, constraints);
			constraints.gridx = 1;
			if (datosT[0].equals("int"))
			{
				valorParametros[i] = new JFormattedTextField(new Integer(10));
				((JTextField) valorParametros[i]).setColumns(13);
				this.add(valorParametros[i], constraints);
			}
			else if (datosT[0].equals("float"))
			{
				valorParametros[i] = new JFormattedTextField(new Float(10.5));
				((JTextField) valorParametros[i]).setColumns(13);
				this.add(valorParametros[i], constraints);
			}
			else if (datosT[0].equals("double"))
			{
				valorParametros[i] = new JFormattedTextField(new Double(10.5));
				((JTextField) valorParametros[i]).setColumns(13);
				this.add(valorParametros[i], constraints);
			}
			else if (datosT[0].equals("byte"))
			{
				valorParametros[i] = new JFormattedTextField(new Byte((byte) 10));
				((JTextField) valorParametros[i]).setColumns(13);
				this.add(valorParametros[i], constraints);
			}
			else if (datosT[0].equals("short"))
			{
				valorParametros[i] = new JFormattedTextField(new Short((short) 10));
				((JTextField) valorParametros[i]).setColumns(13);
				this.add(valorParametros[i], constraints);
			}
			else if (datosT[0].equals("long"))
			{
				valorParametros[i] = new JFormattedTextField(new Long(10));
				((JTextField) valorParametros[i]).setColumns(13);
				this.add(valorParametros[i], constraints);
			}
			else if (datosT[0].equals("char"))
			{
				try
				{
					MaskFormatter mfCC = new MaskFormatter("*");
					valorParametros[i] = new JFormattedTextField(mfCC);
					((JFormattedTextField) valorParametros[i]).setValue('c');
					((JTextField) valorParametros[i]).setColumns(1);
					this.add(valorParametros[i], constraints);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			else if (datosT[0].equals("boolean"))
			{
				valorParametros[i] = new JFormattedTextField(new Boolean("false"));
				((JTextField) valorParametros[i]).setColumns(13);
				this.add(valorParametros[i], constraints);
			}
			else if (datosT[0].equals("String"))
			{
				valorParametros[i] = new JFormattedTextField(new String("cadena"));
				((JTextField) valorParametros[i]).setColumns(13);
				this.add(valorParametros[i], constraints);
			}
			else if (datosT[0].startsWith("[")) // PARA LOS ARRAYS
			{
				Vector<String> arrays = cargador.getObjetosTipo(datosT[0]);
				arrays.addElement("-Escoja un elemento-");
				valorParametros[i] = new JComboBox(arrays);
				this.add(new ArrayParamPanel(cargador, ventana, datosT[0], valorParametros[i], panelPadre), constraints);
			}
			else
			// Si los tipos son objetos no primitivos
			{
				Vector<String> objetos = cargador.getObjetosTipo(datosT[0]);
				objetos.addElement("-Escoja un elemento-");
				valorParametros[i] = new JComboBox(objetos);
				this.add(new ObjectParamPanel(cargador, ventana, datosT[0], valorParametros[i]), constraints);
			}

		}
	}

	/**
	 * Devuelve un array de Object, con los valores de cada parámetro
	 * introducidos en la ventana de diálogo para la ejecución de un constructor
	 * o un método. Para los parámetros de tipo primitivo u objetos que
	 * envuelven a los tipos primitivos se devuelve el valor directamente del
	 * JFormattedTextField, en caso contrario, se obtiene el nombre del objeto
	 * del JComboBox y se extrae el objeto del contenedor. En caso de existir
	 * algún error con los nombre de los objetos se devuelve null.
	 * 
	 * @return array de tipo Object, con los valores de cada parámetro o null en
	 *         caso de que exista algún problema con los nombres de los objetos.
	 */
	public Object[] getArrayObject()
	{
		Object[] retorno = new Object[tiposParametros.length];
		for (int i = 0; i < tiposParametros.length; i++)
		{
			String[] datos = cargador.datosTipo(tiposParametros[i]);
			if (datos[0].equals("char"))
				retorno[i] = ((JFormattedTextField) valorParametros[i]).getText().charAt(0);
			else if (datos[1].equals("yes") && datos[2].equals("no"))
				retorno[i] = ((JFormattedTextField) valorParametros[i]).getValue();
			else
			// Si son arrays u objetos no primitivos
			{
				try
				{
					if (((JComboBox) valorParametros[i]).getSelectedItem().toString().equals("-Escoja un elemento-"))
						return null;
					retorno[i] = cargador.getObjeto(((JComboBox) valorParametros[i]).getSelectedItem().toString());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return retorno;
	}
}