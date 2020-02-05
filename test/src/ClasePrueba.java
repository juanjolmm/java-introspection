public class ClasePrueba
{
	public static final int	numeroS0	= 5;
	public static int		nume		= 5;
	public static int		numeroS2	= 5;
	private int				numero;
	private boolean			bool;
	public boolean			bool2;

	public ClasePrueba()
	{
		super();
		this.numero = 1;
		this.bool = true;
	}

	public ClasePrueba(int numero, boolean bool)
	{
		super();
		this.numero = numero;
		this.bool = bool;
	}

	public ClasePrueba(int numero)
	{
		super();
		this.numero = numero;
		this.bool = false;
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
		System.out.println("Desde clasePrueba cambiaS1");
	}

	public static void restS1()
	{
		nume = 5;
		System.out.println("Desde clasePrueba restS1");
	}

	public static int diHola0()
	{
		nume = 100;
		System.out.println("Desde clasePrueba diHola0");
		return 12;
	}

	public static void diHola2(int num, boolean bool, double dol, float flo, char c, String cadena)
	{
		nume = 10;
		System.out.println("Desde clasePrueba diHola2");
	}
	/*
	 * public static int diHola3(int num, boolean bool) {
	 * System.out.println("Desde clasePrueba diHOla3"); return 10; } public
	 * static int diHola4(int num, boolean bool) {
	 * System.out.println("Desde clasePrueba diHOla3"); return 10; }
	 */
}
