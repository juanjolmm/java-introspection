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
 * InfoPanel es un contenedor que tiene toda la informaci�n necesaria sobre
 * clases u objetos. En dicho panel se muestra la informaci�n de la siguiente
 * forma: Si el elemento del que muestra la informaci�n es una clase, primero
 * muestra la informaci�n de los constructores, a continuaci�n de los atributos
 * est�ticos y por �ltimo la informaci�n de los m�todos est�ticos. Si el
 * elemento es un objeto de cualquier otro tipo de clase, se muestra primero la
 * informaci�n de los atributos y a continuaci�n de los m�todos. Al final de
 * dicha informaci�n se tiene un bot�n que permite borrar dicho elemento del
 * contenedor y en caso de ser un objeto tambi�n se tiene otro bot�n para ver la
 * informaci�n asociada a dicho objeto, dicho bot�n lo �nico que hace es
 * ejecutar el m�todo toString() de dicho objeto.
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
	 *            Ventana principal a la que se a�adir�n los detalles de una
	 *            clase o instancia.
	 * @param carga
	 *            Objeto Analyzer encargado de obtener toda la informaci�n
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
			ventana.showInfo("Error al crear el panel de informaci�n de " + nombre, "Error", JOptionPane.ERROR_MESSAGE, true);
		}
	}

	/**
	 * A�ade en un panel, si los hay, los atributos (est�ticos o no est�ticos) y
	 * los m�todos (est�ticos o no est�ticos) a la ventana (zona est�tica o no
	 * est�tica), dependiendo de si trata de una clase o de un objeto de
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
			ventana.showInfo("Error al crear el panel de informaci�n de " + nombre, "Error", JOptionPane.ERROR_MESSAGE, true);
		}
		add(panelGeneral, BorderLayout.CENTER);
	}

	/**
	 * A�ade un bot�n al panel general que permite borrar una clase que se
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
	 * Para las instancias creadas, este m�todo, a�ade al panel un bot�n que
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
	 * A�ade un panel de informaci�n sobre el array del que se desea saber sus
	 * caracter�sticas. Se muestra el tipo del array y la longitud de este.
	 * 
	 * @param dimensiones
	 *            Dimensiones del array del que se desea la informaci�n.
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
			ventana.showInfo("Error al obtener la informaci�n del array." + nombre, "Error", JOptionPane.ERROR_MESSAGE, true);
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
	 * @author jjlopez && EnriqueV: Cuando se pulsa el bot�n para eliminar una
	 *         clase u objeto, elimina una clase u objeto anteriormente creado y
	 *         a su vez cierra la pesta�a en la que se representa su
	 *         informaci�n.
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
	 * @author jjlopez && EnriqueV: Cuando se pulsa el bot�n para ver la
	 *         informaci�n asociada a un objeto, permite ver la informaci�n
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
				ventana.showInfo("Error al obtener la informaci�n del elemento " + nombre, "Error", JOptionPane.ERROR_MESSAGE, true);
			}
		}
	}
}
