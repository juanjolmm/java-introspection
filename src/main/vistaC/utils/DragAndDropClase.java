package tfc.vistaC.utils;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;
import tfc.modelo.Analyzer;
import tfc.vistaC.InfoPanel;
import tfc.vistaC.MainWindow;

/**
 * @author jjlopez && enriqueV: Clase que permite controlar que se arrastren
 *         ficheros .class a la aplicación para abrir su ficha de información.
 */
public class DragAndDropClase extends TransferHandler
{
	private static final long	serialVersionUID	= -6955799544962841975L;
	private Analyzer			cargador;
	private MainWindow			ventana;

	/**
	 * Unico constructor que crea un objeto de tipo DragAndDropClase, necesario
	 * para controlar el aarastrar fucheros a la aplicación.
	 * 
	 * @param carga
	 *            Analizador y contenedor de clases y objetos.
	 * @param vent
	 *            Ventana principal de la aplicación.
	 */
	public DragAndDropClase(Analyzer carga, MainWindow vent)
	{
		super();
		cargador = carga;
		ventana = vent;
	}

	public boolean canImport(TransferHandler.TransferSupport support)
	{
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean importData(TransferHandler.TransferSupport support)
	{
		Transferable t = support.getTransferable();
		try
		{
			java.util.List<File> ficheros = (java.util.List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
			for (File f : ficheros)
			{
				if (f.getName().substring(f.getName().lastIndexOf("."), f.getName().length()).equalsIgnoreCase(".class"))
				{
					String nombreClase = cargador.abrirClase(f);
					if(nombreClase.equals("-1"))
						ventana.showInfo("Error al abrir el archivo : " + nombreClase, "Error", JOptionPane.ERROR_MESSAGE, true);
					else if(nombreClase.equals("-2"))
						ventana.showInfo("La clase indicada depende de otra que no se encuentra en el CLASSPATH", "Error", JOptionPane.ERROR_MESSAGE, true);
					else
					{
						if (!ventana.selectPestania(nombreClase))
						{
							InfoPanel panelGeneral = new InfoPanel(ventana, cargador, nombreClase);
							ventana.addPestania(nombreClase, panelGeneral);
							ventana.refreshStatus("Clase abierta correctamente");
						}
					}
				}
				else
					ventana.showInfo("Solo se permite abrir archivos .class", "Aviso", JOptionPane.WARNING_MESSAGE, true);
			}

		}
		catch (Exception e)
		{
			return false;
		}

		return true;
	}
}