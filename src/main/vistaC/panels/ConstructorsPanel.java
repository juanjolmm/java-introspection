package tfc.vistaC.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tfc.ExaminaClase;
import tfc.modelo.Analyzer;
import tfc.vistaC.InfoPanel;
import tfc.vistaC.MainWindow;
import tfc.vistaC.panels.parametros.ParametersPanel;

/**
 * @author jjlopez && enriqueV: Clase que permite obtener toda la información de
 *         los constructores de una clase dada.
 * 
 */
public class ConstructorsPanel extends JPanel
{
	private static final long	serialVersionUID		= -6906042786774968526L;
	private static final int	MAXLENGTHCONSTRUCTORES	= 100;
	private MainWindow			ventana;
	private Analyzer			cargador;
	private String				nombre;
	private List<String[]>		infoC;
	private ParametersPanel		panelParam;
	private String[]			tiposParametros;
	private JComponent[]		valorParametros;
	private JTextField			nombreInstancia			= new JTextField(13);
	private int					indiceConstructor;

	/**
	 * Constructor que inicializa un objeto de tipo ConstructorsPanel, necesario
	 * para obtener la información de los constructores de una clase.
	 * 
	 * @param parent
	 *            Ventana principal de la aplicación.
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 * @param nomb
	 *            Nombre de la clase de la que se quiere obtener la información.
	 */
	public ConstructorsPanel(MainWindow parent, Analyzer carga, String nomb)
	{
		super();
		ventana = parent;
		cargador = carga;
		nombre = nomb;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		try
		{
			infoC = cargador.getInfoConstructores(nombre);
			addConstructores();
		}
		catch (Exception e)
		{
			ventana.showInfo("Error al obtener la información de los constructores de " + nombre, "Error", JOptionPane.ERROR_MESSAGE, true);
		}
	}

	/**
	 * Añade toda la información de cada uno de los constructores y un boton
	 * para poder ejecutar cada uno de ellos. La información de cada constructor
	 * es la información sobre los tipos de sus parámetros.
	 */
	private void addConstructores()
	{
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelInfo = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		panel.setBorder(BorderFactory.createTitledBorder("Constructores"));
		Iterator<String[]> it = infoC.iterator();
		int i = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(5, 10, 5, 10);
		constraints.anchor = GridBagConstraints.WEST;

		Font fuente = new Font("Verdana", Font.TRUETYPE_FONT, 12);
		Color azul = Color.getHSBColor(0.60f, 1.0f, 0.7f);
		Color rojo = new Color(150150150);

		while (it.hasNext())
		{
			String[] param = it.next();
			JButton boton = new JButton();
			boton.setToolTipText("Ejecutar el constructor " + i);
			boton.addActionListener(new ControladorConstructor());
			boton.setActionCommand(String.valueOf(i));

			URL imageURL = ExaminaClase.class.getResource("images/new.gif");
			boton.setIcon(new ImageIcon(imageURL));

			constraints.gridx = 0;
			constraints.gridy = i;
			panelInfo.add(boton, constraints);
			JLabel infoParam = new JLabel();
			infoParam.setFont(fuente);
			String texto = "";
			if (param.length != 0)
			{
				infoParam.setForeground(azul);
				for (int j = 0; j < param.length; j++)
				{
					String[] datos = cargador.datosTipo(param[j]);
					if (datos[2].equals("yes"))
					{
						String corchetes = "";
						for (int k = 0; k < Integer.parseInt(datos[4]); k++)
							corchetes = corchetes + "[]";
						texto = texto + datos[3] + corchetes + "; ";
					}
					else
						texto = texto + param[j] + "; ";
				}
				if (texto.length() > MAXLENGTHCONSTRUCTORES)
				{
					infoParam.setToolTipText(texto);
					texto = texto.substring(0, MAXLENGTHCONSTRUCTORES - 1) + "...";
				}
				infoParam.setText(texto);
			}
			else
			{
				infoParam.setForeground(rojo);
				infoParam.setText("void");
			}
			constraints.gridx = 1;
			panelInfo.add(infoParam, constraints);
			i++;
		}
		panel.add(panelInfo, BorderLayout.WEST);
		add(panel);
	}

	/**
	 * Genera un panel con las características necesarias para mostrar un
	 * diálogo de información de la ventana principal. Dicho panel debe tener un
	 * JLabel oculto para obtener el título del diálogo, un panel donde se
	 * encuentra lo que se quiere mostrar y un botón opcional que pasaría a ser
	 * el botón por defecto del diálogo. En este caso se añaden los tres
	 * elementos con lo que se mostrará un diálogo con el título igual al texto
	 * del JLabel, un panel principal con los parámetros del constructor
	 * seleccionado y un botón por defecto para poder ejecutar dicho
	 * constructor.
	 */
	private void showConstructorDialog()
	{
		JPanel panel = new JPanel(new BorderLayout());
		JLabel etiTitulo = new JLabel("Constructor " + indiceConstructor);
		panel.add(etiTitulo, BorderLayout.NORTH);
		etiTitulo.setVisible(false);

		valorParametros = new JComponent[tiposParametros.length];
		panelParam = new ParametersPanel(cargador, ventana, tiposParametros, valorParametros, nombreInstancia, panel);
		panel.add(panelParam, BorderLayout.CENTER);

		JButton terminar = new JButton("Crear Instancia");
		terminar.addActionListener(new ControladorCrearInstancia());
		panel.add(terminar, BorderLayout.SOUTH);

		ventana.initDialog(panel);
	}

	// ///////////////////////////////////////////////////////////////
	// //////CLASES INTERNAS CONTROLADORAS DE EVENTOS
	// /////////////////////////////////////////////////////////////

	/**
	 * @author jjlopez && enriqueV: Clase que controla si el usuario pulsa un
	 *         botón perteneciente a un constructor. Cuando el usuario pulsa el
	 *         botón de un constructor, se selecciona dicho constructor y se
	 *         muestra un diálogo para que se introduzca el nombre de la nueva
	 *         instancia que se quiere crear asi como los parámetros.
	 * 
	 */
	class ControladorConstructor implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			indiceConstructor = Integer.parseInt(ev.getActionCommand());
			tiposParametros = infoC.get(indiceConstructor);
			showConstructorDialog();
		}
	}

	/**
	 * @author jjlopez && enriqueV: Clase que controla, una vez metido un nombre
	 *         de instancia y los parametros están correctamente asignados, si
	 *         el usuario pulsa un botón para ejecutar un constructor. Cuando el
	 *         usuario pulsa el botón, se comprueban los parámetros y el nombre
	 *         asignado y si todo es correcto se ejecuta dicho constructor,se
	 *         guarda en el contenedor el nuevo objeto obtenido y se muestra una
	 *         nueva pestaña con la información del nuevo objeto.
	 */
	class ControladorCrearInstancia implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			Object[] parametros = panelParam.getArrayObject();
			if (nombreInstancia.getText().length() != 0 && !cargador.existeObjeto(nombreInstancia.getText()))
			{
				try
				{
					if (parametros != null)
					{
						Object objeto = cargador.crearInstancia(nombre, parametros);
						if (cargador.meterObjeto(nombreInstancia.getText(), objeto))
						{
							InfoPanel panel = new InfoPanel(ventana, cargador, nombreInstancia.getText());
							ventana.addPestania(nombre + ":" + nombreInstancia.getText(), panel);
							nombreInstancia.setText("");
							ventana.closeDialog();
							ventana.refreshStatus("Constructor ejecutado correctamente");
						}
						else
							ventana.showInfo("Error al guardar el objeto", "Error", JOptionPane.ERROR_MESSAGE, true);
					}
					else
						ventana.showInfo("Revise los parametros", "Error", JOptionPane.WARNING_MESSAGE, true);
				}
				catch (Exception e)
				{
					ventana.showInfo("Error en la creacion: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE, true);
				}
			}
			else
			{
				ventana.showInfo("Indique un nombre diferente", "Aviso", JOptionPane.WARNING_MESSAGE, true);
			}
		}
	}
}
