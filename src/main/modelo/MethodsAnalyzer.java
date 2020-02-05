package tfc.modelo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jjlopez && EnriqueV: MethodsAnalyzer es la clase que contiene todos
 *         los m�todos necesarios para obtener toda la informaci�n de los
 *         m�todos asociados a los elementos guardados en el objeto de tipo
 *         ContainerTools utilizado para inicializarse. Dicha clase y sus
 *         m�todos solo son accesibles desde otras clases que pertenezcan al
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
	 * Devuelve un array de tipo Class con los par�metros del m�todo pasado por
	 * par�metro.
	 * 
	 * @param m
	 *            M�todo del que se quiere saber los par�metros.
	 * @return Devuelve un array de tipo Class con los par�metros del m�todo
	 *         pasado por par�metro.
	 */
	private Class<?>[] getParam(Method m)
	{
		return m.getParameterTypes();
	}

	/**
	 * Devuelve un entero con el n�mero de par�metros del m�todo pasado por
	 * par�metro.
	 * 
	 * @param m
	 *            M�todo del que se quiere saber el n�mero de par�metros.
	 * @return Devuelve un entero con el n�mero de par�metros del m�todo pasado
	 *         por par�metro.
	 */
	private int getNumParam(Method m)
	{
		return m.getParameterTypes().length;
	}

	/**
	 * Devuelve el n�mero de m�todos est�ticos y p�blicos que tiene el objeto
	 * guardado en el contenedor, asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere saber el n�mero de
	 *            m�todos est�ticos y p�blicos.
	 * @return Devuelve el n�mero de m�todos est�ticos y p�blicos que tiene el
	 *         objeto guardado en el contenedor, asociado a la clave pasada por
	 *         par�metro.
	 */
	private int getNumMetodosEstaticos(String nombre)
	{
		Method[] metodos = contenedor.obtenClase(nombre).getDeclaredMethods();
		return getNumMetodosEstaticos(metodos);
	}

	/**
	 * Devuelve el n�mero de m�todos est�ticos y p�blicos que hay dentro de un
	 * array de m�todos.
	 * 
	 * @param metodos
	 *            Array de m�todos sobre el que se contar� cuantos est�ticos y
	 *            p�blicos hay.
	 * @return Devuelve un entero con el n�mero de m�todos est�ticos y p�blicos.
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
	 * Devuelve un array de tipo Method con los m�todos est�ticos y p�blicos que
	 * tiene el objeto guardado en el contenedor, asociado a la clave pasada por
	 * par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere obtener los m�todos
	 *            est�ticos y p�blicos.
	 * @return Devuelve un array de tipo Method con los m�todos est�ticos y
	 *         p�blicos que tiene el objeto guardado en el contenedor, asociado
	 *         a la clave pasada por par�metro.
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
	 * Devuelve el n�mero de m�todos no est�ticos y p�blicos que tiene el objeto
	 * guardado en el contenedor, asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere saber el n�mero de
	 *            m�todos no est�ticos y p�blicos.
	 * @return Devuelve el n�mero de m�todos no est�ticos y p�blicos que tiene
	 *         el objeto guardado en el contenedor, asociado a la clave pasada
	 *         por par�metro.
	 */
	private int getNumMetodosNoEstaticos(String nombre)
	{
		Method[] metodos = contenedor.obtenClase(nombre).getDeclaredMethods();
		return getNumMetodosNoEstaticos(metodos);
	}

	/**
	 * Devuelve el n�mero de m�todos no est�ticos y p�blicos que hay dentro de
	 * un array de m�todos.
	 * 
	 * @param metodos
	 *            Array de m�todos sobre el que se contar� cuantos no est�ticos
	 *            y p�blicos hay.
	 * @return Devuelve un entero con el n�mero de m�todos no est�ticos y
	 *         p�blicos.
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
	 * Devuelve un array de tipo Method con los m�todos no est�ticos y p�blicos
	 * que tiene el objeto guardado en el contenedor, asociado a la clave pasada
	 * por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere obtener los m�todos no
	 *            est�ticos y p�blicos.
	 * @return Devuelve un array de tipo Method con los m�todos no est�ticos y
	 *         p�blicos que tiene el objeto guardado en el contenedor asociado a
	 *         la clave pasada por par�metro.
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
	 * m�todo est�tico y p�blico indicado, perteneciente al objeto guardado en
	 * el contenedor, asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el m�todo del que se quiere la
	 *            informaci�n.
	 * @param indiceMetodo
	 *            Indice del m�todo del que se quiere la informaci�n.
	 * @return Devuelve un array de tipo String con toda la informaci�n
	 *         necesaria del m�todo est�tico y p�blico indicado. La informaci�n
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
	 * Devuelve un objeto de tipo List con toda la informaci�n de los m�todos
	 * est�ticos y p�blicos pertenecientes al objeto guardado en el contenedor,
	 * asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los m�todos de los que se quiere
	 *            la informaci�n.
	 * @return Devuelve un objeto de tipo List con toda la informaci�n de los
	 *         m�todos est�ticos y p�blicos.
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
	 * m�todo no est�tico y p�blico indicado, perteneciente al objeto guardado
	 * en el contenedor, asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el m�todo del que se quiere la
	 *            informaci�n.
	 * @param indiceMetodo
	 *            Indice del m�todo del que se quiere la informaci�n.
	 * @return Devuelve un array de tipo String con toda la informaci�n
	 *         necesaria del m�todo no est�tico y p�blico indicado. La
	 *         informaci�n obtenida es: nombre, modificador y retorno.
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
	 * Devuelve un objeto de tipo List con toda la informaci�n de los m�todos no
	 * est�ticos y p�blicos pertenecientes al objeto guardado en el contenedor,
	 * asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los m�todos de los que se quiere
	 *            la informaci�n.
	 * @return Devuelve un objeto de tipo List con toda la informaci�n de los
	 *         m�todos no est�ticos y p�blicos.
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
	 * Devuelve un objeto de tipo List<String[]> con toda la informaci�n de los
	 * m�todos. Se obtiene la informaci�n de los m�todos est�ticos y p�blicos en
	 * caso de que el elemento sea un objeto de tipo Class y la informaci�n de
	 * los m�todos no est�ticos y p�blicos en cualquier otro caso. El elemento
	 * es el objeto guardado en el contenedor, asociado a la clave pasada por
	 * par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los m�todos de los que se quiere
	 *            la informaci�n.
	 * @return Devuelve un objeto de tipo List<String[]> con toda la informaci�n
	 *         de los m�todos. Se obtiene la informaci�n de los m�todos
	 *         est�ticos y p�blicos en caso de que el elemento sea un objeto de
	 *         tipo Class y la informaci�n de los m�todos no est�ticos y
	 *         p�blicos en cualquier otro caso. La informaci�n de cada uno de
	 *         los m�todos, que se guarda en un array de tipo String es la
	 *         siguiente:[0]->nombre del m�todo, [1]-> modificador y [2]->
	 *         retorno.
	 * @throws Exception
	 *             En caso de que el nombre indicado por par�metro no se
	 *             encuentre en el contenedor se lanza una excepci�n.
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
	 * Devuelve un array de tipo String con los tipos de los par�metros del
	 * m�todo no est�tico y p�blico pasado por par�metro, perteneciente al
	 * objeto guardado en el contenedor, asociado a la clave pasada por
	 * par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el m�todo no est�tico y p�blico
	 *            del que se quiere saber los par�metros.
	 * @param indiceMetodo
	 *            Indice del m�todo no est�tico y p�blico del que se quiere
	 *            saber los par�metros.
	 * @return Devuelve un array de tipo String con los tipos de los par�metros
	 *         del m�todo.
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
	 * Devuelve un objeto de tipo List con todos los tipos de los par�metros de
	 * los m�todos no est�ticos y p�blicos del objeto guardado en el contenedor,
	 * asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los m�todos no est�ticos y
	 *            p�blicos de los que se quiere saber los par�metros.
	 * @return Devuelve un objeto de tipo List con toda la informaci�n de los
	 *         par�metros de los m�todos no est�ticos y p�blicos del elemento
	 *         pasado por par�metro.
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
	 * Devuelve un array de tipo String con los tipos de los par�metros del
	 * m�todo est�tico y p�blico pasado por par�metro, perteneciente al objeto
	 * guardado en el contenedor, asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el m�todo est�tico y p�blico del
	 *            que se quiere saber los par�metros.
	 * @param indiceMetodo
	 *            Indice del m�todo est�tico y p�blico del que se quiere saber
	 *            los par�metros.
	 * @return Devuelve un array de tipo String con los tipos de los par�metros
	 *         del m�todo.
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
	 * Devuelve un objeto de tipo List con todos los tipos de los par�metros de
	 * los m�todos est�ticos y p�blicos del objeto guardado en el contenedor,
	 * asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los m�todos est�ticos y p�blicos
	 *            de los que se quiere saber los par�metros.
	 * @return Devuelve un objeto de tipo List con toda la informaci�n de los
	 *         par�metros de los m�todos est�ticos y p�blicos del elemento
	 *         pasado por par�metro.
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
	 * Devuelve un objeto de tipo List<String[]> con la informaci�n de los
	 * par�metros de los m�todos. Obtiene la informaci�n de los par�metros de
	 * los m�todos est�ticos y p�blicos si el elemento es un objeto de tipo
	 * Class y de los par�metros de los m�todos no est�ticos y p�blicos en otro
	 * caso. El elemento es el objeto guardado en el contenedor, asociado a la
	 * clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los m�todos de los que se quiere
	 *            saber los par�metros.
	 * @return Devuelve un objeto de tipo List<String[]> con la informaci�n de
	 *         los par�metros de los m�todos. Obtiene la informaci�n de los
	 *         par�metros de los m�todos est�ticos y p�blicos si el elemento es
	 *         un objeto de tipo Class y de los par�metros de los m�todos no
	 *         est�ticos y p�blicos en otro caso. La informaci�n de los
	 *         parametros se guarda en un array de tipo String, cada elemento
	 *         del array contendr� una cadena con el tipo del par�metro
	 *         correspondiente, asi el tipo del primer par�metro ser� la cadena
	 *         que aparezca en la primera posici�n.
	 * @throws Exception
	 *             En caso de que el nombre indicado por par�metro no se
	 *             encuentre en el contenedor se lanza una excepci�n.
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
	 * Ejecuta un m�todo con los par�metros correspondientes para un elemento
	 * concreto. Tanto el elemento como el m�todo se pasan como un String y los
	 * par�metros como un array de tipo Object en los par�metros. Devuelve un
	 * objeto de tipo Object resultado de ejecutar dicho m�todo.
	 * 
	 * @param nombre
	 *            Nombre del objeto que tiene el m�todo que se quiere ejecutar.
	 * @param nombreMetodo
	 *            Nombre del m�todo que se desea ejecutar.
	 * @param arrayObj
	 *            Array de tipo Object con los par�metros del m�todo que se
	 *            desea ejecutar.
	 * @return Devuelve un objeto de tipo Object resultado de ejecutar el
	 *         m�todo.
	 * @throws Exception
	 *             En caso de que el nombre indicado por par�metro no se
	 *             encuentre en el contenedor se lanza una excepci�n. Tambi�n se
	 *             lanza una excepci�n en caso de que el nombre del m�todo no
	 *             sea correcto o de que se produzca un error en la ejecuci�n
	 *             del m�todo.
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
			throw new Exception("Error en la ejecucion del m�todo.");
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
		throw new Exception("Error en la ejecucion del m�todo.");
	}
}
