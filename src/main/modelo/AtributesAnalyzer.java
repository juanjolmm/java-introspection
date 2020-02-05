package tfc.modelo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jjlopez && EnriqueV: AtributesAnalyzer es la clase que contiene todos
 *         los métodos necesarios para obtener toda la información de los
 *         atributos asociados a los elementos guardados en el objeto de tipo
 *         ContainerTools utilizado para inicializarse. Dicha clase y sus
 *         métodos solo son accesibles desde otras clases que pertenezcan al
 *         paquete modelo.
 * 
 */
class AtributesAnalyzer
{
	private ContainerTools	contenedor;

	/**
	 * Constructor que inicializa un objeto de tipo AtributesAnalyzer.
	 * 
	 * @param tools
	 *            Objeto de tipo ContainerTools que tiene el almacen de objetos
	 *            sobre los que se hacen las consultas.
	 */
	AtributesAnalyzer(ContainerTools tools)
	{
		contenedor = tools;
	}

	// //////////////////////////////////////////////////////////////////////////
	// ////// METODOS PARA LA INFO DE LOS ATRIBUTOS
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * Permite determinar el número de atributos estáticos del array pasado por
	 * parámetro.
	 * 
	 * @param atributos
	 *            Array de todos los atributos.
	 * @return Devuelve un entero con la cantidad de atributos estáticos que hay
	 *         en el array pasado por parámetro.
	 */
	private int getNumAtributosEstaticos(Field[] atributos)
	{
		int numero = 0;
		for (int i = 0; i < atributos.length; i++)
		{
			if (Modifier.isStatic(atributos[i].getModifiers()))
				numero++;
		}
		return numero;
	}

	/**
	 * Permite determinar el número de atributos estáticos que tiene el objeto
	 * guardado en el contenedor asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere saber el número de
	 *            atributos estáticos.
	 * @return Devuelve un entero con la cantidad de atributos estáticos que
	 *         tiene el objeto guardado en el contenedor asociado a la clave
	 *         pasada por parámetro.
	 */
	private int getNumAtributosEstaticos(String nombre)
	{
		Field[] atributos = contenedor.obtenClase(nombre).getFields();
		return getNumAtributosEstaticos(atributos);
	}

	/**
	 * Permite determinar el número de atributos no estáticos del array pasado
	 * por parámetro.
	 * 
	 * @param atributos
	 *            Array de todos los atributos.
	 * @return Devuelve un entero con la cantidad de atributos no estáticos que
	 *         hay en el array pasado por parámetro.
	 */
	private int getNumAtributosNoEstaticos(Field[] atributos)
	{
		int numero = 0;
		for (int i = 0; i < atributos.length; i++)
		{
			if (!Modifier.isStatic(atributos[i].getModifiers()))
				numero++;
		}
		return numero;
	}

	/**
	 * Permite determinar el número de atributos no estáticos que tiene el
	 * objeto guardado en el contenedor asociado a la clave pasada por
	 * parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere saber el número de
	 *            atributos no estáticos.
	 * @return Devuelve un entero con la cantidad de atributos no estáticos que
	 *         tiene el objeto guardado en el contenedor asociado a la clave
	 *         pasada por parámetro.
	 */
	private int getNumAtributosNoEstaticos(String nombre)
	{
		Field[] atributos = contenedor.obtenClase(nombre).getFields();
		return getNumAtributosNoEstaticos(atributos);
	}

	/**
	 * Devuelve los atributos estáticos que tiene el objeto guardado en el
	 * contenedor asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere obtener los atributos
	 *            estáticos.
	 * @return Devuelve un array con todos los atributos estáticos del elemento
	 *         pasado por parámetro.
	 */
	private Field[] getAtributosEstaticos(String nombre)
	{
		Field[] atributos = contenedor.obtenClase(nombre).getFields();
		int indice = 0;
		Field[] atribEstaticos = new Field[getNumAtributosEstaticos(atributos)];
		for (int i = 0; i < atributos.length; i++)
		{
			if (Modifier.isStatic(atributos[i].getModifiers()))
			{
				atribEstaticos[indice] = atributos[i];
				indice++;
			}
		}
		return atribEstaticos;
	}

	/**
	 * Devuelve los atributos no estáticos que tiene el objeto guardado en el
	 * contenedor asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere obtener los atributos no
	 *            estáticos.
	 * @return Devuelve un array con todos los atributos no estáticos del
	 *         elemento pasado por parámetro.
	 */
	private Field[] getAtributosNoEstaticos(String nombre)
	{
		Field[] atributos = contenedor.obtenClase(nombre).getFields();
		int indice = 0;
		Field[] atribNoEstaticos = new Field[getNumAtributosNoEstaticos(atributos)];
		for (int i = 0; i < atributos.length; i++)
		{
			if (!Modifier.isStatic(atributos[i].getModifiers()))
			{
				atribNoEstaticos[indice] = atributos[i];
				indice++;
			}
		}
		return atribNoEstaticos;
	}

	/**
	 * Especificando un atributo estático de un elemento del contenedor,
	 * devuelve toda la información asociada a dicho atributo estático.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el atributo estático del que se
	 *            desea la información.
	 * @param indiceAtribEstaticos
	 *            Indice del atributo estático del que se desea obtener la
	 *            información. Dicho índice es el correspondiente en el array
	 *            que se obtiene del método getAtributosEstaticos(String nombre)
	 * @return Devuelve un array de tipo String con toda la información del
	 *         atributo. La información es la siguiente: nombre,
	 *         modificadores,tipo, valor, si es constante o no.
	 */
	private String[] getInfoAtributoEstatico(String nombre, int indiceAtribEstaticos)
	{
		Field[] atributosEsta = getAtributosEstaticos(nombre);
		String[] infoAE = new String[5];
		infoAE[0] = atributosEsta[indiceAtribEstaticos].getName();
		infoAE[1] = Modifier.toString(atributosEsta[indiceAtribEstaticos].getModifiers());
		infoAE[2] = atributosEsta[indiceAtribEstaticos].getType().getName();
		try
		{
			infoAE[3] = atributosEsta[indiceAtribEstaticos].get(null).toString();
		}
		catch (Exception e)
		{
			infoAE[3] = "null";
		}
		if (!Modifier.isFinal(atributosEsta[indiceAtribEstaticos].getModifiers()))
			infoAE[4] = "Modificar";
		else
			infoAE[4] = "Constante";
		return infoAE;
	}

	/**
	 * Devuelve, en un objeto de tipo List, toda la información necesaria sobre
	 * los atributos estáticos que tiene el objeto guardado en el contenedor
	 * asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los atributos estáticos de los
	 *            que se desea la información.
	 * @return Devuelve un objeto de tipo List con toda la información necesaria
	 *         de todos los atributos estáticos del elemento pasado por
	 *         parámetro. La información de cada uno de los atributos estáticos,
	 *         es la que se obtiene en getInfoAtributoEstatico(String nombre,
	 *         int indiceAtribEstaticos)
	 */
	private List<String[]> getInfoAtributosEstaticos(String nombre)
	{
		List<String[]> infoAEs = new ArrayList<String[]>();
		int numAtribEs = getNumAtributosEstaticos(nombre);
		for (int i = 0; i < numAtribEs; i++)
			infoAEs.add(getInfoAtributoEstatico(nombre, i));
		return infoAEs;
	}

	/**
	 * Especificando un atributo no estático de un elemento del contenedor,
	 * devuelve toda la información asociada a dicho atributo no estático.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el atributo no estático del que
	 *            se desea la información.
	 * @param indiceAtrib
	 *            Indice del atributo no estático del que se desea obtener la
	 *            información. Dicho índice es el correspondiente en el array
	 *            que se obtiene del método getAtributosNoEstaticos(String
	 *            nombre)
	 * @return Devuelve un array de tipo String con toda la información del
	 *         atributo. La información es la siguiente: nombre, modificadores,
	 *         tipo , valor, si es constante o no.
	 */
	private String[] getInfoAtributoNoEstatico(String nombre, int indiceAtrib)
	{
		Field[] atributos = getAtributosNoEstaticos(nombre);
		String[] infoA = new String[5];
		infoA[0] = atributos[indiceAtrib].getName();
		infoA[1] = Modifier.toString(atributos[indiceAtrib].getModifiers());
		infoA[2] = atributos[indiceAtrib].getType().getName();
		try
		{
			infoA[3] = atributos[indiceAtrib].get(contenedor.getObjeto(nombre)).toString();
		}
		catch (Exception e)
		{
			infoA[3] = "null";
		}
		if (!Modifier.isFinal(atributos[indiceAtrib].getModifiers()))
			infoA[4] = "Modificar";
		else
			infoA[4] = "Constante";
		return infoA;
	}

	/**
	 * Devuelve, en un objeto de tipo List, toda la información necesaria sobre
	 * los atributos no estáticos que tiene el objeto guardado en el contenedor
	 * asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los atributos no estáticos de
	 *            los que se desea la información.
	 * @return Devuelve un objeto de tipo List con toda la información necesaria
	 *         de todos los atributos no estáticos del elemento pasado por
	 *         parámetro. La información de cada uno de los atributos no
	 *         estáticos, es la que se obtiene en
	 *         getInfoAtributoNoEstatico(String nombre, int indiceAtrib)
	 */
	private List<String[]> getInfoAtributosNoEstaticos(String nombre)
	{
		List<String[]> infoAs = new ArrayList<String[]>();
		int numAtrib = getNumAtributosNoEstaticos(nombre);
		for (int i = 0; i < numAtrib; i++)
			infoAs.add(getInfoAtributoNoEstatico(nombre, i));
		return infoAs;
	}

	/**
	 * Devuelve toda la información necesaria de los atributos estáticos, en
	 * caso de que el elemento sea un objeto de tipo Class, y la información de
	 * los atributos no estáticos en caso contrario. El elemento es el objeto
	 * guardado en el contenedor asociado a la clave pasada por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento sobre el que se quiere obtener la
	 *            información.
	 * @return Devuelve un objeto de tipo List<String[]> con la información de
	 *         los atributos. Si el elemento es un objeto de tipo Class se
	 *         devuelve la información de los atributos estáticos y en caso
	 *         contrario la información de los atributos no estáticos. La
	 *         información de cada uno de los atributos, que se guarda en un
	 *         array de tipo String, es la siguiente: [0]->nombre,
	 *         [1]->modificadores, [2]->tipo, [3]->valor y [4]->si es constante
	 *         o no.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepción.
	 */
	List<String[]> getInfoAtributos(String nombre) throws Exception
	{
		contenedor.comprobarNombre(nombre);
		if (contenedor.esClase(nombre))
		{
			return getInfoAtributosEstaticos(nombre);
		}
		return getInfoAtributosNoEstaticos(nombre);
	}

	/**
	 * Devuelve una cadena con el valor de un atributo, especificado por el
	 * nombre del atributo y el elemento que tiene dicho atributo.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el atributo del que se desea la
	 *            información.
	 * @param nombreAtributo
	 *            Nombre del atributo del que se quiere la información.
	 * @return Devuelve una cadena con el valor del atributo especificado.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepción. Se lanza
	 *             también una excepcion en caso de que no exista ningún
	 *             atributo con dicho nombre.
	 */
	String getValorAtributo(String nombre, String nombreAtributo) throws Exception
	{
		contenedor.comprobarNombre(nombre);
		if (contenedor.esClase(nombre))
		{
			Field[] atributosEsta = getAtributosEstaticos(nombre);
			for (int i = 0; i < atributosEsta.length; i++)
			{
				if (atributosEsta[i].getName().equals(nombreAtributo))
					return atributosEsta[i].get(null).toString();
			}
			throw new Exception("getValorAtributo:Nombre de atributo incorrecto.");
		}
		Field[] atributosNoEsta = getAtributosNoEstaticos(nombre);
		for (int i = 0; i < atributosNoEsta.length; i++)
		{
			if (atributosNoEsta[i].getName().equals(nombreAtributo))
				return atributosNoEsta[i].get(contenedor.getObjeto(nombre)).toString();
		}
		throw new Exception("getValorAtributo:Nombre de atributo incorrecto.");
	}

	/**
	 * Modifica el valor de un atributo, especificado por el nombre del atributo
	 * y el elemento que tiene dicho atributo con el valor pasado por parámetro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el atributo que se desea
	 *            modificar.
	 * @param nombreAtributo
	 *            Nombre del atributo que se desea modificar.
	 * @param valor
	 *            Nuevo valor que tomará el atributo indicado.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepción. Se lanza
	 *             también una excepcion en caso de que no exista ningún
	 *             atributo con dicho nombre.
	 */
	void setValorAtributo(String nombre, String nombreAtributo, Object valor) throws Exception
	{
		contenedor.comprobarNombre(nombre);
		if (contenedor.esClase(nombre))
		{
			Field[] atributosEsta = getAtributosEstaticos(nombre);
			for (int i = 0; i < atributosEsta.length; i++)
			{
				if (atributosEsta[i].getName().equals(nombreAtributo))
					try
					{
						atributosEsta[i].set(null, valor);
						return;
					}
					catch (Exception e)
					{
						throw new Exception("setValorAtributo: " + e.getCause());
					}
			}
			throw new Exception("setValorAtributo:Nombre de atributo incorrecto.");
		}
		Field[] atributosNoEsta = getAtributosNoEstaticos(nombre);
		for (int i = 0; i < atributosNoEsta.length; i++)
		{
			if (atributosNoEsta[i].getName().equals(nombreAtributo))
				try
				{
					atributosNoEsta[i].set(contenedor.getObjeto(nombre), valor);
					return;
				}
				catch (Exception e)
				{
					throw new Exception("setValorAtributo: " + e.getCause());
				}
		}
		throw new Exception("setValorAtributo:Nombre de atributo incorrecto.");
	}
}
