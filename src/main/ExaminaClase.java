package tfc;

import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import tfc.modelo.Analyzer;
import tfc.vistaC.MainWindow;

/**
 * ExaminaClase: Clase encargada de arrancar la aplicación.
 * 
 */
public class ExaminaClase
{
	/**
	 * Es el programa principal de la aplicación en el cual se crean una ventana
	 * MainWindow, donde se mostrará la información de una clase Java; y un
	 * cargador, almacén de todos estos datos. Inicialmente es posible arrancar
	 * la aplicación con un parámetro, que será una clase de Java, en cuyo caso
	 * se detallará esta en la ventana anterior, o sin parámetro, para comenzar
	 * el análisis de clases posteriormente.
	 * 
	 * @param args
	 *            Nombre de un archivo *.class, clase a analizar por la
	 *            aplicación.
	 */
	public static void main(String[] args)
	{
		int version=Integer.parseInt(System.getProperty("java.version").replace("_", "").replace(".", ""));
		if (version >= 16010)
			try
			{
				System.out.println("Version de JVM: " + System.getProperty("java.version"));
				Toolkit.getDefaultToolkit().setDynamicLayout(true);
				System.setProperty("sun.awt.noerasebackground", "true");
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFrame.setDefaultLookAndFeelDecorated(true);
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				MainWindow ventana;
				Analyzer carga = new Analyzer(100);
				if (args.length != 0)
				{
					ventana = new MainWindow(carga, args[0]);
				}
				else
				{
					ventana = new MainWindow(carga, null);
				}
				ventana.setVisible(true);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Error inesperado, se cierra la aplicación!!!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		else
			JOptionPane.showMessageDialog(null, "Tu VM debe ser, por lo menos, la versión 1.6u10!!!", "Aviso", JOptionPane.WARNING_MESSAGE);
	}
}
