package figuras;

import java.awt.Color;
import java.awt.geom.Line2D;

public class Linea extends Line2D.Double
{
	private static final long serialVersionUID = 1L;
	private Color color = null;
	private String nombre = null;

	public Linea()
	{
		super(10, 20, 564,698);
		color = Color.BLACK;
		nombre = "linea";
	}

	public Linea(double[] coordenadas, Color colorL, String nombreL)
	{
		super(coordenadas[0], coordenadas[1], coordenadas[2], coordenadas[3]);
		color = colorL;
		nombre = nombreL;
	}

	public double getLong()
	{
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	
	public void setLine(double x1, double y1, double x2, double y2)
	{
		super.setLine(x1, y1, x2, y2);
		System.out.println("Linea modificada...");
	}

}
