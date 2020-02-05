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
import tfc.vistaC.panels.user.GuardarPanel;

/**
 * @author jjlopez && enriqueV: Clase que permite obtener toda la informaci�n de
 *         los m�todos de una clase u objeto dada.
 * 
 */
public class MetodosPanel extends JPanel
{
	private static final long	serialVersionUID	= -6906042786774968526L;
	private static final int	MAXLENGTHMETODOS	= 60;
	private MainWindow			ventana;
	private Analyzer			cargador;
	private String				nombre;
	private List<String[]>		infoM;
	private List<String[]>		infoPM;
	private ParametersPanel		panelParam;
	private String[]			tiposParametros;
	private JComponent[]		valorParametros;
	private AtributosPanel		panelAtributos;
	private int					indiceMetodo;
	private int					numAtributos;

	/**
	 * Constructor que inicializa un objeto de tipo MetodosPanel, necesario para
	 * obtener la informaci�n de los m�todos de una clase u objeto.
	 * 
	 * @param parent
	 *            Ventana principal de la aplicaci�n.
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 * @param nomb
	 *            Nombre de la clase de la que se quiere obtener la informaci�n.
	 * @param info
	 *            Informaci�n necesaria sobre todos los m�todos.
	 * @param panelA
	 *            Panel de atributos nesesario para modificar el valor de los
	 *            atributos en caso de que alg�n m�todo modifique estos valores.
	 * @param numA
	 *            N�mero de atributos, es necesario saber este n�mero ya que si
	 *            es cero, panelA es null.
	 */
	public MetodosPanel(MainWindow parent, Analyzer carga, String nomb, List<String[]> info, AtributosPanel panelA, int numA)
	{
		super();
		ventana = parent;
		cargador = carga;
		nombre = nomb;
		infoM = info;
		panelAtributos = panelA;
		numAtributos = numA;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addMetodos();
	}

	/**
	 * A�ade toda la informaci�n sobre los m�todos (est�ticos o no est�ticos)
	 * mostrando cierta informaci�n sobre ellos: modo de definici�n, tipo del
	 * valor de retorno y tipo de los par�metros. Adem�s, cada uno esta ligado a
	 * un bot�n con su nombre que permite ejecutarlo.
	 */
	private void addMetodos()
	{
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelMetodos = new JPanel(new GridBagLayout());
		try
		{
			infoPM = cargador.getInfoParamMetodos(nombre);
			if (cargador.esClase(nombre))
				panel.setBorder(BorderFactory.createTitledBorder("M�todos estaticos"));
			else
				panel.setBorder(BorderFactory.createTitledBorder("M�todos"));

			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			constraints.insets = new Insets(5, 15, 5, 5);
			constraints.gridy = 0;

			JLabel vacio = new JLabel("");
			constraints.gridx = 0;
			panelMetodos.add(vacio, constraints);

			Font negrita = new Font("Verdana", Font.BOLD, 15);
			Font normal = new Font("Verdana", Font.TRUETYPE_FONT, 12);
			Color azul = Color.getHSBColor(0.60f, 1.0f, 0.7f);
			Color rojo = new Color(150150150);

			JLabel nombre = new JLabel("Nombre");
			nombre.setFont(negrita);
			constraints.gridx = 1;
			panelMetodos.add(nombre, constraints);

			JLabel mod = new JLabel("Modificador");
			mod.setFont(negrita);
			constraints.gridx = 2;
			panelMetodos.add(mod, constraints);

			JLabel retorno = new JLabel("Retorno");
			retorno.setFont(negrita);
			constraints.gridx = 3;
			panelMetodos.add(retorno, constraints);

			JLabel paramL = new JLabel("Parametros");
			paramL.setFont(negrita);
			constraints.gridx = 4;
			panelMetodos.add(paramL, constraints);

			Iterator<String[]> it = infoM.iterator();
			int i = 0;
			while (it.hasNext())
			{
				String[] datos = it.next();
				JButton boton = new JButton();
				boton.setToolTipText("Ejecutar " + datos[0]);
				boton.addActionListener(new ControladorMetodo());
				boton.setActionCommand(String.valueOf(i));
				URL imageURL = ExaminaClase.class.getResource("images/method.gif");
				boton.setIcon(new ImageIcon(imageURL));
				constraints.gridx = 0;
				constraints.gridy = i + 1;
				constraints.fill = GridBagConstraints.BOTH;
				panelMetodos.add(boton, constraints);
				constraints.gridx = 1;

				constraints.fill = GridBagConstraints.NONE;

				JLabel nombreM = new JLabel(datos[0]);
				nombreM.setForeground(azul);
				nombreM.setFont(new Font("Verdana", Font.BOLD, 12));
				panelMetodos.add(nombreM, constraints);

				for (int j = 0; j < 3; j++)
				{
					constraints.gridx = j + 2;
					if (j != 2)
					{
						String[] datosM = cargador.datosTipo(datos[j + 1]);
						String texto = "";
						if (datosM[2].equals("yes"))
						{
							String corchetes = "";
							for (int k1 = 0; k1 < Integer.parseInt(datosM[4]); k1++)
								corchetes = corchetes + "[]";
							texto = datosM[3] + corchetes;
						}
						else
							texto = datos[j + 1];

						JLabel infoM = new JLabel(texto);
						infoM.setForeground(azul);
						infoM.setFont(normal);
						panelMetodos.add(infoM, constraints);
					}
					else
					{
						JLabel infoParam = new JLabel();
						infoParam.setFont(normal);
						String[] param = infoPM.get(i);
						String texto = "";
						if (param.length != 0)
						{
							infoParam.setForeground(azul);
							for (int k = 0; k < param.length; k++)
							{
								String[] datosM = cargador.datosTipo(param[k]);
								if (datosM[2].equals("yes"))
								{
									String corchetes = "";
									for (int k1 = 0; k1 < Integer.parseInt(datosM[4]); k1++)
										corchetes = corchetes + "[]";
									texto = texto + datosM[3] + corchetes + "; ";
								}
								else
									texto = texto + param[k] + "; ";
							}
							if (texto.length() > MAXLENGTHMETODOS)
							{
								infoParam.setToolTipText(texto);
								texto = texto.substring(0, MAXLENGTHMETODOS - 1) + "...";
							}
							infoParam.setText(texto);
						}
						else
						{
							infoParam.setForeground(rojo);
							infoParam.setText("void");
						}
						panelMetodos.add(infoParam, constraints);
					}
				}
				i++;
			}
		}
		catch (Exception e)
		{
			ventana.showInfo("Error al obtener la informaci�n de los m�todos de " + nombre, "Error", JOptionPane.ERROR_MESSAGE, true);
		}
		panel.add(panelMetodos, BorderLayout.WEST);
		add(panel);
	}

	/**
	 * Genera un panel con las caracter�sticas necesarias para mostrar un
	 * di�logo de informaci�n de la ventana principal. Dicho panel debe tener un
	 * JLabel oculto para obtener el t�tulo del di�logo, un panel donde se
	 * encuentra lo que se quiere mostrar y un bot�n opcional que pasar�a a ser
	 * el bot�n por defecto del di�logo. En este caso se a�aden los tres
	 * elementos con lo que se mostrar� un di�logo con el t�tulo igual al texto
	 * del JLabel, un panel principal con los par�metros del m�todo seleccionado
	 * y un bot�n por defecto para poder ejecutar dicho m�todo.
	 * 
	 * @param titulo
	 *            Cadena que se asigna a la etiqueta oculta, que sirve de t�tulo
	 *            para el di�logo.
	 */
	private void showMetodosDialog(String titulo)
	{
		JPanel panel = new JPanel(new BorderLayout());
		JLabel etiTitulo = new JLabel(titulo);
		panel.add(etiTitulo, BorderLayout.NORTH);
		etiTitulo.setVisible(false);

		valorParametros = new JComponent[tiposParametros.length];
		panelParam = new ParametersPanel(cargador, ventana, tiposParametros, valorParametros, panel);
		panel.add(panelParam, BorderLayout.CENTER);

		JButton ejecutar = new JButton("Ejecutar");
		ejecutar.addActionListener(new ControladorEjecutarMetodo());
		panel.add(ejecutar, BorderLayout.SOUTH);

		ventana.initDialog(panel);
	}

	private void ejecutaMetodo(Object[] parametros)
	{
		try
		{
			Object obj = cargador.ejecutaMetodo(nombre, infoM.get(indiceMetodo)[0], parametros);
			if (obj != null)
			{
				if (cargador.datosTipo(infoM.get(indiceMetodo)[2])[1].equals("yes") && !(cargador.datosTipo(infoM.get(indiceMetodo)[2])[2].equals("yes")))
					ventana.showInfo("Valor devuelto: " + obj.toString(), "Valor devuelto", JOptionPane.INFORMATION_MESSAGE, true);
				else
					new GuardarPanel(ventana, cargador, obj, infoM.get(indiceMetodo)[0]);
			}
			else if (!infoM.get(indiceMetodo)[2].equals("void"))
				ventana.showInfo("El m�todo ha devuelto null", "Valor devuelto", JOptionPane.INFORMATION_MESSAGE, true);
			if (numAtributos > 0)
				panelAtributos.actualizaValoresAtributos();
		}
		catch (Exception e)
		{
			ventana.showInfo("Error en la ejecuci�n: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE, true);
		}
	}

	// ///////////////////////////////////////////////////////////////
	// //////CLASES INTERNAS CONTROLADORAS DE EVENTOS
	// /////////////////////////////////////////////////////////////

	/**
	 * @author jjlopez && enriqueV: Clase que controla si el usuario pulsa un
	 *         bot�n perteneciente a un m�todo. Cuando el usuario pulsa el bot�n
	 *         de un m�todo, se selecciona dicho m�todo y , en caso de que dicho
	 *         m�todo necesite par�metros, se muestra un di�logo para que se
	 *         introduzcan o se ejecuta el m�todo directamente si no tiene
	 *         par�metros. Si el m�todo devuelve un objeto se muestra un di�logo
	 *         para poder guardar dicho objeto en el contenedor o si devuelve un
	 *         tipo primitivo o objeto de una clase que envuelve a los tipos
	 *         primitivos, se muestra un mensaje con el valor devuelto.
	 */
	class ControladorMetodo implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			indiceMetodo = Integer.parseInt(ev.getActionCommand());
			tiposParametros = infoPM.get(indiceMetodo);
			if (tiposParametros.length != 0)
				showMetodosDialog("Metodo " + infoM.get(indiceMetodo)[0]);
			else
			{
				ejecutaMetodo(new Object[0]);
				ventana.refreshStatus("M�todo ejecutado correctamente");
			}
		}
	}

	/**
	 * @author jjlopez && enriqueV: Clase que controla, una vez metidos los
	 *         parametros correctamente, si el usuario pulsa un bot�n para
	 *         ejecutar un m�todo. Cuando el usuario pulsa el bot�n, se
	 *         comprueban los par�metros y si todo es correcto se ejecuta dicho
	 *         m�todo.Si el m�todo devuelve un objeto se muestra un di�logo para
	 *         poder guardar dicho objeto en el contenedor o si devuelve un tipo
	 *         primitivo o objeto de una clase que envuelve a los tipos
	 *         primitivos, se muestra un mensaje con el valor devuelto.
	 */
	class ControladorEjecutarMetodo implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			Object[] parametros = panelParam.getArrayObject();
			if (parametros == null)
				ventana.showInfo("Revise los parametros", "Aviso", JOptionPane.WARNING_MESSAGE, true);
			else
			{
				ventana.closeDialog();
				ejecutaMetodo(parametros);
				ventana.refreshStatus("M�todo ejecutado correctamente");
			}
		}
	}
}
