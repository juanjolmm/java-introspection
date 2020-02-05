package tfc.vistaC.utils;

import java.awt.Color;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;

/**
 * @author jjlopez && enriqueV: Clase que permite redirigir la salida est�ndar y
 *         el error est�ndar a un area de texto.
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
	 * �nico constructor que inicializa el �rea de texto con la filas indicadas
	 * por par�metro.
	 * 
	 * @param rows
	 *            N�mero de filas (altura) que tendr� dicha �rea de texto.
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
	 * Desvia la salida est�ndar y el error est�ndar a unos pipeline de los que
	 * se podr� leer para obtener asi la informaci�n.
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
	 * Establece los valores normales para la salida y error est�ndar.
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
	 * Permite leer del pipeline pasado por par�metro una l�nea entera.
	 * 
	 * @param in
	 *            Pipeline del que se desea leer.
	 * @return Devuelve la l�nea leida del pipeline pasado por par�metro.
	 * @throws IOException
	 *             Se lanza una excepci�n si no se lee correctamente del
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
