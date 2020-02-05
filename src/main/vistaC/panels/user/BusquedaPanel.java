package tfc.vistaC.panels.user;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import tfc.modelo.Analyzer;
import tfc.vistaC.InfoPanel;
import tfc.vistaC.MainWindow;

/**
 * @author jjlopez enriqueV: Clase que permite al usuario abrir el panel de
 *         información de una clase accesible desde el classpath, indicando su
 *         nombre completo en una caja de texto.
 */
public class BusquedaPanel extends JPanel
{
	private static final long	serialVersionUID	= -6906042786774968526L;
	private MainWindow			ventana;
	private Analyzer			cargador;
	private JTextField			nombreClase			= new JTextField(13);

	/**
	 * Constructor que inicializa un objeto de tipo BusquedaPanel, necesario
	 * para poder abrir el panel de información de una clase accesible desde el
	 * classpath.
	 * 
	 * @param parent
	 *            Ventana principal de la aplicación.
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 */
	public BusquedaPanel(MainWindow parent, Analyzer carga)
	{
		super();
		ventana = parent;
		cargador = carga;
		showBuscarDialog("Buscar clase en el classpath");
	}

	/**
	 * Genera un panel con las características necesarias para mostrar un
	 * diálogo de información de la ventana principal. Dicho panel debe tener un
	 * JLabel oculto para obtener el título del diálogo, un panel donde se
	 * encuentra lo que se quiere mostrar y un botón opcional que pasaría a ser
	 * el botón por defecto del diálogo. En este caso se añaden los tres
	 * elementos, con lo que se mostrará un diálogo con el título igual al texto
	 * del JLabel, un panel principal con un cuadro de texto para que se
	 * introduzca el nombre completo de la clase que se desea buscar, por
	 * último, un botón por defecto que permite realizar dicha busqueda.
	 */
	private void showBuscarDialog(String titulo)
	{
		setLayout(new BorderLayout());
		JLabel etiTitulo = new JLabel(titulo);
		add(etiTitulo, BorderLayout.NORTH);
		etiTitulo.setVisible(false);

		add(initContenido(), BorderLayout.CENTER);

		JButton buscar = new JButton("Buscar en classpath");
		buscar.addActionListener(new ControladorBuscarClase());
		add(buscar, BorderLayout.SOUTH);

		ventana.initDialog(this);
	}

	/**
	 * Devuelve el panel principal que se muestra en el diálogo que sirve para
	 * buscar una clase en el classpath. Dicho panel tiene un cuadro de texto
	 * para que se introduzca el nombre completo de la clase que se desea
	 * buscar.
	 */
	private JPanel initContenido()
	{
		JPanel retorno = new JPanel();
		retorno.setLayout(new BoxLayout(retorno, BoxLayout.X_AXIS));
		JLabel etiqueta = new JLabel("Nombre completo");
		etiqueta.setFont(new Font("Tahoma", Font.BOLD, 12));
		retorno.add(etiqueta);
		retorno.add(nombreClase);
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
	class ControladorBuscarClase implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			if (nombreClase.getText().length() != 0)
				try
				{
					int estado = cargador.abrirClase(nombreClase.getText());
					if (estado == 1)
					{
						InfoPanel panel = new InfoPanel(ventana, cargador, nombreClase.getText());
						ventana.addPestania(nombreClase.getText(), panel);
						ventana.closeDialog();
						ventana.refreshStatus("Clase abierta correctamente");
					}
					else if (estado == -1)
						ventana.showInfo("No se encuentra la clase en el classpath", "Error", JOptionPane.ERROR_MESSAGE, true);
					else
					{
						ventana.closeDialog();
						if (!ventana.selectPestania(nombreClase.getText()))
						{
							InfoPanel panel = new InfoPanel(ventana, cargador, nombreClase.getText());
							ventana.addPestania(nombreClase.getText(), panel);
						}
					}
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
			else
				ventana.showInfo("Indique un nombre diferente", "Aviso", JOptionPane.WARNING_MESSAGE, true);
		}
	}
}
