package tfc.modelo;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

/**
 * @author jjlopez && enriqueV. La clase ContainerTools es la clase encargada,
 *         principalmente, de almacenar los objetos y clases que se analizarán
 *         durante la ejecución de la aplicación. Contiene todos los métodos
 *         necesarios para la gestión del contenedor de elementos, asi como
 *         guardar elementos, extraerlos utilizando diversos cirterios, saber si
 *         un elemento se encuentra en el contenedor, etc. Para guardar los
 *         elementos se ha optado por utilizar un objeto de tipo HashMap, el
 *         número de elementos que pueden guardarse se especifica mediante un
 *         parámetro de tipo int en el constructor. Dicha clase es solo
 *         accesible para el resto de clases del paquete "modelo" y ninguno de
 *         sus métodos es visible desde fuera del paquete.
 */
class ContainerTools
{
	/**
	 * El atributo contenedor es el objeto que guarda todos los elementos sobre
	 * los que se desea obtener información.
	 * 
	 * El atributo classPath contiene todos los directorios donde se encuentran
	 * las clases que se desean analizar.
	 * 
	 * El atributo loader es el cargador que tiene esos directorios.
	 */
	private Map<String, Object>	contenedor;
	private ArrayList<URL>		classPath	= new ArrayList<URL>();
	private ClassLoader			loader		= new URLClassLoader(new URL[0]);

	/**
	 * Único constructor de la clase. Inicializa un objeto de tipo
	 * ContainerTools indicando el número de elementos que podrá almacenar el
	 * contenedor.
	 * 
	 * @param cantidad
	 *            Parámetro de tipo int que indica el número de elementos que
	 *            podrá almacenar el contenedor.
	 */
	ContainerTools(int cantidad)
	{
		contenedor = new HashMap<String, Object>(cantidad);
	}

	/**
	 * Comprueba si en el contenedor existe una clave que coincida con el nombre
	 * pasado por parámetro. En caso de no existir se lanza una excepción.
	 * 
	 * @param nombre
	 *            Nombre del elemento sobre el que se quiere saber si pertenece
	 *            al contenedor.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepción.
	 */
	void comprobarNombre(String nombre) throws Exception
	{
		if (!contenedor.containsKey(nombre))
			throw new Exception("comprobarNombre:El elemento " + nombre + " no se encuentra en el contenedor.");
	}

	/**
	 * Se busca una clave en el contenedor que coincida con el nombre pasado por
	 * parámetro y se devuelve el objeto de tipo Class correspondiente al objeto
	 * asociado a dicha clave.
	 * 
	 * @param nombre
	 *            Nombre del elemento sobre el que se quiere obtener la
	 *            información.
	 * @return Devuelve un objeto de tipo Class, utilizado para obtener toda la
	 *         información necesaria.
	 */
	Class<?> obtenClase(String nombre)
	{
		Object obj = contenedor.get(nombre);
		if (obj instanceof Class<?>)
		{
			return (Class<?>) obj;
		}
		return obj.getClass();
	}

	/**
	 * Indicando una cadena que representa un directorioo un archivo jar, añade
	 * dicho directorio o archivo jar al CLASSPATH en tiempo de ejecución.
	 * 
	 * @param newDir
	 *            Nuevo directorio o archivo jar para añadir al CLASSPATH
	 * @throws Exception
	 *             Si la cadena pasada por parámetro no se corresponde con un
	 *             directorio o archivo jar real.
	 */
	void addDirToClassPath(String newDir) throws Exception
	{
		URL dirPath = new File(newDir).toURI().toURL();
		if (!classPath.contains(dirPath))
		{
			classPath.add(dirPath);
			loader = URLClassLoader.newInstance(classPath.toArray(new URL[classPath.size()]));
		}
	}

	/**
	 * Permite guardar una clase en el contenedor a partir de un archivo.
	 * 
	 * @param pathClase
	 *            Fichero .class que representa la clase que se desea guardar en
	 *            el contenedor.
	 * @return Devuelve una cadena con el nombre completo de la clase si toda va
	 *         bien. Devuelve una cadena con un -1 si el parámetro no es
	 *         correcto. Devuelve una cadena con un -2 si no se encuentra alguna
	 *         clase necesaria.
	 * @throws Exception
	 *             En caso de existir algún problema al guardar la clase se
	 *             lanza una excepción.
	 */
	String abrirClase(File pathClase)
	{
		ClassParser parser;
		JavaClass clase = null;
		String nombreClase = null;
		try
		{
			parser = new ClassParser(pathClase.getCanonicalPath());
			clase = parser.parse();
			nombreClase = clase.getClassName();
			String dirName=null;
			if(nombreClase.contains("."))	//Si la clase pertenece a un paquete.
				dirName = pathClase.getCanonicalPath().substring(0, pathClase.getCanonicalPath().lastIndexOf(nombreClase.substring(0, nombreClase.indexOf("."))));
			else
				dirName = pathClase.getCanonicalPath().substring(0, pathClase.getCanonicalPath().lastIndexOf(nombreClase));
			addDirToClassPath(dirName);
		}
		catch (Exception e)
		{
			return "-1";
		}
		Class<?> claseNueva = null;
		try
		{
			claseNueva = Class.forName(nombreClase, true, loader);
		}
		catch (Exception e)
		{
			return "-2";
		}

		if (!contenedor.containsKey(nombreClase))
			contenedor.put(nombreClase, claseNueva);
		return nombreClase;
	}

	/**
	 * Permite guardar una clase en el contenedor a partir del nombre de la
	 * clase. Dicha clase debe ser accesible desde el CLASSPATH.
	 * 
	 * @param nombre
	 *            Nombre completo de la clase que se desea guardar en el
	 *            contenedor.
	 * @return Si la clase que se desea guardar ya existe en el contenedor,
	 *         devuelve 0, si se mete satisfactoriamente la clase en el
	 *         contenedor, devuelve 1 y si hay un error al intentar guardar la
	 *         clase especificada, devuelve -1.
	 */
	int abrirClase(String nombre)
	{
		if (contenedor.containsKey(nombre))
			return 0;
		try
		{
			contenedor.put(nombre, Class.forName(nombre, true, loader));
		}
		catch (ClassNotFoundException e)
		{
			return -1;
		}
		return 1;
	}

	/**
	 * Mete el objeto pasado por parámetro en el contenedor asociado a la clave
	 * pasada por parámetro. Si se mete correctamente devuelve verdadero y si la
	 * clave ya existe en el contenedor, no se mete el objeto y devuelve falso.
	 * 
	 * @param nombre
	 *            Clave del nuevo elemento a introducir.
	 * @param obj
	 *            Objeto a introducir en el contenedor.
	 * @return Devuelve true si el objeto se mete en el contenedor con la clave
	 *         indicada y false si el nombre ya existe en el contenedor.
	 * 
	 */
	boolean meterObjeto(String nombre, Object obj)
	{
		if (!contenedor.containsKey(nombre))
		{
			contenedor.put(nombre, obj);
			return true;
		}
		return false;
	}

	/**
	 * Devuelve una cadena representativa del elemento obtenido del contenedor.
	 * 
	 * @param nombre
	 *            Nombre del elemento sobre el que se quiere obtener la
	 *            información.
	 * @return Devuelve una cadena representativa del objeto obtenido del
	 *         contenedor.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepción.
	 */
	String getInfoObjeto(String nombre) throws Exception
	{
		comprobarNombre(nombre);
		return contenedor.get(nombre).toString();
	}

	/**
	 * Devuelve una cadena representativa de los modificadores de la clase
	 * pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre de la clase sobre la que se quiere obtener la
	 *            información.
	 * @return Devuelve una cadena representativa de los modificadores de la
	 *         clase pasada por parámetro.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor, o el elemento no sea un objeto de
	 *             tipo Class, se lanza una excepción.
	 */
	String getModificadorClase(String nombre) throws Exception
	{
		if (esClase(nombre))
			return Modifier.toString(((Class<?>) contenedor.get(nombre)).getModifiers());
		throw new Exception("getTipoClase:El elemento " + nombre + " no es un objeto de tipo Class.");
	}

	/**
	 * Comprueba la existencia de una clave en el contenedor.
	 * 
	 * @param nombre
	 *            Clave que se desea buscar en el contenedor.
	 * @return Devuelve verdadero si dicha clave se encuentra en el contenedor y
	 *         falso en caso contrario.
	 */
	boolean existeObjeto(String nombre)
	{
		if (contenedor.containsKey(nombre))
			return true;
		return false;
	}

	/**
	 * Devuelve el objeto, guardado en el contenedor, asociado a la clave pasada
	 * por parámetro.
	 * 
	 * @param nombre
	 *            Clave asociada al objeto que se desea obtener.
	 * @return Devuelve el objeto asociado a la clave pasada por parámetro.
	 * @throws Exception
	 *             Si la clave pasada por parámetro no se encuentra en el
	 *             contenedor se lanza una excepción.
	 */
	Object getObjeto(String nombre) throws Exception
	{
		comprobarNombre(nombre);
		return contenedor.get(nombre);
	}

	/**
	 * Devuelve en un vector, las interfaces que implementa el objeto pasado por
	 * parámetro.
	 * 
	 * @param obj
	 *            Objeto del que se quiere saber que interfaces implementa.
	 * @return Devuelve que implementa el objeto cuya clave, en el contenedor,
	 *         es el nombre pasado por parámetro.
	 */
	Vector<String> getInterfaces(Object obj)
	{
		Vector<String> retorno = new Vector<String>();
		Class<?>[] interfaces = obj.getClass().getInterfaces();
		for (Class<?> m : interfaces)
		{
			retorno.add(m.getName());
		}
		return retorno;
	}

	/**
	 * Devuelve en un vector, el nombre la las clases de las que desciende el
	 * objeto pasado por parámetro.
	 * 
	 * @param obj
	 *            Objeto del que se quiere saber sus superclases.
	 * @return Devuelve el nombre la las clases de las que desciende el objeto
	 *         cuya clave, en el contenedor, es el nombre pasado por parámetro.
	 */
	Vector<String> getSuperClases(Object obj)
	{
		Vector<String> retorno = new Vector<String>();
		Class<?> superClase = obj.getClass().getSuperclass();
		while (superClase != null)
		{
			String nombreS = superClase.getName();
			retorno.add(nombreS);
			try
			{
				superClase = Class.forName(nombreS, true, loader).getSuperclass();
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		return retorno;
	}

	/**
	 * Devuelve un objeto de tipo Vector con los objetos, almacenados en el
	 * contenedor, que son del tipo indicado por parámetro. También aparecen en
	 * el vector aquellos objetos que desciendan del tipo indicado o , en el
	 * caso de que el tipo haga referencia a una interfaz, los objetos que
	 * implementen dicha interfaz.
	 * 
	 * @param tipo
	 *            Nombre del tipo del que se desea saber que objetos, que se
	 *            encuentran en el contenedor, son de dicho tipo, desciende de
	 *            dicho tipo, o en el caso de que el tipo haga referencia a una
	 *            interfaz, aquellos objetos que implementen dicha interfaz.
	 * @return Devuelve un objeto de tipo Vector con los nombres de los objetos
	 *         guardados en el contenedor que son de dicho tipo, que descienden
	 *         de dicho tipo, o en el caso de que el tipo haga referencia a una
	 *         interfaz, el nombre de aquellos objetos que implementen dicha
	 *         interfaz.
	 */
	Vector<String> getObjetosTipo(String tipo)
	{
		Vector<String> listaObj = new Vector<String>();
		Iterator<Entry<String, Object>> it = contenedor.entrySet().iterator();
		while (it.hasNext())
		{
			Entry<String, Object> e = it.next();
			if (e.getValue().getClass().getName().equals(tipo) || getSuperClases(e.getValue()).contains(tipo) || getInterfaces(e.getValue()).contains(tipo))
				listaObj.add(e.getKey());
		}
		return listaObj;
	}

	/**
	 * Crea e introduce en el contenedor un nuevo array del tipo indicado y con
	 * los componentes indicados. Si el tipo del array es primitivo o de
	 * cualquier otra clase que envuelve a los tipo primitivos, como Integer a
	 * int, los datos del array van como datos de tipo String en el parámetro
	 * componentes, en caso contrario, si el tipo del array no es primitivo o si
	 * es un array de mas de una dimensión, en el parámetro componentes van las
	 * claves de los objetos, guardados en el contenedor, que formarán parte del
	 * nuevo array.
	 * 
	 * @param nombreArray
	 *            Nombre del array que se va a crear.
	 * @param tipoDatos
	 *            Tipo del array.
	 * @param componentes
	 *            Array de tipo cadena con la siguiente información, si el array
	 *            es de tipo primitivo tiene los valores a introducir en el
	 *            array, si el array no es de tipo primitivo, almacena los
	 *            nombres de los objetos que serán los elementos del array y que
	 *            deben estar almacenados en el contenedor.
	 * @return Devuelve true si el array es creado y metido correctamente en el
	 *         contenedor y false en caso contrario.
	 * @throws Exception
	 *             Si los nombres pasados en el array componentes, en caso de
	 *             ser un array de tipo no primitivo, no existen en el
	 *             contenedor se lanza una excepción. También salta una
	 *             excepción si los objetos indicados no son del tipo del array.
	 */
	boolean meterArray(String nombreArray, String tipoDatos, String[] componentes) throws Exception
	{
		Object newArray = null;
		if (tipoDatos.equals("int"))
		{
			newArray = new int[componentes.length];
			for (int i = 0; i < componentes.length; i++)
				Array.setInt(newArray, i, Integer.parseInt(componentes[i]));
		}
		else if (tipoDatos.equals("float"))
		{
			newArray = new float[componentes.length];
			for (int i = 0; i < componentes.length; i++)
				Array.setFloat(newArray, i, Float.parseFloat(componentes[i]));
		}
		else if (tipoDatos.equals("double"))
		{
			newArray = new double[componentes.length];
			for (int i = 0; i < componentes.length; i++)
				Array.setDouble(newArray, i, Double.parseDouble(componentes[i]));
		}
		else if (tipoDatos.equals("long"))
		{
			newArray = new long[componentes.length];
			for (int i = 0; i < componentes.length; i++)
				Array.setLong(newArray, i, Long.parseLong(componentes[i]));
		}
		else if (tipoDatos.equals("boolean"))
		{
			newArray = new boolean[componentes.length];
			for (int i = 0; i < componentes.length; i++)
				Array.setBoolean(newArray, i, Boolean.parseBoolean(componentes[i]));
		}
		else if (tipoDatos.equals("byte"))
		{
			newArray = new byte[componentes.length];
			for (int i = 0; i < componentes.length; i++)
				Array.setByte(newArray, i, Byte.parseByte(componentes[i]));
		}
		else if (tipoDatos.equals("short"))
		{
			newArray = new short[componentes.length];
			for (int i = 0; i < componentes.length; i++)
				Array.setShort(newArray, i, Short.parseShort(componentes[i]));
		}
		else if (tipoDatos.equals("char"))
		{
			newArray = new char[componentes.length];
			for (int i = 0; i < componentes.length; i++)
				Array.setChar(newArray, i, (char) componentes[i].charAt(0));
		}
		else if (tipoDatos.equals("String"))
		{
			newArray = (String[]) componentes;
		}
		else
		{
			newArray = Array.newInstance(Class.forName(tipoDatos, true, loader), componentes.length);
			if (tipoDatos.equals("java.lang.Integer"))
			{
				for (int i = 0; i < componentes.length; i++)
					Array.set(newArray, i, new Integer(Integer.parseInt(componentes[i])));
			}
			else if (tipoDatos.equals("java.lang.Float"))
			{
				for (int i = 0; i < componentes.length; i++)
					Array.set(newArray, i, new Float(Float.parseFloat(componentes[i])));
			}
			else if (tipoDatos.equals("java.lang.Double"))
			{
				for (int i = 0; i < componentes.length; i++)
					Array.set(newArray, i, new Double(Double.parseDouble(componentes[i])));
			}
			else if (tipoDatos.equals("java.lang.Long"))
			{
				for (int i = 0; i < componentes.length; i++)
					Array.set(newArray, i, new Long(Long.parseLong(componentes[i])));
			}
			else if (tipoDatos.equals("java.lang.Byte"))
			{
				for (int i = 0; i < componentes.length; i++)
					Array.set(newArray, i, new Byte(Byte.parseByte(componentes[i])));
			}
			else if (tipoDatos.equals("java.lang.Short"))
			{
				for (int i = 0; i < componentes.length; i++)
					Array.set(newArray, i, new Short(Short.parseShort(componentes[i])));
			}
			else if (tipoDatos.equals("java.lang.Boolean"))
			{
				for (int i = 0; i < componentes.length; i++)
					Array.set(newArray, i, new Boolean(Boolean.parseBoolean(componentes[i])));
			}
			else if (tipoDatos.equals("java.lang.Character"))
			{
				for (int i = 0; i < componentes.length; i++)
					Array.set(newArray, i, new Character(componentes[i].charAt(0)));
			}
			else
			// Si no son objetos de tipo primitivo
			{
				for (int i = 0; i < componentes.length; i++)
				{
					comprobarNombre(componentes[i]);
					Object aMeter = contenedor.get(componentes[i]);
					if (aMeter.getClass().getName().equals(tipoDatos) || getSuperClases(aMeter).contains(tipoDatos) || getInterfaces(aMeter).contains(tipoDatos))
						Array.set(newArray, i, aMeter);
					else
						throw new Exception("meterArray: No coinciden los tipos.");
				}
			}
		}
		return meterObjeto(nombreArray, newArray);
	}

	/**
	 * Determina si el nombre pasado por parametro hace referencia a un objeto,
	 * del contenedor, de tipo Class, en cuyo caso devuelve verdadero, o a un
	 * objeto, del contenedor, de otro cualquier tipo de clase, en cuyo caso
	 * devuelve false.
	 * 
	 * @param nombre
	 *            Nombre del elemento sobre el que se quiere obtener la
	 *            información.
	 * @return Devuelve verdadero si el nombre pasado por parametro es una
	 *         instancia de tipo Class y falso si el nombre se refiere a un
	 *         objeto instancia de cualquier otro tipo de clase.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepción.
	 */
	boolean esClase(String nombre) throws Exception
	{
		comprobarNombre(nombre);
		Object obj = contenedor.get(nombre);
		if ((obj instanceof Class<?>))
		{
			return true;
		}
		return false;
	}

	/**
	 * Método que determina si un elemento del contenedor indicado por su clave
	 * es un array o no.
	 * 
	 * @param nombre
	 *            Nombre del elemento del contenedor del que se desea saber si
	 *            es un array.
	 * @return Devuelve -1 si el elemento pasado por parámetro no es un array y
	 *         en caso de que sea un array devuelve la dimendión de este.
	 * @throws Exception
	 *             Se lanza una excepción en caso de que el nombre pasado por
	 *             parámetro no haga referencia a ningún elemento del array.
	 */
	int esArray(String nombre) throws Exception
	{
		comprobarNombre(nombre);
		if (contenedor.get(nombre).getClass().isArray())
			return Integer.parseInt(datosTipo(contenedor.get(nombre).getClass().getName())[4]);
		return -1;
	}

	/**
	 * Devuelve un array de tipo String con todos los datos de interes a cerca
	 * del tipo pasado por parámetro. Se obtiene la siguiente información, el
	 * tipo, si el tipo de dato es un array, si es de un tipo primitivo, el tipo
	 * de los elementos del array(en caso de ser un array) y las dimensiones del
	 * array (en caso de serlo). Cabe decir que los tipos de datos primitivos y
	 * las clases que envuelven dicho datos (Integer a int) son tratados de la
	 * misma manera, por ejemplo int y java.lang.Integer devuelven ambas como
	 * tipo "int".
	 * 
	 * @param tipo
	 *            Tipo del que se desea saber toda la información.
	 * @return Devuelve un array de tipo String de dimensión 5 con los
	 *         siguientes datos: [0]->tipo, [1]->Si es primitivo, [2]->Si es
	 *         array, [3]->Tipo de datos del array y [4]->Dimensiones del array.
	 */
	String[] datosTipo(String tipo)
	{
		String esArray = "no";
		String esPrimitivo = "yes";
		String tipoRetorno = null;
		String tipoDatosArray = null;
		String dimArray = "1";

		String retorno[] = new String[5];

		if (tipo.equals("int") || tipo.equals("java.lang.Integer"))
			tipoRetorno = "int";
		else if (tipo.equals("float") || tipo.equals("java.lang.Float"))
			tipoRetorno = "float";
		else if (tipo.equals("double") || tipo.equals("java.lang.Double"))
			tipoRetorno = "double";
		else if (tipo.equals("long") || tipo.equals("java.lang.Long"))
			tipoRetorno = "long";
		else if (tipo.equals("boolean") || tipo.equals("java.lang.Boolean"))
			tipoRetorno = "boolean";
		else if (tipo.equals("byte") || tipo.equals("java.lang.Byte"))
			tipoRetorno = "byte";
		else if (tipo.equals("short") || tipo.equals("java.lang.Short"))
			tipoRetorno = "short";
		else if (tipo.equals("char") || tipo.equals("java.lang.Character"))
			tipoRetorno = "char";
		else if (tipo.equals("String") || tipo.equals("java.lang.String"))
			tipoRetorno = "String";
		else if (tipo.startsWith("["))
		{
			esArray = "yes";
			tipoRetorno = tipo;
			String tipoAux = tipo.substring(1, tipo.length());
			if (tipoAux.equals("I"))
				tipoDatosArray = "int";
			else if (tipoAux.equals("F"))
				tipoDatosArray = "float";
			else if (tipoAux.equals("D"))
				tipoDatosArray = "double";
			else if (tipoAux.equals("J"))
				tipoDatosArray = "long";
			else if (tipoAux.equals("Z"))
				tipoDatosArray = "boolean";
			else if (tipoAux.equals("B"))
				tipoDatosArray = "byte";
			else if (tipoAux.equals("S"))
				tipoDatosArray = "short";
			else if (tipoAux.equals("C"))
				tipoDatosArray = "char";
			else if (tipoAux.equals("Ljava.lang.String;"))
				tipoDatosArray = "String";
			else if (tipoAux.startsWith("L"))
			{
				tipoDatosArray = tipoAux.substring(1, tipoAux.length() - 1);
				esPrimitivo = "no";
			}
			else
			// Si es de mas de una dimension
			{
				dimArray = String.valueOf(tipoAux.lastIndexOf("[") + 2);
				tipoAux = tipoAux.substring(tipoAux.lastIndexOf("[") + 1, tipoAux.length());

				if (tipoAux.equals("I"))
					tipoDatosArray = "int";
				else if (tipoAux.equals("F"))
					tipoDatosArray = "float";
				else if (tipoAux.equals("D"))
					tipoDatosArray = "double";
				else if (tipoAux.equals("J"))
					tipoDatosArray = "long";
				else if (tipoAux.equals("Z"))
					tipoDatosArray = "boolean";
				else if (tipoAux.equals("B"))
					tipoDatosArray = "byte";
				else if (tipoAux.equals("S"))
					tipoDatosArray = "short";
				else if (tipoAux.equals("C"))
					tipoDatosArray = "char";
				else
					// Son arrays no primitivos, quitamos la L y el ;
					tipoDatosArray = tipoAux.substring(1, tipoAux.length() - 1);

				if (!datosTipo(tipoDatosArray)[1].equals("yes"))
					esPrimitivo = "no";
			}
		}
		else
		{
			esArray = "no";
			esPrimitivo = "no";
			tipoRetorno = tipo;
		}
		retorno[0] = tipoRetorno;
		retorno[1] = esPrimitivo;
		retorno[2] = esArray;
		retorno[3] = tipoDatosArray;
		retorno[4] = dimArray;
		return retorno;
	}

	/**
	 * Devuelve un array de dos dimensiones con toda la información de los
	 * elementos guardados en el contenedor. Dicha información es la clave
	 * asociada a cada elemento y el tipo de los objetos. Dicho array representa
	 * una tabla en la que cada fila tiene la clave del contenedor asociada a
	 * cada objeto, en la primera columna y el tipo de dicho objeto en la
	 * segunda columna.
	 * 
	 * @return Devuelve un array de dos dimensiones con toda la información de
	 *         los elementos guardados en el contenedor, dicho array es perfecto
	 *         para mostrar dicha información en un JTable.
	 */
	String[][] getContenido()
	{
		int cuantos = contenedor.size();
		String[][] lista = new String[cuantos][2];
		Iterator<Entry<String, Object>> it = contenedor.entrySet().iterator();
		int i = 0;
		while (it.hasNext())
		{
			Entry<String, Object> e = it.next();
			lista[i][0] = e.getKey();
			lista[i][1] = e.getValue().getClass().getName();
			i++;
		}
		return lista;
	}

	/**
	 * Elimina del contenedor el elemento cuya clave coincide con el nombre
	 * pasado por parametro.
	 * 
	 * @param nombre
	 *            Nombre del elemento sobre el que se quiere obtener la
	 *            información.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepción.
	 */
	void eliminar(String nombre) throws Exception
	{
		comprobarNombre(nombre);
		contenedor.remove(nombre);
	}

	/**
	 * Elimina todos los elementos guardados en el contendor.
	 */
	void eliminarTodo()
	{
		contenedor.clear();
	}
}
