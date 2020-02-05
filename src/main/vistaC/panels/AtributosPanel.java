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

import tfc.ExaminaClase;
import tfc.modelo.Analyzer;
import tfc.vistaC.MainWindow;
import tfc.vistaC.panels.parametros.ParametersPanel;

/**
 * @author jjlopez && enriqueV: Clase que permite obtener toda la información de
 *         los atributos de una clase u objeto dada.
 * 
 */
public class AtributosPanel extends JPanel
{
	private static final long	serialVersionUID	= -6906042786774968526L;
	private static final int	MAXLENGTHATRIBUTOS	= 30;
	private MainWindow			ventana;
	private Analyzer			cargador;
	private String				nombre;
	private List<String[]>		infoA;
	private ParametersPanel		panelParam;
	private String[]			tiposParametros;
	private JComponent[]		valorParametros;
	private JLabel[]			valorAtributos;
	private int					indiceAtributo;

	/**
	 * Constructor que inicializa un objeto de tipo AtributosPanel, necesario
	 * para obtener la información de los atributos de una clase u objeto.
	 * 
	 * @param parent
	 *            Ventana principal de la aplicación.
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 * @param nomb
	 *            Nombre de la clase de la que se quiere obtener la información.
	 * @param info
	 *            Información necesaria sobre todos los atributos.
	 */
	public AtributosPanel(MainWindow parent, Analyzer carga, String nomb, List<String[]> info)
	{
		super();
		ventana = parent;
		cargador = carga;
		nombre = nomb;
		infoA = info;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addAtributos();
	}

	/**
	 * Añade toda la información de cada uno de los atributos y un boton para
	 * poder modificar los que no sean de tipo final. La información de cada
	 * atributo es el modificador, tipo, nombre y valor.
	 */
	private void addAtributos()
	{
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelatributos = new JPanel(new GridBagLayout());
		try
		{
			valorAtributos = new JLabel[infoA.size()];
			if (cargador.esClase(nombre))
				panel.setBorder(BorderFactory.createTitledBorder("Atributos estaticos"));
			else
				panel.setBorder(BorderFactory.createTitledBorder("Atributos"));

			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			constraints.insets = new Insets(5, 15, 5, 5);
			constraints.gridy = 0;

			JLabel vacio = new JLabel("");
			constraints.gridx = 0;
			panelatributos.add(vacio, constraints);

			Font negrita = new Font("Verdana", Font.BOLD, 15);
			Font normal = new Font("Verdana", Font.TRUETYPE_FONT, 12);
			Color azul = Color.getHSBColor(0.60f, 1.0f, 0.7f);

			JLabel mod = new JLabel("Nombre");
			mod.setFont(negrita);
			constraints.gridx = 1;
			panelatributos.add(mod, constraints);

			JLabel tipo = new JLabel("Modificador");
			tipo.setFont(negrita);
			constraints.gridx = 2;
			panelatributos.add(tipo, constraints);

			JLabel nombreA = new JLabel("Tipo");
			nombreA.setFont(negrita);
			constraints.gridx = 3;
			panelatributos.add(nombreA, constraints);

			JLabel valor = new JLabel("Valor");
			valor.setFont(negrita);
			constraints.gridx = 4;
			panelatributos.add(valor, constraints);

			Iterator<String[]> it = infoA.iterator();
			int i = 0;
			while (it.hasNext())
			{
				String[] datos = it.next();
				for (int j = 0; j < 4; j++)
				{
					constraints.gridy = i + 1;
					if (datos[4].equals("Modificar"))
					{
						JButton modificar = new JButton();
						modificar.setToolTipText("Modificar " + datos[0]);
						modificar.addActionListener(new ControladorAtributo());
						modificar.setActionCommand(String.valueOf(i));

						URL imageURL = ExaminaClase.class.getResource("images/atributo.gif");
						modificar.setIcon(new ImageIcon(imageURL));

						constraints.gridx = 0;
						constraints.fill = GridBagConstraints.BOTH;
						panelatributos.add(modificar, constraints);
						constraints.fill = GridBagConstraints.NONE;
					}
					constraints.gridx = j + 1;
					if (j == 2)
					{
						String[] datosT = cargador.datosTipo(datos[j]);
						String texto = "";
						if (datosT[2].equals("yes"))
						{
							String corchetes = "";
							for (int k = 0; k < Integer.parseInt(datosT[4]); k++)
								corchetes = corchetes + "[]";
							texto = texto + datosT[3] + corchetes;
						}
						else
							texto = datos[j];

						JLabel infoA = new JLabel(texto);
						infoA.setFont(normal);
						infoA.setForeground(azul);
						panelatributos.add(infoA, constraints);
					}
					else if (j != 3)
					{
						JLabel infoA = new JLabel(datos[j]);
						if (j == 0)
							infoA.setFont(new Font("Verdana", Font.BOLD, 12));
						else
							infoA.setFont(normal);
						infoA.setForeground(azul);
						panelatributos.add(infoA, constraints);
					}
					else
					{
						String texto = datos[j];
						valorAtributos[i] = new JLabel();
						valorAtributos[i].setForeground(azul);
						valorAtributos[i].setFont(normal);
						if (texto.length() > MAXLENGTHATRIBUTOS)
						{
							valorAtributos[i].setToolTipText(texto);
							texto = texto.substring(0, MAXLENGTHATRIBUTOS - 1) + "...";
						}
						valorAtributos[i].setText(texto);
						panelatributos.add(valorAtributos[i], constraints);
					}
				}
				i++;
			}
		}
		catch (Exception e)
		{
			ventana.showInfo("Error al obtener la información de los atributos de " + nombre, "Error", JOptionPane.ERROR_MESSAGE, true);
		}

		panel.add(panelatributos, BorderLayout.WEST);
		add(panel);
	}

	/**
	 * Actualiza el valor de los atributos por su posible modificación trás la
	 * ejecución de un método, o después de modificar el valor de uno de ellos.
	 */
	public void actualizaValoresAtributos()
	{
		Iterator<String[]> it = infoA.iterator();
		int i = 0;
		while (it.hasNext())
		{
			String[] datos = it.next();
			try
			{
				String texto = cargador.getValorAtributo(nombre, datos[0]);
				if (texto.length() > MAXLENGTHATRIBUTOS)
				{
					valorAtributos[i].setToolTipText(texto);
					texto = texto.substring(0, MAXLENGTHATRIBUTOS) + "...";
				}
				valorAtributos[i].setText(texto);
			}
			catch (Exception e)
			{
				valorAtributos[i].setText("null");
			}
			i++;
		}
	}

	/**
	 * Genera un panel con las características necesarias para mostrar un
	 * diálogo de información de la ventana principal. Dicho panel debe tener un
	 * JLabel oculto para obtener el título del diálogo, un panel donde se
	 * encuentra lo que se quiere mostrar y un botón opcional que pasaría a ser
	 * el botón por defecto del diálogo. En este caso se añaden los tres
	 * elementos con lo que se mostrará un diálogo con el título igual al texto
	 * del JLabel, un panel principal para especificar el nuevo valor del
	 * atributo seleccionado y un botón por defecto para poder modificar dicho
	 * atributo.
	 * 
	 * @param titulo
	 *            Cadena que se asigna a la etiqueta oculta, que sirve de título
	 *            para el diálogo.
	 */
	private void showAtributosDialog(String titulo)
	{
		JPanel panel = new JPanel(new BorderLayout());
		JLabel etiTitulo = new JLabel(titulo);
		panel.add(etiTitulo, BorderLayout.NORTH);
		etiTitulo.setVisible(false);

		valorParametros = new JComponent[tiposParametros.length];
		panelParam = new ParametersPanel(cargador, ventana, tiposParametros, valorParametros, panel);
		panel.add(panelParam, BorderLayout.CENTER);

		JButton modificar = new JButton("Modificar");
		modificar.addActionListener(new ControladorModificarAtributo());
		panel.add(modificar, BorderLayout.SOUTH);

		ventana.initDialog(panel);
	}

	// ///////////////////////////////////////////////////////////////
	// //////CLASES INTERNAS CONTROLADORAS DE EVENTOS
	// /////////////////////////////////////////////////////////////

	/**
	 * @author jjlopez && enriqueV: Clase que controla si el usuario pulsa un
	 *         botón para modificar un atributo. Cuando el usuario pulsa el
	 *         botón para modificar un atributo, se selecciona dicho atributo y
	 *         se muestra un diálogo para que se introduzca el nuevo valor que
	 *         se quiere asignar a dicho atributo.
	 * 
	 */
	class ControladorAtributo implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{

			indiceAtributo = Integer.parseInt(ev.getActionCommand());
			String[] datosA = infoA.get(indiceAtributo);
			tiposParametros = new String[1];
			tiposParametros[0] = datosA[2];
			showAtributosDialog("Atributo :" + datosA[0]);
		}
	}

	/**
	 * @author jjlopez && enriqueV: Clase que controla, una vez metido un nuevo
	 *         valor para un atributo, si el usuario pulsa un botón para
	 *         modificar dicho atributo. Cuando el usuario pulsa el botón, se
	 *         comprueba el nuevo valor y si es correcto se modifica el valor de
	 *         dicho atributo y se modifica la información del atributo para que
	 *         aparezca el nuevo valor.
	 */
	class ControladorModificarAtributo implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			Object[] nuevoValor = panelParam.getArrayObject();
			if (nuevoValor == null)
				ventana.showInfo("Revise los parametros", "Aviso", JOptionPane.WARNING_MESSAGE, true);
			else
			{
				try
				{
					cargador.setValorAtributo(nombre, infoA.get(indiceAtributo)[0], nuevoValor[0]);
					actualizaValoresAtributos();
					ventana.closeDialog();
					ventana.refreshStatus("Atributo modificado correctamente");
				}
				catch (Exception e)
				{
					ventana.showInfo("Error al modificar el atributo: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE, true);
				}
			}
		}
	}
}
