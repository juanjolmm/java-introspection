//////////////////////////////////////////////////////////////////////////////////////////
////////	CLASE MODELO
//////////////////////////////////////////////////////////////////////////////////////////

package tfc.modelo;

import java.io.File;
import java.util.List;
import java.util.Vector;

/**
 * @author jjlopez && EnriqueV: La clase Analyzer representa el modelo de la
 *         aplicaci�n. En esta clase aparecen los m�todos publicos que podr�n
 *         ejecutarse, para el desarrollo de la aplicaci�n. La clase Analyzer es
 *         la �nica clase p�blica del paquete modelo cuyos m�todos son p�blicos,
 *         esto quiere decir que desde fuera del paquete modelo, el resto de
 *         clases, solo podr�n ejecutar los m�todos de la clase Analyzer. De
 *         hecho la clase Analyzer, cuando ejecuta uno de sus m�todos, lo que
 *         hace es llamar a los m�todos de las otras clases especializadas del
 *         paquete modelo. El modelo de la aplicaci�n consiste en crear un
 *         contenedor de objetos, guardar en �l los objetos que se desee y poder
 *         realizar cualquier consulta sobre los constructores, atributos y
 *         m�todos de los objetos del contenedor. Estos objetos podr�n ser
 *         objetos de cualquier tipo de clase o podr�n ser objetos de tipo
 *         Class, lo que permite crear m�s objetos utilizando los constructores
 *         de la clase. Tambi�n se puede ejecutar los m�todos, est�ticos o no, y
 *         ver el contenido de los atributos, est�ticos o no, e incluso
 *         modificar dichos atributos. En definitiva, dicho modelo permite
 *         probar cualquier clase, ejecutando sus constructores, modificando y
 *         viendo sus atributos y ejecutando sus m�todos, y permite crear
 *         cualquier tipo de objeto, probando tambi�n sus m�todos y accediendo a
 *         sus atributos, si estos son p�blicos. Para ello almacena dichos
 *         objetos en un contenedor que los hace accesibles siempre que se
 *         desee.
 */
public class Analyzer
{
	/**
	 * El objeto contenedor es el objeto especializado en manejar el contenedor
	 * de elementos. De hecho el contenedor es un atributo privado de dicha
	 * clase. Tiene todos los m�todos para meter, sacar o hacer cualquier
	 * comprobaci�n sobre los elementos del contenedor, dicha clase, asi como
	 * sus m�todos, son solo accesibles desde las clases del paquete modelo.
	 */
	private ContainerTools			contenedor;
	/**
	 * El objeto constructorA es el especializado en obtener toda la informaci�n
	 * referente a los contructores de los elementos del contenedor, asi como
	 * tambi�n puede ejecutar dichos constructores. Dicha clase, asi como sus
	 * m�todos, son solo accesibles desde las clases del paquete modelo.
	 */
	private ConstructorsAnalyzer	constructorA;
	/**
	 * El objeto atributesA es el especializado en obtener toda la informaci�n
	 * referente a los atributos de los elementos del contenedor, asi como
	 * tambi�n puede modificar u obtener el valor de dichos atributos. Dicha
	 * clase, asi como sus m�todos, son solo accesibles desde las clases del
	 * paquete modelo.
	 */
	private AtributesAnalyzer		atributesA;
	/**
	 * El objeto methodsA es el especializado en obtener toda la informaci�n
	 * referente a los m�todos de los elementos del contenedor, asi como tambi�n
	 * puede ejecutar dichos m�todos. Dicha clase, asi como sus m�todos, son
	 * solo accesibles desde las clases del paquete modelo.
	 */
	private MethodsAnalyzer			methodsA;

	/**
	 * �nico constructor de la clase Analyzer, crea un nuevo objeto de tipo
	 * Analyzer.
	 * 
	 * @param cantidad
	 *            Indica el n�mero de elementos con el que se crea el Map
	 *            perteneciente a su atributo utiles.
	 */
	public Analyzer(int cantidad)
	{
		contenedor = new ContainerTools(cantidad);
		constructorA = new ConstructorsAnalyzer(contenedor);
		atributesA = new AtributesAnalyzer(contenedor);
		methodsA = new MethodsAnalyzer(contenedor);
	}

	// //////////////////////////////////////////////////////////////////////////
	// ////// METODOS PUBLICOS PARA EL CONTENEDOR
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * Indicando una cadena que representa un directorioo un archivo jar, a�ade
	 * dicho directorio o archivo jar al CLASSPATH en tiempo de ejecuci�n.
	 * 
	 * @param newDir
	 *            Nuevo directorio o archivo jar para a�adir al CLASSPATH
	 * @throws Exception
	 *             Si la cadena pasada por par�metro no se corresponde con un
	 *             directorio o archivo jar real.
	 */
	public void addDirToClassPath(String newDir) throws Exception
	{
		contenedor.addDirToClassPath(newDir);
	}
	
	/**
	 * Permite guardar una clase en el contenedor a partir de un archivo.
	 * 
	 * @param pathClase
	 *            Fichero .class que representa la clase que se desea guardar en
	 *            el contenedor.
	 * @return Devuelve una cadena con el nombre completo de la clase.
	 * @throws Exception
	 *             En caso de existir alg�n problema al guardar la clase se
	 *             lanza una excepci�n.
	 */
	public String abrirClase(File pathClase)
	{
		return contenedor.abrirClase(pathClase);
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
	public int abrirClase(String nombre)
	{
		return contenedor.abrirClase(nombre);
	}

	/**
	 * Mete el objeto pasado por par�metro en el contenedor asociado a la clave
	 * pasada por par�metro. Si se mete correctamente devuelve verdadero y si la
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
	public boolean meterObjeto(String nombre, Object obj)
	{
		return contenedor.meterObjeto(nombre, obj);
	}

	/**
	 * Devuelve una cadena representativa del elemento obtenido del contenedor.
	 * 
	 * @param nombre
	 *            Nombre del elemento sobre el que se quiere obtener la
	 *            informaci�n.
	 * @return Devuelve una cadena representativa del objeto obtenido del
	 *         contenedor.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepci�n.
	 */
	public String getInfoObjeto(String nombre) throws Exception
	{
		return contenedor.getInfoObjeto(nombre);
	}

	/**
	 * Devuelve una cadena representativa de los modificadores de la clase
	 * pasada por par�metro.
	 * 
	 * @param nombre
	 *            Nombre de la clase sobre la que se quiere obtener la
	 *            informaci�n.
	 * @return Devuelve una cadena representativa de los modificadores de la
	 *         clase pasada por par�metro.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor, o el elemento no sea un objeto de
	 *             tipo Class, se lanza una excepci�n.
	 */
	public String getModificadorClase(String nombre) throws Exception
	{
		return contenedor.getModificadorClase(nombre);
	}

	/**
	 * Comprueba la existencia de una clave en el contenedor.
	 * 
	 * @param nombre
	 *            Clave que se desea buscar en el contenedor.
	 * @return Devuelve verdadero si dicha clave se encuentra en el contenedor y
	 *         falso en caso contrario.
	 */
	public boolean existeObjeto(String nombre)
	{
		return contenedor.existeObjeto(nombre);
	}

	/**
	 * Devuelve el objeto, guardado en el contenedor, asociado a la clave pasada
	 * por par�metro.
	 * 
	 * @param nombre
	 *            Clave asociada al objeto que se desea obtener.
	 * @return Devuelve el objeto asociado a la clave pasada por par�metro.
	 * @throws Exception
	 *             Si la clave pasada por par�metro no se encuentra en el
	 *             contenedor se lanza una excepci�n.
	 */
	public Object getObjeto(String nombre) throws Exception
	{
		return contenedor.getObjeto(nombre);
	}

	/**
	 * Devuelve un objeto de tipo Vector con los objetos, almacenados en el
	 * contenedor, que son del tipo indicado por par�metro. Tambi�n aparecen en
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
	public Vector<String> getObjetosTipo(String tipo)
	{
		return contenedor.getObjetosTipo(tipo);
	}

	/**
	 * Crea e introduce en el contenedor un nuevo array del tipo indicado y con
	 * los componentes indicados. Si el tipo del array es primitivo o de
	 * cualquier otra clase que envuelve a los tipo primitivos, como Integer a
	 * int, los datos del array van como datos de tipo String en el par�metro
	 * componentes, en caso contrario, si el tipo del array no es primitivo o si
	 * es un array de mas de una dimensi�n, en el par�metro componentes van las
	 * claves de los objetos, guardados en el contenedor, que formar�n parte del
	 * nuevo array.
	 * 
	 * @param nombreArray
	 *            Nombre del array que se va a crear.
	 * @param tipoDatos
	 *            Tipo del array.
	 * @param componentes
	 *            Array de tipo cadena con la siguiente informaci�n, si el array
	 *            es de tipo primitivo tiene los valores a introducir en el
	 *            array, si el array no es de tipo primitivo, almacena los
	 *            nombres de los objetos que ser�n los elementos del array y que
	 *            deben estar almacenados en el contenedor.
	 * @return Devuelve true si el array es creado y metido correctamente en el
	 *         contenedor y false en caso contrario.
	 * @throws Exception
	 *             Si los nombres pasados en el array componentes, en caso de
	 *             ser un array de tipo no primitivo, no existen en el
	 *             contenedor se lanza una excepci�n. Tambi�n salta una
	 *             excepci�n si los objetos indicados no son del tipo del array.
	 */
	public boolean meterArray(String nombreArray, String tipoDatos, String[] componentes) throws Exception
	{
		return contenedor.meterArray(nombreArray, tipoDatos, componentes);
	}

	/**
	 * Determina si el nombre pasado por parametro hace referencia a un objeto,
	 * del contenedor, de tipo Class, en cuyo caso devuelve verdadero, o a un
	 * objeto, del contenedor, de otro cualquier tipo de clase, en cuyo caso
	 * devuelve false.
	 * 
	 * @param nombre
	 *            Nombre del elemento sobre el que se quiere obtener la
	 *            informaci�n.
	 * @return Devuelve verdadero si el nombre pasado por parametro es una
	 *         instancia de tipo Class y falso si el nombre se refiere a un
	 *         objeto instancia de cualquier otro tipo de clase.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepci�n.
	 */
	public boolean esClase(String nombre) throws Exception
	{
		return contenedor.esClase(nombre);
	}

	/**
	 * M�todo que determina si un elemento del contenedor indicado por su clave
	 * es un array o no.
	 * 
	 * @param nombre
	 *            Nombre del elemento del contenedor del que se desea saber si
	 *            es un array.
	 * @return Devuelve -1 si el elemento pasado por par�metro no es un array y
	 *         en caso de que sea un array devuelve la dimendi�n de este.
	 * @throws Exception
	 *             Se lanza una excepci�n en caso de que el nombre pasado por
	 *             par�metro no haga referencia a ning�n elemento del array.
	 */
	public int esArray(String nombre) throws Exception
	{
		return contenedor.esArray(nombre);
	}

	/**
	 * Devuelve un array de tipo String con todos los datos de interes a cerca
	 * del tipo pasado por par�metro. Se obtiene la siguiente informaci�n, el
	 * tipo, si el tipo de dato es un array, si es de un tipo primitivo, el tipo
	 * de los elementos del array(en caso de ser un array) y las dimensiones del
	 * array (en caso de serlo). Cabe decir que los tipos de datos primitivos y
	 * las clases que envuelven dicho datos (Integer a int) son tratados de la
	 * misma manera, por ejemplo int y java.lang.Integer devuelven ambas como
	 * tipo "int".
	 * 
	 * @param tipo
	 *            Tipo del que se desea saber toda la informaci�n.
	 * @return Devuelve un array de tipo String de dimensi�n 5 con los
	 *         siguientes datos: [0]->tipo, [1]->Si es primitivo, [2]->Si es
	 *         array, [3]->Tipo de datos del array y [4]->Dimensiones del array.
	 */
	public String[] datosTipo(String tipo)
	{
		return contenedor.datosTipo(tipo);
	}

	/**
	 * Devuelve un array de dos dimensiones con toda la informaci�n de los
	 * elementos guardados en el contenedor. Dicha informaci�n es la clave
	 * asociada a cada elemento y el tipo de los objetos. Dicho array representa
	 * una tabla en la que cada fila tiene la clave del contenedor asociada a
	 * cada objeto, en la primera columna y el tipo de dicho objeto en la
	 * segunda columna.
	 * 
	 * @return Devuelve un array de dos dimensiones con toda la informaci�n de
	 *         los elementos guardados en el contenedor, dicho array es perfecto
	 *         para mostrar dicha informaci�n en un JTable.
	 */
	public String[][] getContenido()
	{
		return contenedor.getContenido();
	}

	/**
	 * Elimina del contenedor el elemento cuya clave coincide con el nombre
	 * pasado por parametro.
	 * 
	 * @param nombre
	 *            Nombre del elemento sobre el que se quiere obtener la
	 *            informaci�n.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepci�n.
	 */
	public void eliminar(String nombre) throws Exception
	{
		contenedor.eliminar(nombre);
	}

	/**
	 * Elimina todos los elementos guardados en el contendor.
	 */
	public void eliminarTodo()
	{
		contenedor.eliminarTodo();
	}

	// //////////////////////////////////////////////////////////////////////////
	// ////// METODOS PUBLICOS PARA LA INFO DE LOS CONSTRUCTORES
	// //////////////////////////////////////////////////////////////////////////

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
	public List<String[]> getInfoConstructores(String nombre) throws Exception
	{
		return constructorA.getInfoConstructores(nombre);
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
	public Object crearInstancia(String nombre, Object[] params) throws Exception
	{
		return constructorA.crearInstancia(nombre, params);
	}

	// //////////////////////////////////////////////////////////////////////////
	// ////// METODOS PUBLICOS PARA LA INFO DE LOS ATRIBUTOS
	// //////////////////////////////////////////////////////////////////////////

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
	 *         array de tipo String, es la siguiente: [0]->modificadores,
	 *         [1]->tipo, [2]->nombre, [3]->valor y [4]->si es constante o no.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepci�n.
	 */
	public List<String[]> getInfoAtributos(String nombre) throws Exception
	{
		return atributesA.getInfoAtributos(nombre);
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
	public String getValorAtributo(String nombre, String nombreAtributo) throws Exception
	{
		return atributesA.getValorAtributo(nombre, nombreAtributo);
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
	public void setValorAtributo(String nombre, String nombreAtributo, Object valor) throws Exception
	{
		atributesA.setValorAtributo(nombre, nombreAtributo, valor);
	}

	// //////////////////////////////////////////////////////////////////////////
	// ////// METODOS PUBLICOS PARA LA INFO DE LOS METODOS
	// //////////////////////////////////////////////////////////////////////////

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
	public List<String[]> getInfoMetodos(String nombre) throws Exception
	{
		return methodsA.getInfoMetodos(nombre);
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
	public List<String[]> getInfoParamMetodos(String nombre) throws Exception
	{
		return methodsA.getInfoParamMetodos(nombre);
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
	public Object ejecutaMetodo(String nombre, String nombreMetodo, Object[] arrayObj) throws Exception
	{
		return methodsA.ejecutaMetodo(nombre, nombreMetodo, arrayObj);
	}
}
