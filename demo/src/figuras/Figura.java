package figuras;

import java.awt.Color;

public class Figura
{
	private Linea[]	conjuntoL	= null;
	private String	nombre		= null;
	
	public Figura()
	{
		System.out.println("Figura creada correctamente");
	}

	public Figura(Linea[] lineas, String nombreF) throws Exception
	{
		if (lineas.length == 0)
			throw new Exception("Una figura tiene al menos 1 línea...");
		conjuntoL = lineas;
		nombre = nombreF;
		System.out.println("Figura creada correctamente");
	}

	public Figura(double[][] lineas, String nombreF) throws Exception
	{
		if (lineas.length == 0)
			throw new Exception("Una figura tiene al menos 1 línea...");
		conjuntoL = new Linea[lineas.length];
		for (int i = 0; i < lineas.length; i++)
		{
			if (lineas[i].length < 4)
				throw new Exception("Para una línea se necesitan 4 parametros...");
			double[] coordenadas = new double[4];
			for (int j = 0; j < 4; j++)
			{
				coordenadas[j] = lineas[i][j];
			}
			conjuntoL[i] = new Linea(coordenadas, Color.BLACK, "linea" + i);
		}
		nombre = nombreF;
		System.out.println("Figura creada correctamente");
	}

	public Linea[] getConjuntoL()
	{
		return conjuntoL;
	}
	
	public void setConjuntoL(Linea[] conjuntoL) throws Exception
	{
		if (conjuntoL.length == 0)
			throw new Exception("Una figura tiene al menos 1 línea...");
		this.conjuntoL = conjuntoL;
	}
	
	public Linea getLineaAt(int index)
	{
		return conjuntoL[index];
	}
	
	public int getNumLineas()
	{
		return conjuntoL.length;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
}
