package tfc.vistaC.panels.user;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import tfc.modelo.Analyzer;
import tfc.vistaC.InfoPanel;
import tfc.vistaC.MainWindow;

/**
 * @author jjlopez enriqueV: Clase que permite al usuario ver el contenido
 *         completo del contenedor. Haciendo doble click sobre cada uno de los
 *         elementos, permite seleccionar la ficha correspondiente, si es que
 *         estaba ya abierta, o abrir la ficha que corresponda y seleccionarla
 *         si la ventana principal no tenía dicha ficha.
 */
public class ContenidoPanel extends JPanel
{
	private static final long	serialVersionUID	= -6906042786774968526L;
	private MainWindow			ventana;
	private Analyzer			cargador;
	private JTable				table				= new JTable();
	private int					filaSelect			= -1;
	private String[][]			datos;

	/**
	 * Constructor que inicializa un objeto de tipo ContenidoPanel, necesario
	 * para poder ver el contenido completo del contenedor.
	 * 
	 * @param parent
	 *            Ventana principal de la aplicación.
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 */
	public ContenidoPanel(MainWindow parent, Analyzer carga)
	{
		super();
		ventana = parent;
		cargador = carga;
		showContenidoDialog("Elementos del contenedor");
	}

	/**
	 * Genera un panel con las características necesarias para mostrar un
	 * diálogo de información de la ventana principal. Dicho panel debe tener un
	 * JLabel oculto para obtener el título del diálogo, un panel donde se
	 * encuentra lo que se quiere mostrar y un botón opcional que pasaría a ser
	 * el botón por defecto del diálogo. En este caso se añaden sólo los dos
	 * primeros elementos, con lo que se mostrará un diálogo con el título igual
	 * al texto del JLabel y un panel principal que contiene un JTable con la
	 * información de todos los elementos del contenedor. Dicha información está
	 * en dos columnas, la primera columna tiene la clave del elemento dentro
	 * del contenedor, que representa el nombre del objeto o el nombre completo
	 * de una clase y en la segunda columna aparece el tipo del objeto
	 * contenido.
	 */
	private void showContenidoDialog(String titulo)
	{
		setLayout(new BorderLayout());
		JLabel etiTitulo = new JLabel(titulo);
		add(etiTitulo, BorderLayout.NORTH);
		etiTitulo.setVisible(false);

		add(initContenido(), BorderLayout.CENTER);

		ventana.initDialog(this);
	}

	/**
	 * Devuelve el panel principal que se muestra en el diálogo que sirve para
	 * ver los elementos contenidos en el contenedor. Dicho panel muestra una
	 * tabla con la información de los elementos del contendor en dos columnas,
	 * la primera columna tiene la clave del elemento dentro del contenedor, que
	 * representa el nombre del objeto o el nombre completo de una clase y en la
	 * segunda columna aparece el tipo del objeto contenido.
	 */
	private JPanel initContenido()
	{
		JPanel retorno = new JPanel(new BorderLayout());

		String[] columnas = { "Nombre", "Tipo" };
		datos = cargador.getContenido();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new ControladorDobleClick());
		ListSelectionModel rowSM = table.getSelectionModel();
		rowSM.addListSelectionListener(new ControladorContenido());
		table.setModel(new DefaultTableModel(datos, columnas) {
			private static final long	serialVersionUID	= 4057081238265769507L;

			public boolean isCellEditable(int row, int col)
			{
				return false;
			}
		});
		JScrollPane scrollpane = new JScrollPane(table);
		retorno.add(scrollpane, BorderLayout.CENTER);

		return retorno;
	}

	// ///////////////////////////////////////////////////////////////
	// //////CLASES INTERNAS CONTROLADORAS DE EVENTOS
	// /////////////////////////////////////////////////////////////

	/**
	 * @author jjlopez && enriqueV: Clase que controla en cada momento que fila
	 *         de la tabla ha sido seleccionada, actualizando así el atributo
	 *         filaSelect.
	 */
	class ControladorContenido implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (!lsm.isSelectionEmpty())
			{
				filaSelect = lsm.getMinSelectionIndex();
			}
			else
			{
				filaSelect = -1;
			}

		}
	}

	/**
	 * @author jjlopez && enriqueV: Clase que controla cuando el usuario hace
	 *         doble click. Cuando el usuario hace doble click sobre una fila,
	 *         permite seleccionar la ficha correspondiente, si es que estaba ya
	 *         abierta, o abrir la ficha que corresponda y seleccionarla si la
	 *         ventana principal no tenía dicha ficha.
	 */
	class ControladorDobleClick extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if (e.getClickCount() == 2)
			{
				try
				{
					String nombre = null;
					if (datos[filaSelect][1].equals("java.lang.Class"))
						nombre = datos[filaSelect][0];
					else
						nombre = datos[filaSelect][1] + ":" + datos[filaSelect][0];
					if (!ventana.selectPestania(nombre))
					{
						InfoPanel panel = new InfoPanel(ventana, cargador, datos[filaSelect][0]);
						ventana.addPestania(nombre, panel);
					}
				}
				catch (Exception e1)
				{
					ventana.showInfo("Error: " + e1.toString(), "Error", JOptionPane.ERROR_MESSAGE, true);
				}
			}
		}
	}
}
