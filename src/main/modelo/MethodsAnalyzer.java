package tfc.modelo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jjlopez && EnriqueV: MethodsAnalyzer es la clase que contiene todos
 *         los métodos necesarios para obtener toda la información de los
 *         métodos asociados a los elementos guardados en el objeto de tipo
 *         ContainerTools utilizado para inicializarse. Dicha clase y sus
 *         métodos solo son accesibles desde otras clases que pertenezcan al
 *         paquete modelo.
 * 
 */
class MethodsAnalyzer
{
	private ContainerTools	contenedor;

	/**
	 * Constructor que inicializa un objeto de tipo MethodsAnalyzer.
	 * 
	 * @param tools
	 *            Objeto de tipo ContainerTools que tiene el almacen de objetos
	 *            sobre los que se hacen las consultas.
	 */
	MethodsAnalyzer(ContainerTools tools)
	{
		contenedor = tools;
	}

	// //////////////////////////////////////////////////////////////////////////
	// ////// METODOS PARA LA INFO DE LOS METODOS
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * Devuelve un array de tipo Class con los parámetros del método pasado por
	 * parámetro.
	 * 
	 * @param m
	 *            Método del que se quiere saber los parámetros.
	 * @return Devuelve un array de tipo Class con los parámetros del método
	 *         pasado por parámetro.
	 */
	private Class<?>[] getParam(Method m)
	{
		return m.getParameterTypes();
	}

	/**
	 * Devuelve un entero con el nœmero de parámetros del método pasado por
	 * parámetro.
	 * 
	 * @param m
	 *            Método del que se quiere saber el número de parámetros.
	 * @return Devuelve un entero con el número de parámetros del método pasado
	 *         por parámetro.
	 */
	private int getNumParam(Method m)
	{
		return m.getParameterTypes().length;
	}

	/**
	 * Devuelve el número de métodos estáticos y públicos que tiene el objeto
	 * guardado en el contenedor, asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere saber el número de
	 *            métodos estáticos y públicos.
	 * @return Devuelve el número de métodos estáticos y públicos que tiene el
	 *         objeto guardado en el contenedor, asociado a la clave pasada por
	 *         parámetro.
	 */
	private int getNumMetodosEstaticos(String nombre)
	{
		Method[] metodos = contenedor.obtenClase(nombre).getDeclaredMethods();
		return getNumMetodosEstaticos(metodos);
	}

	/**
	 * Devuelve el número de métodos estáticos y públicos que hay dentro de un
	 * array de métodos.
	 * 
	 * @param metodos
	 *            Array de métodos sobre el que se contará cuantos estáticos y
	 *            públicos hay.
	 * @return Devuelve un entero con el número de métodos estáticos y públicos.
	 */
	private int getNumMetodosEstaticos(Method[] metodos)
	{
		int numero = 0;
		for (int i = 0; i < metodos.length; i++)
		{
			if (Modifier.isStatic(metodos[i].getModifiers()) && Modifier.isPublic(metodos[i].getModifiers()))
				numero++;
		}
		return numero;
	}

	/**
	 * Devuelve un array de tipo Method con los métodos estáticos y públicos que
	 * tiene el objeto guardado en el contenedor, asociado a la clave pasada por
	 * parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere obtener los métodos
	 *            estáticos y públicos.
	 * @return Devuelve un array de tipo Method con los métodos estáticos y
	 *         públicos que tiene el objeto guardado en el contenedor, asociado
	 *         a la clave pasada por parámetro.
	 */
	private Method[] getMetodosEstaticos(String nombre)
	{
		Method[] metodos = contenedor.obtenClase(nombre).getDeclaredMethods();
		int indice = 0;
		Method[] metodEstaticos = new Method[getNumMetodosEstaticos(metodos)];
		for (int i = 0; i < metodos.length; i++)
		{
			if (Modifier.isStatic(metodos[i].getModifiers()) && Modifier.isPublic(metodos[i].getModifiers()))
			{
				metodEstaticos[indice] = metodos[i];
				indice++;
			}
		}
		return metodEstaticos;
	}

	/**
	 * Devuelve el número de métodos no estáticos y públicos que tiene el objeto
	 * guardado en el contenedor, asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere saber el número de
	 *            métodos no estáticos y públicos.
	 * @return Devuelve el número de métodos no estáticos y públicos que tiene
	 *         el objeto guardado en el contenedor, asociado a la clave pasada
	 *         por parámetro.
	 */
	private int getNumMetodosNoEstaticos(String nombre)
	{
		Method[] metodos = contenedor.obtenClase(nombre).getDeclaredMethods();
		return getNumMetodosNoEstaticos(metodos);
	}

	/**
	 * Devuelve el número de métodos no estáticos y públicos que hay dentro de
	 * un array de métodos.
	 * 
	 * @param metodos
	 *            Array de métodos sobre el que se contará cuantos no estáticos
	 *            y públicos hay.
	 * @return Devuelve un entero con el número de métodos no estáticos y
	 *         públicos.
	 */
	private int getNumMetodosNoEstaticos(Method[] metodos)
	{
		int numero = 0;
		for (int i = 0; i < metodos.length; i++)
		{
			if (!Modifier.isStatic(metodos[i].getModifiers()) && Modifier.isPublic(metodos[i].getModifiers()))
				numero++;
		}
		return numero;
	}

	/**
	 * Devuelve un array de tipo Method con los métodos no estáticos y públicos
	 * que tiene el objeto guardado en el contenedor, asociado a la clave pasada
	 * por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere obtener los métodos no
	 *            estáticos y públicos.
	 * @return Devuelve un array de tipo Method con los métodos no estáticos y
	 *         públicos que tiene el objeto guardado en el contenedor asociado a
	 *         la clave pasada por parámetro.
	 */
	private Method[] getMetodosNoEstaticos(String nombre)
	{
		Method[] metodos = contenedor.obtenClase(nombre).getDeclaredMethods();
		int indice = 0;
		Method[] metodNoEstaticos = new Method[getNumMetodosNoEstaticos(metodos)];
		for (int i = 0; i < metodos.length; i++)
		{
			if (!Modifier.isStatic(metodos[i].getModifiers()) && Modifier.isPublic(metodos[i].getModifiers()))
			{
				metodNoEstaticos[indice] = metodos[i];
				indice++;
			}
		}
		return metodNoEstaticos;
	}

	/**
	 * Devuelve un array de tipo String con todos los datos necesarios del
	 * método estático y público indicado, perteneciente al objeto guardado en
	 * el contenedor, asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el método del que se quiere la
	 *            información.
	 * @param indiceMetodo
	 *            Indice del método del que se quiere la información.
	 * @return Devuelve un array de tipo String con toda la información
	 *         necesaria del método estático y público indicado. La información
	 *         obtenida es: nombre, modificador y retorno.
	 */
	private String[] getInfoMetodoEstatico(String nombre, int indiceMetodo)
	{
		Method[] metodos = getMetodosEstaticos(nombre);
		String[] infoM = new String[3];
		infoM[0] = metodos[indiceMetodo].getName();
		infoM[1] = Modifier.toString(metodos[indiceMetodo].getModifiers());
		infoM[2] = metodos[indiceMetodo].getReturnType().getName();
		return infoM;
	}

	/**
	 * Devuelve un objeto de tipo List con toda la información de los métodos
	 * estáticos y públicos pertenecientes al objeto guardado en el contenedor,
	 * asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los métodos de los que se quiere
	 *            la información.
	 * @return Devuelve un objeto de tipo List con toda la información de los
	 *         métodos estáticos y públicos.
	 */
	private List<String[]> getInfoMetodosEstaticos(String nombre)
	{
		List<String[]> infoME = new ArrayList<String[]>();
		int numMet = getNumMetodosEstaticos(nombre);
		for (int i = 0; i < numMet; i++)
			infoME.add(getInfoMetodoEstatico(nombre, i));
		return infoME;
	}

	/**
	 * Devuelve un array de tipo String con todos los datos necesarios del
	 * método no estático y público indicado, perteneciente al objeto guardado
	 * en el contenedor, asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el método del que se quiere la
	 *            información.
	 * @param indiceMetodo
	 *            Indice del método del que se quiere la información.
	 * @return Devuelve un array de tipo String con toda la información
	 *         necesaria del método no estático y público indicado. La
	 *         información obtenida es: nombre, modificador y retorno.
	 */
	private String[] getInfoMetodoNoEstatico(String nombre, int indiceMetodo)
	{
		Method[] metodos = getMetodosNoEstaticos(nombre);
		String[] infoM = new String[3];
		infoM[0] = metodos[indiceMetodo].getName();
		infoM[1] = Modifier.toString(metodos[indiceMetodo].getModifiers());
		infoM[2] = metodos[indiceMetodo].getReturnType().getName();
		return infoM;
	}

	/**
	 * Devuelve un objeto de tipo List con toda la información de los métodos no
	 * estáticos y públicos pertenecientes al objeto guardado en el contenedor,
	 * asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los métodos de los que se quiere
	 *            la información.
	 * @return Devuelve un objeto de tipo List con toda la información de los
	 *         métodos no estáticos y públicos.
	 */
	private List<String[]> getInfoMetodosNoEstaticos(String nombre)
	{
		List<String[]> infoMNE = new ArrayList<String[]>();
		int numMet = getNumMetodosNoEstaticos(nombre);
		for (int i = 0; i < numMet; i++)
			infoMNE.add(getInfoMetodoNoEstatico(nombre, i));
		return infoMNE;
	}

	/**
	 * Devuelve un objeto de tipo List<String[]> con toda la información de los
	 * métodos. Se obtiene la información de los métodos estáticos y públicos en
	 * caso de que el elemento sea un objeto de tipo Class y la información de
	 * los métodos no estáticos y públicos en cualquier otro caso. El elemento
	 * es el objeto guardado en el contenedor, asociado a la clave pasada por
	 * parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los métodos de los que se quiere
	 *            la información.
	 * @return Devuelve un objeto de tipo List<String[]> con toda la información
	 *         de los métodos. Se obtiene la información de los métodos
	 *         estáticos y públicos en caso de que el elemento sea un objeto de
	 *         tipo Class y la información de los métodos no estáticos y
	 *         públicos en cualquier otro caso. La información de cada uno de
	 *         los métodos, que se guarda en un array de tipo String es la
	 *         siguiente:[0]->nombre del método, [1]-> modificador y [2]->
	 *         retorno.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parámetro no se
	 *             encuentre en el contenedor se lanza una excepción.
	 */
	List<String[]> getInfoMetodos(String nombre) throws Exception
	{
		contenedor.comprobarNombre(nombre);
		if (contenedor.esClase(nombre))
		{
			return getInfoMetodosEstaticos(nombre);
		}
		return getInfoMetodosNoEstaticos(nombre);
	}

	/**
	 * Devuelve un array de tipo String con los tipos de los parámetros del
	 * método no estático y público pasado por parámetro, perteneciente al
	 * objeto guardado en el contenedor, asociado a la clave pasada por
	 * parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el método no estático y público
	 *            del que se quiere saber los parámetros.
	 * @param indiceMetodo
	 *            Indice del método no estático y público del que se quiere
	 *            saber los parámetros.
	 * @return Devuelve un array de tipo String con los tipos de los parámetros
	 *         del método.
	 */
	private String[] getInfoParamMetodoNoEstatico(String nombre, int indiceMetodo)
	{
		Method[] metodos = getMetodosNoEstaticos(nombre);
		int numParam = getNumParam(metodos[indiceMetodo]);
		String[] infoPMNE = new String[numParam];
		if (numParam != 0)
		{
			Class<?>[] tipos = getParam(metodos[indiceMetodo]);
			for (int j = 0; j < numParam; j++)
				infoPMNE[j] = tipos[j].getName();
		}
		return infoPMNE;
	}

	/**
	 * Devuelve un objeto de tipo List con todos los tipos de los parámetros de
	 * los métodos no estáticos y públicos del objeto guardado en el contenedor,
	 * asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los métodos no estáticos y
	 *            públicos de los que se quiere saber los parámetros.
	 * @return Devuelve un objeto de tipo List con toda la información de los
	 *         parámetros de los métodos no estáticos y públicos del elemento
	 *         pasado por parámetro.
	 */
	private List<String[]> getInfoParamMetodosNoEstaticos(String nombre)
	{
		List<String[]> infoPMNE = new ArrayList<String[]>();
		int numMet = getNumMetodosNoEstaticos(nombre);
		for (int i = 0; i < numMet; i++)
			infoPMNE.add(getInfoParamMetodoNoEstatico(nombre, i));
		return infoPMNE;
	}

	/**
	 * Devuelve un array de tipo String con los tipos de los parámetros del
	 * método estático y público pasado por parámetro, perteneciente al objeto
	 * guardado en el contenedor, asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el método estático y público del
	 *            que se quiere saber los parámetros.
	 * @param indiceMetodo
	 *            Indice del método estático y público del que se quiere saber
	 *            los parámetros.
	 * @return Devuelve un array de tipo String con los tipos de los parámetros
	 *         del método.
	 */
	private String[] getInfoParamMetodoEstatico(String nombre, int indiceMetodo)
	{
		Method[] metodos = getMetodosEstaticos(nombre);
		int numParam = getNumParam(metodos[indiceMetodo]);
		String[] infoPME = new String[numParam];
		if (numParam != 0)
		{
			Class<?>[] tipos = getParam(metodos[indiceMetodo]);
			for (int j = 0; j < numParam; j++)
				infoPME[j] = tipos[j].getName();
		}
		return infoPME;
	}

	/**
	 * Devuelve un objeto de tipo List con todos los tipos de los parámetros de
	 * los métodos estáticos y públicos del objeto guardado en el contenedor,
	 * asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los métodos estáticos y públicos
	 *            de los que se quiere saber los parámetros.
	 * @return Devuelve un objeto de tipo List con toda la información de los
	 *         parámetros de los métodos estáticos y públicos del elemento
	 *         pasado por parámetro.
	 */
	private List<String[]> getInfoParamMetodosEstaticos(String nombre)
	{
		List<String[]> infoPME = new ArrayList<String[]>();
		int numMet = getNumMetodosEstaticos(nombre);
		for (int i = 0; i < numMet; i++)
			infoPME.add(getInfoParamMetodoEstatico(nombre, i));
		return infoPME;
	}

	/**
	 * Devuelve un objeto de tipo List<String[]> con la información de los
	 * parámetros de los métodos. Obtiene la información de los parámetros de
	 * los métodos estáticos y públicos si el elemento es un objeto de tipo
	 * Class y de los parámetros de los métodos no estáticos y públicos en otro
	 * caso. El elemento es el objeto guardado en el contenedor, asociado a la
	 * clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los métodos de los que se quiere
	 *            saber los parámetros.
	 * @return Devuelve un objeto de tipo List<String[]> con la información de
	 *         los parámetros de los métodos. Obtiene la información de los
	 *         parámetros de los métodos estáticos y públicos si el elemento es
	 *         un objeto de tipo Class y de los parámetros de los métodos no
	 *         estáticos y públicos en otro caso. La información de los
	 *         parametros se guarda en un array de tipo String, cada elemento
	 *         del array contendrá una cadena con el tipo del parámetro
	 *         correspondiente, asi el tipo del primer parámetro será la cadena
	 *         que aparezca en la primera posición.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parámetro no se
	 *             encuentre en el contenedor se lanza una excepción.
	 */
	List<String[]> getInfoParamMetodos(String nombre) throws Exception
	{
		contenedor.comprobarNombre(nombre);
		if (contenedor.esClase(nombre))
		{
			return getInfoParamMetodosEstaticos(nombre);
		}
		return getInfoParamMetodosNoEstaticos(nombre);
	}

	/**
	 * Ejecuta un método con los parámetros correspondientes para un elemento
	 * concreto. Tanto el elemento como el método se pasan como un String y los
	 * parámetros como un array de tipo Object en los parámetros. Devuelve un
	 * objeto de tipo Object resultado de ejecutar dicho método.
	 * 
	 * @param nombre
	 *            Nombre del objeto que tiene el método que se quiere ejecutar.
	 * @param nombreMetodo
	 *            Nombre del método que se desea ejecutar.
	 * @param arrayObj
	 *            Array de tipo Object con los parámetros del método que se
	 *            desea ejecutar.
	 * @return Devuelve un objeto de tipo Object resultado de ejecutar el
	 *         método.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parámetro no se
	 *             encuentre en el contenedor se lanza una excepción. También se
	 *             lanza una excepción en caso de que el nombre del método no
	 *             sea correcto o de que se produzca un error en la ejecución
	 *             del método.
	 */
	Object ejecutaMetodo(String nombre, String nombreMetodo, Object[] arrayObj) throws Exception
	{
		contenedor.comprobarNombre(nombre);
		if (contenedor.esClase(nombre))
		{
			Method[] metodosEsta = getMetodosEstaticos(nombre);
			for (int i = 0; i < metodosEsta.length; i++)
			{
				if (metodosEsta[i].getName().equals(nombreMetodo))
					try
					{
						return metodosEsta[i].invoke(null, arrayObj);
					}
					catch (Exception e)
					{
						//throw new Exception("ejecutaMetodo: " + e.getCause());
					}
			}
			throw new Exception("Error en la ejecucion del método.");
		}
		Method[] metodos = getMetodosNoEstaticos(nombre);
		for (int i = 0; i < metodos.length; i++)
		{
			if (metodos[i].getName().equals(nombreMetodo))
				try
				{
					return metodos[i].invoke(contenedor.getObjeto(nombre), arrayObj);
				}
				catch (Exception e)
				{
					//throw new Exception("ejecutaMetodo: " + e.getCause());
				}
		}
		throw new Exception("Error en la ejecucion del método.");
	}
}
