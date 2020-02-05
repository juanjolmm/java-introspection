package tfc.vistaC.utils;

import java.awt.Color;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;

/**
 * @author jjlopez && enriqueV: Clase que permite redirigir la salida estándar y
 *         el error estándar a un area de texto.
 * 
 */
public class ConsolaArea extends JTextArea implements Runnable
{
	private static final long	serialVersionUID	= 1L;
	private PipedInputStream	pin;
	private PipedInputStream	pin2;
	private PrintStream			estandarOut			= System.out;
	private PrintStream			estandarErr			= System.err;
	private boolean				parado				= false;
	private Thread				reader;
	private Thread				reader2;

	/**
	 * Único constructor que inicializa el área de texto con la filas indicadas
	 * por parámetro.
	 * 
	 * @param rows
	 *            Número de filas (altura) que tendrá dicha área de texto.
	 */
	public ConsolaArea(int rows)
	{
		super();
		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);
		this.setEditable(false);
		this.setRows(rows);
	}

	/**
	 * Desvia la salida estándar y el error estándar a unos pipeline de los que
	 * se podrá leer para obtener asi la información.
	 */
	public void comenzar()
	{
		parado = false;
		pin = new PipedInputStream();
		pin2 = new PipedInputStream();
		try
		{
			PipedOutputStream pout = new PipedOutputStream(pin);
			System.setOut(new PrintStream(pout, true));
			PipedOutputStream pout2 = new PipedOutputStream(pin2);
			System.setErr(new PrintStream(pout2, true));
		}
		catch (Exception ex)
		{
			this.append(ex.toString());
		}
		reader = new Thread(this);
		reader.setDaemon(true);
		reader.start();

		reader2 = new Thread(this);
		reader2.setDaemon(true);
		reader2.start();
	}

	/**
	 * Establece los valores normales para la salida y error estándar.
	 */
	public synchronized void parar()
	{
		parado = true;
		try
		{
			pin.close();
			pin2.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.setOut(estandarOut);
		System.setErr(estandarErr);
	}

	public synchronized void run()
	{
		try
		{
			while (Thread.currentThread() == reader && !parado)
			{
				try
				{
					this.wait(100);
				}
				catch (InterruptedException ie)
				{
				}
				if (pin.available() != 0)
				{
					String input = this.readLine(pin);
					this.append(input);
					this.setCaretPosition(this.getText().length());
				}
			}
			while (Thread.currentThread() == reader2 && !parado)
			{
				try
				{
					this.wait(100);
				}
				catch (InterruptedException ie)
				{
				}
				if (pin2.available() != 0)
				{
					String input = this.readLine(pin2);
					this.append(input);
					this.setCaretPosition(this.getText().length());
				}
			}
		}
		catch (Exception e)
		{
			this.append(e.toString());
		}
	}

	/**
	 * Permite leer del pipeline pasado por parámetro una línea entera.
	 * 
	 * @param in
	 *            Pipeline del que se desea leer.
	 * @return Devuelve la línea leida del pipeline pasado por parámetro.
	 * @throws IOException
	 *             Se lanza una excepción si no se lee correctamente del
	 *             pipeline.
	 */
	public synchronized String readLine(PipedInputStream in) throws IOException
	{
		String input = "";
		do
		{
			int available = in.available();
			if (available == 0)
				break;
			byte b[] = new byte[available];
			in.read(b);
			input = input + new String(b, 0, b.length);
		} while (!input.endsWith("\n") && !input.endsWith("\r\n"));
		return input;
	}
}
