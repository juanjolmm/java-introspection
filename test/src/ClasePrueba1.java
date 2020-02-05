import javax.swing.JFrame;

public class ClasePrueba1
{
	public static final int		numeroS0	= 5;
	public static int			nume		= 5;
	public static int			numeroS2	= 5;
	public static JFrame		ventana;
	public static JFrame[][]	ventana2;
	private int					numero;
	private boolean				bool;
	public boolean				bool2;


	public ClasePrueba1(int i, Integer a12, JFrame[] hola)
	{
		super();
		this.numero = 1;
		this.bool = true;
	}

	public ClasePrueba1()
	{
		super();
		this.numero = 1;
		this.bool = true;
	}

	public ClasePrueba1(int i, float f, double d, long l, byte b, short s, boolean bol, char c, String cadena)
	{
		super();
		this.numero = 1;
		this.bool = true;
	}

	public ClasePrueba1(int[][] arr2, boolean[] arr)
	{
		super();
		this.numero = 1;
		this.bool = true;
		for (int i = 0; i < arr.length; i++)
			System.out.println(arr[i]);
	}

	public int getNumero()
	{
		return numero;
	}

	public void setNumero(int numero)
	{
		this.numero = numero;
	}

	public boolean isBool()
	{
		return bool;
	}

	public void setBool(boolean bool)
	{
		this.bool = bool;
	}

	public static void cambiaS1()
	{
		nume = 10;
		System.out.println("Desde clasePrueba1 cambiaS1");
	}

	public static void restS1()
	{
		nume = 5;
		System.out.println("Desde clasePrueba1 restS1");
	}

	public static int diHola0(JFrame[][] arrayJFrame, long lon)
	{
		nume = 100;
		System.out.println("Desde clasePrueba1 diHola0");
		return 12;
	}

	public static void diHola2(String aSt, int[] aInt, float[] aFlo, char[] aCha, boolean[] aBoo, byte[] aDou)
	{
		nume = 10;
		System.out.println("Desde clasePrueba1 diHola2");
	}
	/*
	 * public static int diHola3(int num, boolean bool) {
	 * System.out.println("Desde clasePrueba diHOla3"); return 10; } public
	 * static int diHola4(int num, boolean bool) { System.out.println("Desde
	 * clasePrueba diHOla3"); return 10; }
	 */
}
