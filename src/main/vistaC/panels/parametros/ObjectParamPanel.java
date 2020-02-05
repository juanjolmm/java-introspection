package tfc.vistaC.panels.parametros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tfc.modelo.Analyzer;
import tfc.vistaC.InfoPanel;
import tfc.vistaC.MainWindow;

/**
 * @author jjlopez enriqueV: Clase que representa un panel utilizado para poder
 *         crear nuevas instancias de alguna clase. Si se desea crear un nuevo
 *         objeto de una clase, es necesario primero analizar la clase y a
 *         continuación utilizar uno de los constructores para poder ejecutarlo.
 *         Gracias a este panel, si la clase de la que se quiere crear un objeto
 *         no esta en el contenedor, la añade y muestra su información en una
 *         nueva pestaña. Si la clase ya estaba en el contendor muestras su
 *         información y selecciona la pestaña.
 */
public class ObjectParamPanel extends JPanel
{
	private static final long	serialVersionUID	= -6906042786774968526L;
	private Analyzer			cargador;
	private MainWindow			ventana;
	private String				tipo;
	private JComponent			valorParam;

	/**
	 * Constructor que inicializa un objeto de tipo ObjectParamPanel, necesario
	 * para poder crear nuevas instancias de objetos.
	 * 
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 * @param mainV
	 *            Ventana principal de la aplicación.
	 * @param tipoObj
	 *            Especifica el tipo del nuevo objeto que se desea crear.
	 * @param valorParametro
	 *            JComboBox que tiene la información de los objetos del mismo
	 *            tipo que hay en el contenedor.
	 */
	public ObjectParamPanel(Analyzer carga, MainWindow mainV, String tipoObj, JComponent valorParametro)
	{
		super();
		cargador = carga;
		ventana = mainV;
		tipo = tipoObj;
		valorParam = valorParametro;
		initObjectParamPanel();
	}

	/**
	 * Inicializa dicho panel añadiendo un JComboBox que tiene los nombre de los
	 * objetos del contenedor del mismo tipo y añadiendo un botón, que es el
	 * botón por defecto, que sirve para abrir la clase del objeto que se quiere
	 * crear.
	 */
	public void initObjectParamPanel()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(valorParam);
		JButton abreClase = new JButton("Nuevo");
		abreClase.addActionListener(new ControladorNuevoObject());
		add(abreClase);
	}

	// ///////////////////////////////////////////////////////////////
	// //////CLASES INTERNAS CONTROLADORAS DE EVENTOS
	// /////////////////////////////////////////////////////////////

	/**
	 * @author jjlopez && enriqueV: Clase que controla si el usuario pulsa un
	 *         botón para solicitar crear un nuevo objeto de un determinado
	 *         tipo. Cuando el usuario pulsa el botón para crear un objeto, se
	 *         intenta abrir la clase deseada, si no existia en el contenedor,
	 *         se añade y a continuación se selecciona la ficha de la clase para
	 *         que se puedan usar los constructores y crear un nuevo objeto.
	 */
	class ControladorNuevoObject implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			try
			{
				int estado = cargador.abrirClase(tipo);
				if (estado == 1)
				{
					InfoPanel panel = new InfoPanel(ventana, cargador, tipo);
					ventana.addPestania(tipo, panel);
					ventana.closeDialog();
				}
				else if (estado == -1)
					ventana.showInfo("No se encuentra la clase en el classpath", "Error", JOptionPane.ERROR_MESSAGE, true);
				else
				{
					ventana.closeDialog();
					if (!ventana.selectPestania(tipo))
					{
						InfoPanel panel = new InfoPanel(ventana, cargador, tipo);
						ventana.addPestania(tipo, panel);
					}
				}
			}
			catch (Exception e1)
			{
				ventana.showInfo("Error: " + e1.toString(), "Error", JOptionPane.ERROR_MESSAGE, true);
			}
		}
	}
}
