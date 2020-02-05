package tfc.modelo;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jjlopez && EnriqueV: ConstructorsAnalyzer es la clase que contiene
 *         todos los m�todos necesarios para obtener toda la informaci�n de los
 *         constructores asociados a los elementos guardados en el objeto de
 *         tipo ContainerTools utilizado para inicializarse. Dicha clase y sus
 *         m�todos solo son accesibles desde otras clases que pertenezcan al
 *         paquete modelo.
 * 
 */
class ConstructorsAnalyzer
{
	private ContainerTools	contenedor;

	/**
	 * Constructor que inicializa un objeto de tipo ConstructorsAnalyzer.
	 * 
	 * @param tools
	 *            Objeto de tipo ContainerTools que tiene el almacen de objetos
	 *            sobre los que se hacen las consultas.
	 */
	ConstructorsAnalyzer(ContainerTools tools)
	{
		contenedor = tools;
	}

	// //////////////////////////////////////////////////////////////////////////
	// ////// METODOS PARA LA INFO DE LOS CONSTRUCTORES
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * Devuelve un array con los par�metros del constructor pasado por
	 * par�metro.
	 * 
	 * @param c
	 *            Contructor del que se quieren saber los par�metros.
	 * @return Devuelve un array de tipo Class con todos los par�metros
	 *         asociados al constructor.
	 */
	private Class<?>[] getParam(Constructor<?> c)
	{
		return c.getParameterTypes();
	}

	/**
	 * Devuelve el n�mero de par�metros del constructor pasado por par�metro.
	 * 
	 * @param c
	 *            Contructor del que se quiere saber el n�mero de par�metros.
	 * @return Devuelve un entero con el n�mero de par�metros del constructor.
	 */
	private int getNumParam(Constructor<?> c)
	{
		return c.getParameterTypes().length;
	}

	/**
	 * Permite obtener el n�mero de constructores de la clase del objeto
	 * asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Clave del elemento sobre el que se quiere obtener la
	 *            informaci�n.
	 * @return Devuelve el n�mero de constructores de la clase del objeto
	 *         asociado a la clave pasada por par�metro.
	 */
	private int getNumConstructores(String nombre)
	{
		Constructor<?>[] constructores = contenedor.obtenClase(nombre).getConstructors();
		return constructores.length;
	}

	/**
	 * Devuelve un array con la informaci�n de los par�metros del constructor
	 * indicado perteneciente a la clase del objeto asociado a la clave pasada
	 * por par�metro.
	 * 
	 * @param nombre
	 *            Clave del elemento sobre el que se quiere obtener la
	 *            informaci�n.
	 * @param indiceConstructor
	 *            Identificaci�n del constructor del que se quiere obtener la
	 *            informaci�n.
	 * @return Devuelve un array de tipo String que representa cada uno de los
	 *         par�metros del constructor.
	 */
	private String[] getInfoConstructor(String nombre, int indiceConstructor)
	{
		Constructor<?>[] constructores = contenedor.obtenClase(nombre).getConstructors();
		int numParam = getNumParam(constructores[indiceConstructor]);
		String[] infoC = new String[numParam];
		if (numParam != 0)
		{
			Class<?>[] tipos = getParam(constructores[indiceConstructor]);
			for (int j = 0; j < numParam; j++)
				infoC[j] = tipos[j].getName();
		}
		return infoC;
	}

	/**
	 * Devuelve toda la informaci�n necesaria sobre todos los constructores de
	 * la clase asociada al elemento pasado por par�metro.
	 * 
	 * @param nombre
	 *            Clave del elemento sobre el que se quiere obtener la
	 *            informaci�n.
	 * @return Devuelve un objeto de tipo List<String[]> con toda la informaci�n
	 *         de todos los constructores de la clase del elemento pasado por
	 *         par�metro. La informaci�n de cada uno de los constructores se
	 *         guarda en un array de tipo String, en el que cada elemento
	 *         representa el tipo de los par�metros del constructor.
	 * @throws Exception
	 *             En caso de que el nombre indicado por par�metro no se
	 *             encuentre en el contenedor se lanza una excepci�n.
	 */
	List<String[]> getInfoConstructores(String nombre) throws Exception
	{
		contenedor.comprobarNombre(nombre);
		List<String[]> infoCs = new ArrayList<String[]>();
		int numCons = getNumConstructores(nombre);
		for (int i = 0; i < numCons; i++)
			infoCs.add(getInfoConstructor(nombre, i));
		return infoCs;
	}

	/**
	 * Permite determina si el array de objetos pasado por par�metro es v�lido
	 * para ejecutar el constructos que tiene como par�metros tiposParam.
	 * 
	 * @param tiposParam
	 *            Tipos de los par�metros de un constructor.
	 * @param params
	 *            Array de objetos pasados por par�metro para ejecutar un
	 *            constructor.
	 * @return Devuelve true si el array de objetos es valido para los
	 *         parametros de un contructor. False en caso contrario.
	 */
	private boolean coincideParam(Class<?>[] tiposParam, Object[] params)
	{
		if (tiposParam.length == params.length)
		{
			for (int i = 0; i < params.length; i++)
			{
				if (!contenedor.datosTipo(tiposParam[i].getName())[0].equals(contenedor.datosTipo(params[i].getClass().getName())[0])
						&& !contenedor.getSuperClases(params[i]).contains(tiposParam[i].getName()) && !contenedor.getInterfaces(params[i]).contains(tiposParam[i].getName()))
					return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Ejecuta el constructor cuyos par�metros coinciden con el array de objetos
	 * pasado por par�metro y crea un nuevo objeto.
	 * 
	 * @param nombre
	 *            Nombre de la clase que tiene el constructor que se quiere
	 *            ejecutar.
	 * @param params
	 *            Array de objetos que son los par�metros del constructor.
	 * @return Devuelve el objeto creado en la instanciaci�n.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepci�n. Se lanza
	 *             tambi�n una excepcion en caso de que los par�metros no se
	 *             ajusten a ning�n constructor.
	 */
	Object crearInstancia(String nombre, Object[] params) throws Exception
	{
		contenedor.comprobarNombre(nombre);
		Constructor<?>[] constructores = contenedor.obtenClase(nombre).getConstructors();
		for (int i = 0; i < constructores.length; i++)
		{
			if (coincideParam(getParam(constructores[i]), params))
				return constructores[i].newInstance(params);
		}
		throw new Exception("crearInstancia:Parametros incorrectos.");
	}
}
