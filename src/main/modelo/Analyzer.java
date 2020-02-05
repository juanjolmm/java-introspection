//////////////////////////////////////////////////////////////////////////////////////////
////////	CLASE MODELO
//////////////////////////////////////////////////////////////////////////////////////////

package tfc.modelo;

import java.io.File;
import java.util.List;
import java.util.Vector;

/**
 * @author jjlopez && EnriqueV: La clase Analyzer representa el modelo de la
 *         aplicación. En esta clase aparecen los métodos publicos que podrán
 *         ejecutarse, para el desarrollo de la aplicación. La clase Analyzer es
 *         la única clase pública del paquete modelo cuyos métodos son públicos,
 *         esto quiere decir que desde fuera del paquete modelo, el resto de
 *         clases, solo podrán ejecutar los métodos de la clase Analyzer. De
 *         hecho la clase Analyzer, cuando ejecuta uno de sus métodos, lo que
 *         hace es llamar a los métodos de las otras clases especializadas del
 *         paquete modelo. El modelo de la aplicación consiste en crear un
 *         contenedor de objetos, guardar en él los objetos que se desee y poder
 *         realizar cualquier consulta sobre los constructores, atributos y
 *         métodos de los objetos del contenedor. Estos objetos podrán ser
 *         objetos de cualquier tipo de clase o podrán ser objetos de tipo
 *         Class, lo que permite crear más objetos utilizando los constructores
 *         de la clase. También se puede ejecutar los métodos, estáticos o no, y
 *         ver el contenido de los atributos, estáticos o no, e incluso
 *         modificar dichos atributos. En definitiva, dicho modelo permite
 *         probar cualquier clase, ejecutando sus constructores, modificando y
 *         viendo sus atributos y ejecutando sus métodos, y permite crear
 *         cualquier tipo de objeto, probando también sus métodos y accediendo a
 *         sus atributos, si estos son públicos. Para ello almacena dichos
 *         objetos en un contenedor que los hace accesibles siempre que se
 *         desee.
 */
public class Analyzer
{
	/**
	 * El objeto contenedor es el objeto especializado en manejar el contenedor
	 * de elementos. De hecho el contenedor es un atributo privado de dicha
	 * clase. Tiene todos los métodos para meter, sacar o hacer cualquier
	 * comprobación sobre los elementos del contenedor, dicha clase, asi como
	 * sus métodos, son solo accesibles desde las clases del paquete modelo.
	 */
	private ContainerTools			contenedor;
	/**
	 * El objeto constructorA es el especializado en obtener toda la información
	 * referente a los contructores de los elementos del contenedor, asi como
	 * también puede ejecutar dichos constructores. Dicha clase, asi como sus
	 * métodos, son solo accesibles desde las clases del paquete modelo.
	 */
	private ConstructorsAnalyzer	constructorA;
	/**
	 * El objeto atributesA es el especializado en obtener toda la información
	 * referente a los atributos de los elementos del contenedor, asi como
	 * también puede modificar u obtener el valor de dichos atributos. Dicha
	 * clase, asi como sus métodos, son solo accesibles desde las clases del
	 * paquete modelo.
	 */
	private AtributesAnalyzer		atributesA;
	/**
	 * El objeto methodsA es el especializado en obtener toda la información
	 * referente a los métodos de los elementos del contenedor, asi como también
	 * puede ejecutar dichos métodos. Dicha clase, asi como sus métodos, son
	 * solo accesibles desde las clases del paquete modelo.
	 */
	private MethodsAnalyzer			methodsA;

	/**
	 * Único constructor de la clase Analyzer, crea un nuevo objeto de tipo
	 * Analyzer.
	 * 
	 * @param cantidad
	 *            Indica el número de elementos con el que se crea el Map
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
	 * Indicando una cadena que representa un directorioo un archivo jar, añade
	 * dicho directorio o archivo jar al CLASSPATH en tiempo de ejecución.
	 * 
	 * @param newDir
	 *            Nuevo directorio o archivo jar para añadir al CLASSPATH
	 * @throws Exception
	 *             Si la cadena pasada por parámetro no se corresponde con un
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
	 *             En caso de existir algún problema al guardar la clase se
	 *             lanza una excepción.
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
	public boolean meterObjeto(String nombre, Object obj)
	{
		return contenedor.meterObjeto(nombre, obj);
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
	public String getInfoObjeto(String nombre) throws Exception
	{
		return contenedor.getInfoObjeto(nombre);
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
	 * por parámetro.
	 * 
	 * @param nombre
	 *            Clave asociada al objeto que se desea obtener.
	 * @return Devuelve el objeto asociado a la clave pasada por parámetro.
	 * @throws Exception
	 *             Si la clave pasada por parámetro no se encuentra en el
	 *             contenedor se lanza una excepción.
	 */
	public Object getObjeto(String nombre) throws Exception
	{
		return contenedor.getObjeto(nombre);
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
	public Vector<String> getObjetosTipo(String tipo)
	{
		return contenedor.getObjetosTipo(tipo);
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
	 *            información.
	 * @return Devuelve verdadero si el nombre pasado por parametro es una
	 *         instancia de tipo Class y falso si el nombre se refiere a un
	 *         objeto instancia de cualquier otro tipo de clase.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepción.
	 */
	public boolean esClase(String nombre) throws Exception
	{
		return contenedor.esClase(nombre);
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
	public int esArray(String nombre) throws Exception
	{
		return contenedor.esArray(nombre);
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
	public String[] datosTipo(String tipo)
	{
		return contenedor.datosTipo(tipo);
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
	 *            información.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepción.
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
	public List<String[]> getInfoConstructores(String nombre) throws Exception
	{
		return constructorA.getInfoConstructores(nombre);
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
	public Object crearInstancia(String nombre, Object[] params) throws Exception
	{
		return constructorA.crearInstancia(nombre, params);
	}

	// //////////////////////////////////////////////////////////////////////////
	// ////// METODOS PUBLICOS PARA LA INFO DE LOS ATRIBUTOS
	// //////////////////////////////////////////////////////////////////////////

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
	 *         array de tipo String, es la siguiente: [0]->modificadores,
	 *         [1]->tipo, [2]->nombre, [3]->valor y [4]->si es constante o no.
	 * @throws Exception
	 *             En caso de que el nombre indicado por parametro no se
	 *             encuentre en el contenedor se lanza una excepción.
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
	public String getValorAtributo(String nombre, String nombreAtributo) throws Exception
	{
		return atributesA.getValorAtributo(nombre, nombreAtributo);
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
	public void setValorAtributo(String nombre, String nombreAtributo, Object valor) throws Exception
	{
		atributesA.setValorAtributo(nombre, nombreAtributo, valor);
	}

	// //////////////////////////////////////////////////////////////////////////
	// ////// METODOS PUBLICOS PARA LA INFO DE LOS METODOS
	// //////////////////////////////////////////////////////////////////////////

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
	public List<String[]> getInfoMetodos(String nombre) throws Exception
	{
		return methodsA.getInfoMetodos(nombre);
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
	public List<String[]> getInfoParamMetodos(String nombre) throws Exception
	{
		return methodsA.getInfoParamMetodos(nombre);
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
	public Object ejecutaMetodo(String nombre, String nombreMetodo, Object[] arrayObj) throws Exception
	{
		return methodsA.ejecutaMetodo(nombre, nombreMetodo, arrayObj);
	}
}
