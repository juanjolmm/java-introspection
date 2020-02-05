package visor;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import figuras.Figura;
import figuras.Linea;
import figuras.Triangulo;

public class Visor extends JPanel
{
	private static final long	serialVersionUID	= 1L;
	private int					ancho				= 0;
	private int					alto				= 0;
	private MiCanvas			canvas				= null;

	public Visor(int anchoV, int altoV)
	{
		ancho = anchoV;
		alto = altoV;
		canvas = new MiCanvas();
	}

	public Visor()
	{
		ancho = 800;
		alto = 800;
		canvas = new MiCanvas();
		System.out.println("Visor creado correctamente...");
	}

	public void mostrarLinea(JFrame ventana, Linea linea)
	{
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventana.setSize(ancho, alto);
		ventana.setVisible(true);
		ventana.add(canvas);
		canvas.dibuja(linea);
	}

	public void mostrarLinea(Linea linea)
	{
		JFrame ventana = new JFrame("Visor");
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventana.setSize(ancho, alto);
		ventana.setVisible(true);
		ventana.add(canvas);
		canvas.dibuja(linea);
	}

	public void mostrarFigura(JFrame ventana, Figura figura)
	{
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventana.setSize(ancho, alto);
		ventana.setVisible(true);
		ventana.add(canvas);
		canvas.dibuja(figura);
	}

	public void mostrarFigura(Figura figura)
	{
		JFrame ventana = new JFrame("Visor");
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventana.setSize(ancho, alto);
		ventana.setVisible(true);
		ventana.add(canvas);
		canvas.dibuja(figura);
	}

	public void ejemploVisor()
	{
		JFrame ventana = new JFrame("Visor");
		ventana.setLayout(new BorderLayout());
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventana.setSize(ancho,alto);
		ventana.setVisible(true);
		ventana.add(canvas, BorderLayout.CENTER);
		canvas.dibuja(new Triangulo());
	}

	public int getAncho()
	{
		return ancho;
	}

	public void setAncho(int ancho)
	{
		this.ancho = ancho;
	}

	public int getAlto()
	{
		return alto;
	}

	public void setAlto(int alto)
	{
		this.alto = alto;
	}

	public void colorFondo(Color color)
	{
		canvas.setColorFondo(color);
	}

	// /////////////////////////
	// Clase interna
	// /////////////////////////

	class MiCanvas extends Canvas
	{
		private static final long	serialVersionUID	= 1L;
		private Figura				figura				= null;
		private Linea				linea				= null;
		private boolean				esFigura			= false;
		private Color				colorFondo			= Color.WHITE;

		public MiCanvas()
		{
			super();
			setBackground(colorFondo);
			System.out.println("Canvas creado correctamente...");
		}

		public void setColorFondo(Color color)
		{
			colorFondo = color;
		}

		public void paint(Graphics g)
		{
			g.setColor( colorFondo );
	        g.fillRect( 0,0,(int) this.getSize().getWidth(),(int) this.getSize().getHeight());
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (esFigura)
			{
				Linea[] lineas = figura.getConjuntoL();
				for (int i = 0; i < lineas.length; i++)
				{
					g2.setPaint(lineas[i].getColor());
					g2.draw(lineas[i]);
				}
			}
			else
			{
				g2.setPaint(linea.getColor());
				g2.draw(linea);
			}
		}

		void dibuja(Figura figuraF)
		{
			figura = figuraF;
			esFigura = true;
			System.out.println("Se dibuja una figura...");
			repaint();
		}

		void dibuja(Linea lineaL)
		{
			linea = lineaL;
			esFigura = false;
			System.out.println("Se dibuja una linea...");
			repaint();
		}

	}
}
