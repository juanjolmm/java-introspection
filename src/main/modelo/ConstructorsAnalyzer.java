package tfc.modelo;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jjlopez && EnriqueV: ConstructorsAnalyzer es la clase que contiene
 *         todos los métodos necesarios para obtener toda la información de los
 *         constructores asociados a los elementos guardados en el objeto de
 *         tipo ContainerTools utilizado para inicializarse. Dicha clase y sus
 *         métodos solo son accesibles desde otras clases que pertenezcan al
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
	 * Devuelve un array con los parámetros del constructor pasado por
	 * parámetro.
	 * 
	 * @param c
	 *            Contructor del que se quieren saber los parámetros.
	 * @return Devuelve un array de tipo Class con todos los parámetros
	 *         asociados al constructor.
	 */
	private Class<?>[] getParam(Constructor<?> c)
	{
		return c.getParameterTypes();
	}

	/**
	 * Devuelve el número de parámetros del constructor pasado por parámetro.
	 * 
	 * @param c
	 *            Contructor del que se quiere saber el nœmero de parámetros.
	 * @return Devuelve un entero con el número de parámetros del constructor.
	 */
	private int getNumParam(Constructor<?> c)
	{
		return c.getParameterTypes().length;
	}

	/**
	 * Permite obtener el número de constructores de la clase del objeto
	 * asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Clave del elemento sobre el que se quiere obtener la
	 *            información.
	 * @return Devuelve el número de constructores de la clase del objeto
	 *         asociado a la clave pasada por parámetro.
	 */
	private int getNumConstructores(String nombre)
	{
		Constructor<?>[] constructores = contenedor.obtenClase(nombre).getConstructors();
		return constructores.length;
	}

	/**
	 * Devuelve un array con la información de los parámetros del constructor
	 * indicado perteneciente a la clase del objeto asociado a la clave pasada
	 * por parámetro.
	 * 
	 * @param nombre
	 *            Clave del elemento sobre el que se quiere obtener la
	 *            información.
	 * @param indiceConstructor
	 *            Identificación del constructor del que se quiere obtener la
	 *            información.
	 * @return Devuelve un array de tipo String que representa cada uno de los
	 *         parámetros del constructor.
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
	 * Devuelve toda la información necesaria sobre todos los constructores de
	 * la clase asociada al elemento pasado por parámetro.
	 * 
	 * @param nombre
	 *            Clave del elemento sobre el que se quiere obtener la
	 *            información.
	 * @return Devuelve un objeto de tipo List<String[]> con toda la información
	 *         de todos los constructores de la clase del elemento pasado por
	 *         parámetro. La información de cada uno de los constructores se
	 *         guarda en un array de tipo String, en el que cada elemento
	 *         representa el tipo de los parámetros del constructor.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parámetro no se
	 *             encuentre en el contenedor se lanza una excepción.
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
	 * Permite determina si el array de objetos pasado por parámetro es válido
	 * para ejecutar el constructos que tiene como parámetros tiposParam.
	 * 
	 * @param tiposParam
	 *            Tipos de los parámetros de un constructor.
	 * @param params
	 *            Array de objetos pasados por parámetro para ejecutar un
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
	 * Ejecuta el constructor cuyos parámetros coinciden con el array de objetos
	 * pasado por parámetro y crea un nuevo objeto.
	 * 
	 * @param nombre
	 *            Nombre de la clase que tiene el constructor que se quiere
	 *            ejecutar.
	 * @param params
	 *            Array de objetos que son los parámetros del constructor.
	 * @return Devuelve el objeto creado en la instanciación.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepción. Se lanza
	 *             también una excepcion en caso de que los parámetros no se
	 *             ajusten a ningún constructor.
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
