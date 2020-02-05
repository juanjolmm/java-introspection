package tfc.vistaC;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import tfc.ExaminaClase;
import tfc.modelo.Analyzer;
import tfc.vistaC.panels.user.BusquedaPanel;
import tfc.vistaC.panels.user.ContenidoPanel;
import tfc.vistaC.utils.ConsolaArea;
import tfc.vistaC.utils.DragAndDropClase;
import tfc.vistaC.utils.EscapeDialog;
import tfc.vistaC.utils.Pestanias;

/**
 * Es la clase dedicada a la ventana principal de la aplicacion.Sobre dicha
 * ventana se mostraran todos los detalles de las clases que se desea analizar y
 * los objetos instanciados. La organizacion de la ventana se basa en pestañas,
 * en casa pestaña aparece la información de las clases y objetos. Al final de
 * la ventana se tiene una consola por la que se puede ver la salida estandar y
 * el error estandar, dicha consola se puede habilitar o deshabilitar.
 */

public class MainWindow extends JFrame
{
	private static final long	serialVersionUID	= 1944265881196378001L;
	private ConsolaArea			consola;
	private Analyzer			cargador;
	private EscapeDialog		dialogo;
	private JScrollPane			scroll;
	private Pestanias			fichas				= new Pestanias();
	private JPanel				panelInf			= new JPanel(new BorderLayout());
	private JMenuBar			menubar				= new JMenuBar();
	private JMenu				archivo				= new JMenu("Archivo");
	private JMenu				ver					= new JMenu("Ver");
	private JMenu				ayuda				= new JMenu("Ayuda");
	private JMenuItem			abrir				= new JMenuItem("Abrir");
	private JMenuItem			buscar				= new JMenuItem("Buscar");
	private JMenuItem			modClasspath		= new JMenuItem("Modificar classpath");
	private JMenuItem			borrar				= new JMenuItem("Eliminar todo");
	private JMenuItem			salir				= new JMenuItem("Salir");
	private JMenuItem			acerca				= new JMenuItem("Acerca de...");
	private JCheckBoxMenuItem	consolaItem			= new JCheckBoxMenuItem("Ver consola");
	private JMenuItem			contenido			= new JMenuItem("Elementos del contenedor");
	private boolean				consolaVisible		= false;
	private JLabel				labelStatus			= new JLabel("   Analizador de clases");
	private int					coordenadaX;
	private int					coordenadaY;

	/**
	 * Constructor que inicializa la ventana y todo su contenido. Carga la
	 * informacion de una clase si recibe su nombre como parametro, o no lo
	 * hace, en caso contrario.
	 * 
	 * @param carga
	 *            Objeto de la clase Analyzer, dicho objeto contiene toda la
	 *            informacion sobre las clases y objetos que se quieren
	 *            analizar. Todo lo que se quiera saber hay que pedirselo a
	 *            dicho objeto.
	 * @param nombre
	 *            Nombre de la clase a analizar. Dicho parametro puede ser null,
	 *            en dicho caso al cargar la ventana no se obtendra la
	 *            informacion de ninguna clase. Si es diferente de null se
	 *            intentara cargar dicha clase, obteniendo toda la información
	 *            relativa y almacenandola en el cargador.
	 */
	public MainWindow(Analyzer carga, String nombre)
	{
		super("Analizador de clases y objetos Java");
		cargador = carga;
		initMenu();
		initPanelVentana();
		if (nombre != null)
		{
			String clase;
			clase = cargador.abrirClase(new File(nombre));
			if (clase.equals("-1"))
				showInfo("Error al abrir el archivo : " + nombre, "Error", JOptionPane.ERROR_MESSAGE, true);
			else if (clase.equals("-2"))
				showInfo("La clase indicada depende de otra que no se encuentra en el CLASSPATH", "Error", JOptionPane.ERROR_MESSAGE, true);
			else
			{
				InfoPanel panelGeneral = new InfoPanel(MainWindow.this, cargador, clase);
				addPestania(clase, panelGeneral);
				refreshStatus("Clase abierta correctamente");
			}
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 6, 100);
		setSize(800, 600);
	}

	/**
	 * Crea el menu de la ventana con sus opciones e indica los atajos y
	 * controladores de los eventos cuando sean seleccionadas.
	 */
	private void initMenu()
	{
		setJMenuBar(menubar);
		menubar.setPreferredSize(new Dimension(0, 35));
		archivo.add(abrir);
		archivo.add(buscar);
		archivo.add(modClasspath);
		archivo.add(borrar);
		archivo.add(salir);
		archivo.setMnemonic(KeyEvent.VK_A);
		abrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		buscar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		modClasspath.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
		borrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));

		ver.add(contenido);
		ver.add(consolaItem);
		ver.setMnemonic(KeyEvent.VK_V);
		consolaItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		contenido.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));

		ayuda.add(acerca);
		ayuda.setMnemonic(KeyEvent.VK_Y);
		acerca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

		menubar.add(archivo);
		menubar.add(ver);
		menubar.add(ayuda);
		abrir.addActionListener(new ControladorMenu());
		abrir.setActionCommand("abrir");
		buscar.addActionListener(new ControladorMenu());
		buscar.setActionCommand("buscar");
		modClasspath.addActionListener(new ControladorMenu());
		modClasspath.setActionCommand("modCP");
		contenido.addActionListener(new ControladorMenu());
		contenido.setActionCommand("contenido");
		borrar.addActionListener(new ControladorMenu());
		borrar.setActionCommand("borrar");
		salir.addActionListener(new ControladorMenu());
		salir.setActionCommand("salir");
		acerca.addActionListener(new ControladorMenu());
		acerca.setActionCommand("acerca");
		consolaItem.setState(false);
		consolaItem.addActionListener(new ControladorMenu());
		consolaItem.setActionCommand("consolaItem");
	}

	/**
	 * Inicializa la ventana principal de la aplicación. En un principio
	 * contendrá en la parte superior la zona de pestañas que muestra la
	 * informacion sobre clases/instancias (inicialmente vacia hasta la apertura
	 * de una de estas); addConsola(), visualiza una consola en la parte
	 * inferior de la ventana (ver definicion de addConsola()).
	 */
	private void initPanelVentana()
	{
		getContentPane().add(new BarraHerramientas(), BorderLayout.PAGE_START);
		scroll = new JScrollPane();
		scroll.setViewportView(fichas);
		fichas.setTransferHandler(new DragAndDropClase(cargador, this));
		getContentPane().add(scroll, BorderLayout.CENTER);
		fichas.addMouseMotionListener(new ControladorCoordenadas());
		addConsola();
	}

	/**
	 * En la parte inferior de la ventana se muestra una consola que muestra la
	 * salida estandar y el error estandar y un boton (limpiar) encargado de
	 * limpiar su contenido. Dicha consola se puede habilitar o deshabilitar
	 * desde el menú Ver\Ver consola
	 */
	private void addConsola()
	{
		consola = new ConsolaArea(5);
		JScrollPane scroll2 = new JScrollPane();
		scroll2.setViewportView(consola);
		panelInf.add(scroll2, BorderLayout.CENTER);
		JButton clear = new JButton("Limpiar");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				consola.setText("");
			}
		});
		panelInf.add(clear, BorderLayout.SOUTH);
		JPanel contPanelInf = new JPanel();
		contPanelInf.setLayout(new BorderLayout());
		contPanelInf.add(panelInf, BorderLayout.CENTER);
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(BorderFactory.createEtchedBorder());
		statusPanel.setPreferredSize(new Dimension(0, 25));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusPanel.add(labelStatus);
		contPanelInf.add(statusPanel, BorderLayout.SOUTH);
		getContentPane().add(contPanelInf, BorderLayout.SOUTH);
		panelInf.setVisible(false);
	}

	/**
	 * Añade una pestaña nueva con el contenido del panel pasado por parámetro y
	 * selecciona dicha pestaña.
	 * 
	 * @param titulo
	 *            Es el nombre de la clase u objeto cuya informacion se muestra
	 *            en la pestaña.
	 * @param panel
	 *            Es el panel que se añade a la pestaña y que contiene toda la
	 *            informacion sobre el objeto o clase.
	 */
	public void addPestania(String titulo, JPanel panel)
	{
		fichas.addTab(titulo, panel);
		selectPestania(titulo);
	}

	/**
	 * Selecciona la pestaña cuyo título coincide con el título pasado por
	 * parámetro.
	 * 
	 * @param titulo
	 *            Título de la pestaña que se desea seleccionar.
	 * @return Devuelve verdadero si alguna de las pestañas coincide con el
	 *         título pasado por parámetro y ha podido ser seleccionada y false
	 *         en caso contrario.
	 */
	public boolean selectPestania(String titulo)
	{
		int indice = fichas.indexOfTab(titulo);
		if (indice != -1)
		{
			fichas.setSelectedIndex(indice);
			JScrollBar verticalScrollBar = scroll.getVerticalScrollBar();
			verticalScrollBar.setValue(verticalScrollBar.getMinimum());
			return true;
		}
		return false;
	}

	/**
	 * Muestra un diálogo conteniendo el panel pasado por parámetro. Dicho panel
	 * debe tener las siguientes características: Su primer componente debe ser
	 * un JLabel, del que se obtiene el título del diálogo, el segundo
	 * componente debe ser un JPanel, que contiene los elementos principales que
	 * se desea mostrar y el tercer componente, que puede ser opcional, debe ser
	 * un JButton, que será el botón por defecto del JDialog, con lo que si se
	 * presiona la tecla enter se ejecutará la acción asociada a dicho botón.
	 * 
	 * @param panel
	 *            Panel que contiene los elementos necesarios y que se mostrarán
	 *            en el JDialog.
	 */
	public void initDialog(JPanel panel)
	{
		if (dialogo != null)
			dialogo.dispose();

		dialogo = new EscapeDialog(this, ((JLabel) panel.getComponent(0)).getText());
		dialogo.add(panel, BorderLayout.CENTER);
		if (panel.getComponentCount() > 2)
			dialogo.getRootPane().setDefaultButton((JButton) panel.getComponent(2));
		dialogo.setResizable(false);
		dialogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialogo.pack();
		dialogo.setLocation(coordenadaX, coordenadaY);
		dialogo.setVisible(true);
	}

	/**
	 * Cierra el diálogo utilizado para pedir datos al usuario.
	 */
	public void closeDialog()
	{
		dialogo.dispose();
	}

	/**
	 * Muestra al usuario un mensaje con informacion relativa a la ejecucion del
	 * programa, dando mensajes de error, alerta, información...
	 * 
	 * @param mensaje
	 *            Mensaje que se quiere mostrar al usuario.
	 * @param titulo
	 *            Titulo que aparece en el dialogo mostrado.
	 * @param tipo
	 *            Se indica el tipo de mensaje. Puede ser un mensaje de error,
	 *            de informacion, de alerta...
	 * @param modal
	 *            Se indica si el dialogo sera modal o no.
	 */
	public void showInfo(String mensaje, String titulo, int tipo, boolean modal)
	{
		JOptionPane jop = new JOptionPane(mensaje);
		jop.setMessageType(tipo);
		JDialog jdg = jop.createDialog(null, titulo);
		jdg.setModal(modal);
		jdg.setLocation(coordenadaX, coordenadaY);
		jdg.setVisible(true);
	}

	/**
	 * Permite modificar la información de la barra de estado.
	 * 
	 * @param info
	 *            Nueva cadena de texto que se mostrará en la barra de estado.
	 */
	public void refreshStatus(String info)
	{
		labelStatus.setText("   " + info);
	}

	/**
	 * Elimina todas las pestañas de la ventana y el contenido del cargador.
	 */
	public void clearAll()
	{
		fichas.removeAll();
		cargador.eliminarTodo();
	}

	/**
	 * Elimina la pestaña seleccionada de la ventana principal.
	 */
	public void deletePestania()
	{
		fichas.remove(fichas.getSelectedIndex());
	}

	// //////////////////////////////////////////////////////////////////////////
	// /////CLASES INTERNAS AUXILIARES
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * Esta clase se encarga filtrar los archivos con extensión *.class de cada
	 * directorio, asi el usuario cuando quiera abrir una clase solo vera los
	 * archivos .class.
	 */
	class FiltroClass extends FileFilter
	{
		public boolean accept(File file)
		{
			String filename = file.getName();
			if (file.isDirectory())
				return true;
			return filename.matches("(?i).*.class");
		}

		public String getDescription()
		{
			return "*.class";
		}
	}

	/**
	 * Esta clase se encarga filtrar los archivos con extensión *.class de cada
	 * directorio, asi el usuario cuando quiera abrir una clase solo vera los
	 * archivos .class.
	 */
	class FiltroJar extends FileFilter
	{
		public boolean accept(File file)
		{
			String filename = file.getName();
			if (file.isDirectory())
				return true;
			return filename.matches("(?i).*.jar");
		}

		public String getDescription()
		{
			return "Directorios y *.jar";
		}
	}

	/**
	 * @author jjlopez && enriqueV: Clase que implementa una barra de
	 *         herramientas para el usuario.
	 * 
	 */
	class BarraHerramientas extends JToolBar
	{

		private static final long	serialVersionUID	= 3954708101816769513L;

		public BarraHerramientas()
		{
			super("Barra de herramientas");
			setPreferredSize(new Dimension(0, 45));
			setFloatable(false);
			addButtons();
		}

		/**
		 * Añade a la barra de herramientas todos los botones necesarios para la
		 * utilización de la aplicación.
		 */
		private void addButtons()
		{
			JButton button = null;

			button = creaBoton("abrir", "Abrir clase", "Abrir clase desde fichero");
			add(button);

			button = creaBoton("buscar", "Buscar clase", "Buscar clase en classpath");
			add(button);

			button = creaBoton("modCP", "Modificar Classpath", "Añadir un directorio o archivo jar al CLASSPATH");
			add(button);
			
			addSeparator();

			button = creaBoton("contenido", "Ver contenedor", "Ver elementos del contenedor");
			add(button);

			button = creaBoton("borrar", "Borrar todo", "Borrar todo el contenido");
			add(button);
			
			addSeparator();

			button = creaBoton("consolaItem", "Consola", "Mostrar/Ocultar consola");
			add(button);

			button = creaBoton("acerca", "Acerca de...", "Acerca de...");
			add(button);
		}

		/**
		 * Crea cada uno de los botones de la barra de herramientas.
		 * 
		 * @param name
		 *            Nombre de la imagen del boton, dicho nombre debe coincidir
		 *            con la acción asociada al dicho botón.
		 * @param texto
		 *            Texto que aparece al lado del botón.
		 * @param toolTip
		 *            ToolTip que se muestra al dejar el puntero del ratón sobre
		 *            el botón.
		 * @return Devuelve el botón customizado.
		 */
		private JButton creaBoton(String name, String texto, String toolTip)
		{
			JButton button = new JButton(texto);
			button.setActionCommand(name);
			button.setToolTipText(toolTip);
			URL imageURL = ExaminaClase.class.getResource("images/" + name + ".gif");
			button.addActionListener(new ControladorMenu());
			button.setIcon(new ImageIcon(imageURL));
			return button;
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// /////CLASES INTERNAS CONTROLADORAS
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * Clase controladora de los elementos del menu de la ventana principal.
	 * Implementa el interfaz ActionListener.
	 * 
	 * (abrir) Pide al usuario que indique el archivo .class que contiene la
	 * clase que desea analizar. Una vez seleccionado el archivo muestra toda la
	 * información relativa a dicha clase.
	 * 
	 * (buscar) Permite abrir una clase, que debe ser accesible mediante el
	 * classpath, indicando su nombre completo.
	 * 
	 * (borrar) Elimina toda la informacion del analizador y deja la ventana sin
	 * onformacion.
	 * 
	 * (salir) Termina la aplicacion.
	 * 
	 * (consolaItem) Opcion para hacer visible o no la consola.
	 * 
	 * (contenido) Opcion que permite ver todos los objetos y clases que estan
	 * almacenados en el contenedor en un momento dado. Permite seleccionar las
	 * pestañas relativas a dichos elementos o abrirlas si éstas se habian
	 * cerrado.
	 * 
	 * (ayuda) Muestra diversa información sobre la aplicacion.
	 */
	class ControladorMenu implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			if (ev.getActionCommand().equals("abrir"))
			{
				JFileChooser fileOpen = new JFileChooser();
				fileOpen.setAcceptAllFileFilterUsed(false);
				fileOpen.setDialogTitle("Indique la clase");
				fileOpen.setFileFilter(new FiltroClass());
				if (fileOpen.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION)
				{
					File arch = fileOpen.getSelectedFile();
					String nombreClase = cargador.abrirClase(arch);
					if (nombreClase.equals("-1"))
						showInfo("Error al abrir el archivo : " + nombreClase, "Error", JOptionPane.ERROR_MESSAGE, true);
					else if (nombreClase.equals("-2"))
						showInfo("La clase indicada depende de otra que no se encuentra en el CLASSPATH", "Error", JOptionPane.ERROR_MESSAGE, true);
					else
					{
						if (!MainWindow.this.selectPestania(nombreClase))
						{
							InfoPanel panelGeneral = new InfoPanel(MainWindow.this, cargador, nombreClase);
							addPestania(nombreClase, panelGeneral);
							refreshStatus("Clase abierta correctamente");
						}
					}
				}
			}
			else if (ev.getActionCommand().equals("buscar"))
			{
				new BusquedaPanel(MainWindow.this, cargador);
			}
			else if (ev.getActionCommand().equals("borrar"))
			{
				clearAll();
				refreshStatus("Buzón vaciado correctamente");
			}
			else if (ev.getActionCommand().equals("salir"))
			{
				System.exit(0);
			}
			else if (ev.getActionCommand().equals("consolaItem"))
			{
				if (consolaVisible)
				{
					consolaItem.setState(false);
					panelInf.setVisible(false);
					consola.parar();
				}
				else
				{
					consolaItem.setState(true);
					panelInf.setVisible(true);
					consola.comenzar();
				}
				consolaVisible = !consolaVisible;
			}
			else if (ev.getActionCommand().equals("contenido"))
			{
				new ContenidoPanel(MainWindow.this, cargador);
			}
			else if (ev.getActionCommand().equals("modCP"))
			{
				JFileChooser fileOpen = new JFileChooser();
				fileOpen.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fileOpen.setAcceptAllFileFilterUsed(false);
				fileOpen.setDialogTitle("Indique el directorio o archivo jar");
				fileOpen.setFileFilter(new FiltroJar());
				if (fileOpen.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION)
				{
					try
					{
						cargador.addDirToClassPath(fileOpen.getSelectedFile().getAbsolutePath());
						showInfo("Modificación correcta del CLASSPATH", "Modificación del CLASSPATH", JOptionPane.INFORMATION_MESSAGE, true);
					}
					catch (Exception e)
					{
						showInfo("Error al modificar el CLASSPATH", "Error", JOptionPane.ERROR_MESSAGE, true);
					}
				}
			}
			else if (ev.getActionCommand().equals("acerca"))
				showInfo("TFC realizado por: Juan José López y Enrique Viñuales", "TFC Septiembre 2009. UPM. EUI.", JOptionPane.INFORMATION_MESSAGE, true);
		}
	}

	/**
	 * @author jjlopez && EnriqueV: Clase que deriva de MouseAdapter que
	 *         simplemente sirve para actualizar las coordenadas del ratón en
	 *         cada momento. Se utiliza para poder mostrar los dialogos de la
	 *         aplicación en una situación favorable para el usuario.
	 * 
	 */
	class ControladorCoordenadas extends MouseAdapter
	{
		public void mouseMoved(MouseEvent e)
		{
			coordenadaX = e.getXOnScreen() - 60;
			coordenadaY = e.getYOnScreen() - 60;
		}
	}
}
