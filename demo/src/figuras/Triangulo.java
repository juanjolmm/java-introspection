package figuras;

import java.awt.Color;

public class Triangulo extends Figura
{
	public Triangulo()
	{
		Linea []triangulo={new Linea(new double[]{10,20,500,600},Color.BLACK,"Linea1"),new Linea(new double[]{10,20,400,100},Color.BLACK,"Linea2"),new Linea(new double[]{400,100,500,600},Color.BLACK,"Linea1")};
		try
		{
			setConjuntoL(triangulo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		setNombre("Triangulo");
	}
	
	public Triangulo(Linea[] lineas, String nombreF) throws Exception
	{
		super();
		if(lineas.length>2)
			throw new Exception("Se necesitan 2 lineas para crear el triángulo...");
		
		Linea tercera=null;
		double coordenadas3[]=new double[4];
		if(lineas[0].getX1()==lineas[1].getX1() && lineas[0].getY1()==lineas[1].getY1())
		{
			coordenadas3[0]=lineas[0].getX2();
			coordenadas3[1]=lineas[0].getY2();
			coordenadas3[2]=lineas[1].getX2();
			coordenadas3[3]=lineas[1].getY2();
		}
		else if(lineas[0].getX1()==lineas[1].getX2() && lineas[0].getY1()==lineas[1].getY2())
		{
			coordenadas3[0]=lineas[0].getX2();
			coordenadas3[1]=lineas[0].getY2();
			coordenadas3[2]=lineas[1].getX1();
			coordenadas3[3]=lineas[1].getY1();
		}
		else if(lineas[0].getX2()==lineas[1].getX1() && lineas[0].getY2()==lineas[1].getY1())
		{
			coordenadas3[0]=lineas[0].getX1();
			coordenadas3[1]=lineas[0].getY1();
			coordenadas3[2]=lineas[1].getX2();
			coordenadas3[3]=lineas[1].getY2();
		}
		else if(lineas[0].getX2()==lineas[1].getX2() && lineas[0].getY2()==lineas[1].getY2())
		{
			coordenadas3[0]=lineas[0].getX1();
			coordenadas3[1]=lineas[0].getY1();
			coordenadas3[2]=lineas[1].getX1();
			coordenadas3[3]=lineas[1].getY1();
		}
		else
			throw new Exception("Las 2 líneas deben coincidir en un punto...");
		tercera=new Linea(coordenadas3,Color.BLACK,"tercera");
		
		Linea []triangulo={lineas[0],lineas[1],tercera};
		setConjuntoL(triangulo);
		setNombre(nombreF);
	}
	
	public double getPerimetro()
	{
		Linea tri[]=getConjuntoL();
		return tri[0].getLong()+tri[1].getLong()+tri[2].getLong();
	}
}
