package tfc.modelo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jjlopez && EnriqueV: AtributesAnalyzer es la clase que contiene todos
 *         los m�todos necesarios para obtener toda la informaci�n de los
 *         atributos asociados a los elementos guardados en el objeto de tipo
 *         ContainerTools utilizado para inicializarse. Dicha clase y sus
 *         m�todos solo son accesibles desde otras clases que pertenezcan al
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
	 * Permite determinar el n�mero de atributos est�ticos del array pasado por
	 * par�metro.
	 * 
	 * @param atributos
	 *            Array de todos los atributos.
	 * @return Devuelve un entero con la cantidad de atributos est�ticos que hay
	 *         en el array pasado por par�metro.
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
	 * Permite determinar el n�mero de atributos est�ticos que tiene el objeto
	 * guardado en el contenedor asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere saber el n�mero de
	 *            atributos est�ticos.
	 * @return Devuelve un entero con la cantidad de atributos est�ticos que
	 *         tiene el objeto guardado en el contenedor asociado a la clave
	 *         pasada por par�metro.
	 */
	private int getNumAtributosEstaticos(String nombre)
	{
		Field[] atributos = contenedor.obtenClase(nombre).getFields();
		return getNumAtributosEstaticos(atributos);
	}

	/**
	 * Permite determinar el n�mero de atributos no est�ticos del array pasado
	 * por par�metro.
	 * 
	 * @param atributos
	 *            Array de todos los atributos.
	 * @return Devuelve un entero con la cantidad de atributos no est�ticos que
	 *         hay en el array pasado por par�metro.
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
	 * Permite determinar el n�mero de atributos no est�ticos que tiene el
	 * objeto guardado en el contenedor asociado a la clave pasada por
	 * par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere saber el n�mero de
	 *            atributos no est�ticos.
	 * @return Devuelve un entero con la cantidad de atributos no est�ticos que
	 *         tiene el objeto guardado en el contenedor asociado a la clave
	 *         pasada por par�metro.
	 */
	private int getNumAtributosNoEstaticos(String nombre)
	{
		Field[] atributos = contenedor.obtenClase(nombre).getFields();
		return getNumAtributosNoEstaticos(atributos);
	}

	/**
	 * Devuelve los atributos est�ticos que tiene el objeto guardado en el
	 * contenedor asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere obtener los atributos
	 *            est�ticos.
	 * @return Devuelve un array con todos los atributos est�ticos del elemento
	 *         pasado por par�metro.
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
	 * Devuelve los atributos no est�ticos que tiene el objeto guardado en el
	 * contenedor asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento del que se quiere obtener los atributos no
	 *            est�ticos.
	 * @return Devuelve un array con todos los atributos no est�ticos del
	 *         elemento pasado por par�metro.
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
	 * Especificando un atributo est�tico de un elemento del contenedor,
	 * devuelve toda la informaci�n asociada a dicho atributo est�tico.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el atributo est�tico del que se
	 *            desea la informaci�n.
	 * @param indiceAtribEstaticos
	 *            Indice del atributo est�tico del que se desea obtener la
	 *            informaci�n. Dicho �ndice es el correspondiente en el array
	 *            que se obtiene del m�todo getAtributosEstaticos(String nombre)
	 * @return Devuelve un array de tipo String con toda la informaci�n del
	 *         atributo. La informaci�n es la siguiente: nombre,
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
	 * Devuelve, en un objeto de tipo List, toda la informaci�n necesaria sobre
	 * los atributos est�ticos que tiene el objeto guardado en el contenedor
	 * asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los atributos est�ticos de los
	 *            que se desea la informaci�n.
	 * @return Devuelve un objeto de tipo List con toda la informaci�n necesaria
	 *         de todos los atributos est�ticos del elemento pasado por
	 *         par�metro. La informaci�n de cada uno de los atributos est�ticos,
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
	 * Especificando un atributo no est�tico de un elemento del contenedor,
	 * devuelve toda la informaci�n asociada a dicho atributo no est�tico.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el atributo no est�tico del que
	 *            se desea la informaci�n.
	 * @param indiceAtrib
	 *            Indice del atributo no est�tico del que se desea obtener la
	 *            informaci�n. Dicho �ndice es el correspondiente en el array
	 *            que se obtiene del m�todo getAtributosNoEstaticos(String
	 *            nombre)
	 * @return Devuelve un array de tipo String con toda la informaci�n del
	 *         atributo. La informaci�n es la siguiente: nombre, modificadores,
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
	 * Devuelve, en un objeto de tipo List, toda la informaci�n necesaria sobre
	 * los atributos no est�ticos que tiene el objeto guardado en el contenedor
	 * asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene los atributos no est�ticos de
	 *            los que se desea la informaci�n.
	 * @return Devuelve un objeto de tipo List con toda la informaci�n necesaria
	 *         de todos los atributos no est�ticos del elemento pasado por
	 *         par�metro. La informaci�n de cada uno de los atributos no
	 *         est�ticos, es la que se obtiene en
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
	 * Devuelve toda la informaci�n necesaria de los atributos est�ticos, en
	 * caso de que el elemento sea un objeto de tipo Class, y la informaci�n de
	 * los atributos no est�ticos en caso contrario. El elemento es el objeto
	 * guardado en el contenedor asociado a la clave pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento sobre el que se quiere obtener la
	 *            informaci�n.
	 * @return Devuelve un objeto de tipo List<String[]> con la informaci�n de
	 *         los atributos. Si el elemento es un objeto de tipo Class se
	 *         devuelve la informaci�n de los atributos est�ticos y en caso
	 *         contrario la informaci�n de los atributos no est�ticos. La
	 *         informaci�n de cada uno de los atributos, que se guarda en un
	 *         array de tipo String, es la siguiente: [0]->nombre,
	 *         [1]->modificadores, [2]->tipo, [3]->valor y [4]->si es constante
	 *         o no.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepci�n.
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
	 *            informaci�n.
	 * @param nombreAtributo
	 *            Nombre del atributo del que se quiere la informaci�n.
	 * @return Devuelve una cadena con el valor del atributo especificado.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepci�n. Se lanza
	 *             tambi�n una excepcion en caso de que no exista ning�n
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
	 * y el elemento que tiene dicho atributo con el valor pasado por par�metro.
	 * 
	 * @param nombre
	 *            Nombre del elemento que tiene el atributo que se desea
	 *            modificar.
	 * @param nombreAtributo
	 *            Nombre del atributo que se desea modificar.
	 * @param valor
	 *            Nuevo valor que tomar� el atributo indicado.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepci�n. Se lanza
	 *             tambi�n una excepcion en caso de que no exista ning�n
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
