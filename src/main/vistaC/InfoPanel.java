package tfc.vistaC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import tfc.modelo.Analyzer;
import tfc.vistaC.panels.AtributosPanel;
import tfc.vistaC.panels.ConstructorsPanel;
import tfc.vistaC.panels.MetodosPanel;
import tfc.vistaC.utils.DragAndDropClase;

/**
 * InfoPanel es un contenedor que tiene toda la información necesaria sobre
 * clases u objetos. En dicho panel se muestra la información de la siguiente
 * forma: Si el elemento del que muestra la información es una clase, primero
 * muestra la información de los constructores, a continuación de los atributos
 * estáticos y por último la información de los métodos estáticos. Si el
 * elemento es un objeto de cualquier otro tipo de clase, se muestra primero la
 * información de los atributos y a continuación de los métodos. Al final de
 * dicha información se tiene un botón que permite borrar dicho elemento del
 * contenedor y en caso de ser un objeto también se tiene otro botón para ver la
 * información asociada a dicho objeto, dicho botón lo único que hace es
 * ejecutar el método toString() de dicho objeto.
 */
public class InfoPanel extends JPanel
{
	private static final long	serialVersionUID	= -6906042786774968526L;
	private MainWindow			ventana;
	private Analyzer			cargador;
	private String				nombre;
	private JPanel				panelGeneral		= new JPanel();

	/**
	 * Inicializa el panel de dos maneras posibles dependiendo de si se analiza
	 * una clase o una instancia.
	 * 
	 * @param parent
	 *            Ventana principal a la que se añadirán los detalles de una
	 *            clase o instancia.
	 * @param carga
	 *            Objeto Analyzer encargado de obtener toda la información
	 *            referente a una clase y a sus instancias.
	 * @param nomb
	 *            Nombre de la clase o la instancia.
	 */
	public InfoPanel(MainWindow parent, Analyzer carga, String nomb)
	{
		super();
		ventana = parent;
		cargador = carga;
		nombre = nomb;
		setTransferHandler(new DragAndDropClase(cargador, ventana));
		panelGeneral.setLayout(new BoxLayout(panelGeneral, BoxLayout.Y_AXIS));
		try
		{
			int dimensionesArray = cargador.esArray(nombre);
			if (dimensionesArray != -1)
			{
				initPanelInfoArray(dimensionesArray);
			}
			else if (cargador.esClase(nombre))
			{
				JPanel panelMod = new JPanel(new BorderLayout());
				JLabel tipoClase = new JLabel("<html><u>" + cargador.getModificadorClase(nombre) + " class " + nombre + "</u></html>");
				tipoClase.setFont(new Font("Verdana", Font.BOLD, 16));
				tipoClase.setForeground(Color.getHSBColor(0.60f, 1.0f, 0.7f));
				panelMod.add(new JLabel(" "), BorderLayout.NORTH);
				panelMod.add(tipoClase, BorderLayout.CENTER);
				panelMod.add(new JLabel(" "), BorderLayout.SOUTH);
				panelGeneral.add(panelMod);
				List<String[]> infoC = cargador.getInfoConstructores(nombre);
				if (infoC.size() > 0)
					panelGeneral.add(new ConstructorsPanel(ventana, cargador, nombre));
				initInfoPanel();
				initPanelInfoClase();
			}
			else
			{
				initInfoPanel();
				initPanelInfoObjeto();
			}
		}
		catch (Exception e)
		{
			ventana.showInfo("Error al crear el panel de información de " + nombre, "Error", JOptionPane.ERROR_MESSAGE, true);
		}
	}

	/**
	 * Añade en un panel, si los hay, los atributos (estáticos o no estáticos) y
	 * los métodos (estáticos o no estáticos) a la ventana (zona estática o no
	 * estática), dependiendo de si trata de una clase o de un objeto de
	 * cualquier otro tipo.
	 */
	private void initInfoPanel()
	{
		try
		{
			AtributosPanel panelAtributos = null;
			List<String[]> infoA = cargador.getInfoAtributos(nombre);
			if (infoA.size() > 0)
			{
				panelAtributos = new AtributosPanel(ventana, cargador, nombre, infoA);
				panelGeneral.add(panelAtributos);
			}
			List<String[]> infoM = cargador.getInfoMetodos(nombre);
			if (infoM.size() > 0)
				panelGeneral.add(new MetodosPanel(ventana, cargador, nombre, infoM, panelAtributos, infoA.size()));
		}
		catch (Exception e)
		{
			ventana.showInfo("Error al crear el panel de información de " + nombre, "Error", JOptionPane.ERROR_MESSAGE, true);
		}
		add(panelGeneral, BorderLayout.CENTER);
	}

	/**
	 * Añade un botón al panel general que permite borrar una clase que se
	 * encuentra guardada en el contenedor.
	 */
	private void initPanelInfoClase()
	{
		JPanel panelTerminar = new JPanel(new GridLayout(1, 3));
		panelTerminar.add(new JLabel(""));
		JButton terminar = new JButton("Borrar clase");
		terminar.addActionListener(new ControladorBorrar());
		panelTerminar.add(terminar);
		panelTerminar.add(new JLabel(""));
		panelGeneral.add(panelTerminar);
	}

	/**
	 * Para las instancias creadas, este método, añade al panel un botón que
	 * permite destruir el objeto y otro que permite mostrar sus datos.
	 */
	private void initPanelInfoObjeto()
	{
		JPanel panelTerminar = new JPanel(new GridLayout(1, 4));
		panelTerminar.add(new JLabel(""));
		JButton toString = new JButton(nombre + ".toString");
		toString.addActionListener(new ControladorInfoObjeto());
		panelTerminar.add(toString);
		JButton terminar = new JButton("Borrar objeto");
		terminar.addActionListener(new ControladorBorrar());
		panelTerminar.add(terminar);
		panelTerminar.add(new JLabel(""));
		panelGeneral.add(panelTerminar);
	}

	/**
	 * Añade un panel de información sobre el array del que se desea saber sus
	 * características. Se muestra el tipo del array y la longitud de este.
	 * 
	 * @param dimensiones
	 *            Dimensiones del array del que se desea la información.
	 */
	private void initPanelInfoArray(int dimensiones)
	{
		JPanel panelArray = new JPanel(new GridLayout(4, 1));

		panelArray.add(new JLabel(" "));
		try
		{
			String corchetes = "";
			for (int i = 0; i < dimensiones; i++)
				corchetes = corchetes + "[]";
			JLabel infoArray = new JLabel("Array: " + cargador.datosTipo(cargador.getObjeto(nombre).getClass().getName())[3] + corchetes, JLabel.CENTER);
			infoArray.setFont(new Font("Verdana", Font.BOLD, 16));
			panelArray.add(infoArray);

			JLabel longArray = new JLabel("Longitud: " + Array.getLength(cargador.getObjeto(nombre)), JLabel.CENTER);
			longArray.setFont(new Font("Verdana", Font.BOLD, 16));
			panelArray.add(longArray);
		}
		catch (Exception e)
		{
			ventana.showInfo("Error al obtener la información del array." + nombre, "Error", JOptionPane.ERROR_MESSAGE, true);
		}

		JPanel panelTerminar = new JPanel(new GridLayout(1, 2));
		JButton toString = new JButton(nombre + ".toString");
		toString.addActionListener(new ControladorInfoObjeto());
		panelTerminar.add(toString);
		JButton terminar = new JButton("Borrar array");
		terminar.addActionListener(new ControladorBorrar());
		panelTerminar.add(terminar);

		panelArray.add(panelTerminar);

		panelGeneral.add(panelArray);
		add(panelGeneral, BorderLayout.CENTER);
	}

	// ///////////////////////////////////////////////////////////////
	// //////CLASES INTERNAS CONTROLADORAS DE EVENTOS
	// /////////////////////////////////////////////////////////////

	/**
	 * @author jjlopez && EnriqueV: Cuando se pulsa el botón para eliminar una
	 *         clase u objeto, elimina una clase u objeto anteriormente creado y
	 *         a su vez cierra la pestaña en la que se representa su
	 *         información.
	 */
	class ControladorBorrar implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			ventana.deletePestania();
			try
			{
				cargador.eliminar(nombre);
				ventana.refreshStatus("Elemento borrado del contenedor correctamente");
			}
			catch (Exception e)
			{
				ventana.showInfo("Error al eliminar el elemento " + nombre, "Error", JOptionPane.ERROR_MESSAGE, true);
			}
		}
	}

	/**
	 * @author jjlopez && EnriqueV: Cuando se pulsa el botón para ver la
	 *         información asociada a un objeto, permite ver la información
	 *         asociada a dicho objeto.
	 * 
	 */
	class ControladorInfoObjeto implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			try
			{
				ventana.showInfo(cargador.getInfoObjeto(nombre), "Info Objeto: " + nombre, JOptionPane.INFORMATION_MESSAGE, false);
			}
			catch (Exception e)
			{
				ventana.showInfo("Error al obtener la información del elemento " + nombre, "Error", JOptionPane.ERROR_MESSAGE, true);
			}
		}
	}
}
