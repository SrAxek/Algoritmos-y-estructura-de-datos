package aed.polinomios;

import java.util.Arrays;
import java.util.function.BiFunction;

import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.*;


/**
 * Operaciones sobre polinomios de una variable con coeficientes enteros.
 */
public class Polinomio {

  // Una lista de monomios
  PositionList<Monomio> terms;

  /**
   * Crea el polinomio "0".
   */
  public Polinomio() {
    terms = new NodePositionList<>();
  }

  /**
   * Crea un polinomio definado por una lista con monomios.
   * @param terms una lista de monomios
   */
  public Polinomio(PositionList<Monomio> terms) {
    this.terms = terms;
  }

  /**
   * Crea un polinomio definado por un String.
   * La representación del polinomio es una secuencia de monomios separados
   * por '+' (y posiblemente con caracteres blancos).
   * Un monomio esta compuesto por tres partes,
   * el coefficiente (un entero), el caracter 'x' (el variable), y el exponente
   * compuesto por un un caracter '^' seguido por un entero.
   * Se puede omitir multiples partes de un monomio, 
   * ejemplos:
   * <pre>
   * {@code
   * new Polinomio("2x^3 + 9");
   * new Polinomio("2x^3 + -9");
   * new Polinomio("x");   // == 1x^1
   * new Polinomio("5");   // == 5x^0
   * new Polinomio("8x");  // == 8x^1
   * new Polinomio("0");   // == 0x^0
   * }
   * </pre>
   * @throws IllegalArgumentException si el argumento es malformado
   * @param polinomio - una secuencia de monomios separados por '+'
   */
  public Polinomio(String polinomio) {
	  
	  this.terms = new NodePositionList<>();
	  String[] pol = polinomio.trim().split("\\+");
	  // Si es "0" terms permanece vacio
	  if(!polinomio.trim().equals("0")) {
		  /**
		   * Vamos anadiendo los coeficientes y los exponentes de terms en funcion de el array
		   * pol.
		   */
		  for(int i = 0; i < pol.length; i++) {
			  int index = pol[i].trim().indexOf('x');
			  if(index != -1) {
				  /**
				   * Por un lado tenemos si presenta coeficiente y por otro si no se da el caso.
				   */
				  if(index == 0) {
					  /*
					   * Tambien clasificamos si x^1 se representa como x
					   */
					  if(pol[i].trim().indexOf('^') != -1) {
					  String exponente = pol[i].trim().substring(pol[i].trim().indexOf('^')+1, 
							  pol[i].trim().length());
					  terms.addLast(new Monomio(1, Integer.parseInt(exponente)));
					  } else terms.addLast(new Monomio(1, 1));
				  }
				  else {
					  /*
					   * Clasificamos si x presenta exponente o si no
					   */
					  if(pol[i].trim().indexOf('x') != (pol[i].trim().length()-1)) {
						  
						  String coeficiente = pol[i].trim().substring(0,index);
						  String exponente = pol[i].trim().substring(pol[i].trim().indexOf('^')+1, 
								  pol[i].trim().length());
					  
						  terms.addLast(new Monomio(Integer.parseInt(coeficiente), Integer.
								  parseInt(exponente)));
					  
					  } else {
						  String coeficiente = pol[i].trim().substring(0,index);
						  terms.addLast(new Monomio(Integer.parseInt(coeficiente),1));
					  }
				  }
				  // Si no presenta una x devolvemos el coeficiente con 0 como exponente.
			  } else {
				  String coeficiente = pol[i].trim().substring(0, pol[i].trim().length());
				  terms.addLast(new Monomio(Integer.parseInt(coeficiente),0));
		  }
	  	}
	  }
	  //throw new RuntimeException("no esta implementado todavia");
  }

  /**
   * Suma dos polinomios.
   * @param p1 primer polinomio.
   * @param p2 segundo polinomio.
   * @return la suma de los polinomios.
   */
  public static Polinomio suma(Polinomio p1, Polinomio p2) {
	  // Empleamos el metodo auxiliar operacion para calcularlo
	  Polinomio res = operacion(p1,p2);
	  return res;
  }
    
  /**
   * Substraccion de dos polinomios.
   * @param p1 primer polinomio.
   * @param p2 segundo polinomio.
   * @return la resta de los polinomios.
   */
  public static Polinomio resta(Polinomio p1, Polinomio p2) {
	  
	  Polinomio q = new Polinomio();
	  Position<Monomio> cursor = p2.terms.last();
	  /**
	   * Creamos un polinomio y le ponemos los mismos valores que a p2 pero con los coeficientes
	   * negativos
	   */
	  while(cursor != null) {
		  q.terms.addFirst(new Monomio(-cursor.element().getCoeficiente(), 
				  cursor.element().getExponente()));
		  cursor = p2.terms.prev(cursor);
	  }
	  
	  Polinomio p = operacion(p1,q);
	  
	  cursor = p.terms.last();
	  Polinomio res = new Polinomio();
	 /**
	  * Despues de sumar los polonomios p1 y q eliminamos los valores que sean 0 
	  */
	  while(cursor != null) {
		  if(cursor.element().getCoeficiente() != 0) 
			  res.terms.addFirst(new Monomio(cursor.element().getCoeficiente(), 
					  cursor.element().getExponente()));
		  cursor = p.terms.prev(cursor);
	  }
	  
	  return res;
  }
  
  /**
   * Suma dos polinomios.
   * @param p1 primer polinomio.
   * @param p2 segundo polinomio.
   * @return la suma de los polinomios.
   */
  
  //Se trata de un metodo auxiliar para simplificar los metodos suma y resta
  public static Polinomio operacion(Polinomio p1, Polinomio p2) {
	  
	  Polinomio res = new Polinomio();
	  
	  Position<Monomio> cursor1=p1.terms.first();
	  Position<Monomio> cursor2=p2.terms.first();
	  
	  if(p1.terms.isEmpty()) return p2;
	  
	  if(p2.terms.isEmpty()) return p1;
	  
	  /**
	   * Mientras que unos de los dos no sea nulo vemos tres casos: el primero si el segundo no es 
	   * nulo y el exponente de este es mayor y ponemos el valor del el segundo en res, el segundo
	   * si el primero no es nulo y nuevamente el exponente de este es mayor y ponemos su valor.
	   * Finalmente si los exponentes coinciden ponemos de coeficiente la suma de los dos y de exponente
	   * el que sea (en este caso hemos puesto el del primero).
	   */
	  while(!(cursor1==null && cursor2==null)) {
		  
		  if(cursor1==null || cursor2!=null && 
				  cursor1.element().getExponente() < cursor2.element().getExponente()) {
			  res.terms.addLast(new Monomio(cursor2.element().getCoeficiente(), 
					  cursor2.element().getExponente()));
			  cursor2=p2.terms.next(cursor2);
		  }
		  else if(cursor2==null || cursor1!=null && 
				  cursor1.element().getExponente() > cursor2.element().getExponente()) {
			  res.terms.addLast(new Monomio(cursor1.element().getCoeficiente(), 
					  cursor1.element().getExponente()));
			  cursor1=p1.terms.next(cursor1);
		  }
		  else {
			  res.terms.addLast(new Monomio(cursor1.element().getCoeficiente()+
					  cursor2.element().getCoeficiente(), cursor1.element().getExponente()));
			  cursor1=p1.terms.next(cursor1);
			  cursor2=p2.terms.next(cursor2);
		  }
	  }
	  
	  return res;
  }
    
  /**
   * Calcula el producto de un monomio y un polinomio.
   * @param m el monomio
   * @param p el polinomio
   * @return el producto del monomio y el polinomio
   */
  public static Polinomio multiplica(Polinomio p1, Polinomio p2) {
	  
	  Polinomio res = new Polinomio();
	  
	  Position<Monomio> cursor = p2.terms.first();
	  while(cursor != null) {
		  /**
		   * Multiplicamos todos los monomios de p2 con p1 y despues los sumamos.
		   */
		  res = suma(multiplica(cursor.element(),p1), res);
		  cursor = p2.terms.next(cursor);
	  }
	  return res;
  }

  /**
   * Calcula el producto de dos polinomios.
   * @param p1 primer polinomio.
   * @param p2 segundo polinomio.
   * @return el producto de los polinomios.
   */
  public static Polinomio multiplica(Monomio t, Polinomio p) {
	  
	  Polinomio res = new Polinomio();
	  Position<Monomio> cursor = p.terms.first();
	  
	  while(cursor != null) {
		  /**
		   * Simplemente multiplicamos los coeficientes y sumamos los exponentes de 
		   * todos los monomios de p con t.
		   */
		  res.terms.addLast(new Monomio(t.getCoeficiente()* cursor.element()
				  .getCoeficiente(),t.getExponente()+cursor.element().getExponente()));
		  cursor = p.terms.next(cursor);
	  }
	  
	  return res;
  }
    
  /**
   * Devuelve el valor del polinomio cuando su variable es sustiuida por un valor concreto.
   * Si el polinomio es vacio (la representacion del polinomio "0") entonces
   * el valor devuelto debe ser -1.
   * @param valor el valor asignado a la variable del polinomio
   * @return el valor del polinomio para ese valor de la variable.
   */
  public long evaluar(int valor) {
	  
	  long res = 0;
	  if(terms == null) return -1;
	  Position<Monomio> cursor = terms.first();
	  
	  while(cursor != null) {
		  /** 
		   * Si tiene exponente hacemos coeficiente*valor^exponente, en caso contrario solo sumamos
		   * el coeficiente.
		   */
		  if(cursor.element().getExponente() == 0) {
			  res += cursor.element().getCoeficiente();
			  cursor = terms.next(cursor);
		  } else {
		  res += cursor.element().getCoeficiente()*
				  (Math.pow(valor,cursor.element().getExponente()));
		  cursor = terms.next(cursor);
		  }
		  
	  }
	  
	  return res;
  }

  /**
   * Devuelve el exponente (grado) del monomio con el mayor grado
   * dentro del polinomio
   * @return el grado del polinomio
   */
  public int grado() {
	  //Simplemente devolvemos el valor del exponente en la primera posicion.
	  if(terms.isEmpty()) return -1;
	  else return terms.first().element().getExponente();
  }
  
  public boolean equals(Object obj) {
	  boolean res = true;
	  /**
	   * Primero verificamos que el objeto es en realidad un polinomio y lo convertimos mediante
	   * el método convertirEnPolinomio.
	   */
	  Polinomio p = convertirEnPolinomio(obj);
	  if(p == null) return false;
	  
	  Position<Monomio> cursor1 = p.terms.first();
	  Position<Monomio> cursor2 = terms.first();
	  /**
	   * Despues comparamos coeficiente a coeficiente y exponente a exponente si son iguales
	   * y despúes verificamos si tienen la misma longitud (si no return false).
	   */
	  while(cursor1 != null && cursor2 != null && res){
		  if(!(cursor1.element().getCoeficiente() == cursor2.element().getCoeficiente() 
				  && cursor1.element().getExponente() == cursor2.element().getExponente())) {
			  res = false;
		  }
		  
		  cursor1 = p.terms.next(cursor1);
		  cursor2 = terms.next(cursor2);
	  }
	  
	  if(cursor1 != null) return false;
	  if(cursor2 != null) return false;
	  
	  return res;
  }
  
  public static <E> Polinomio convertirEnPolinomio(Object o) {
	  try {
	    	return (Polinomio) o;
	  } catch (ClassCastException e) {
		  return null;
	  }
  }
  
  @Override
  public String toString() {
    if (terms.isEmpty()) return "0";
    else {
      StringBuffer buf = new StringBuffer();
      Position<Monomio> cursor = terms.first();
      while (cursor != null) {
        Monomio p = cursor.element();
        int coef = p.getCoeficiente();
        int exp = p.getExponente();
        buf.append(new Integer(coef).toString());
        if (exp > 0) {
          buf.append("x");
          buf.append("^");
          buf.append(new Integer(exp)).toString();
        }
        cursor = terms.next(cursor);
        if (cursor != null) buf.append(" + ");
      }
      return buf.toString();
    }
  }
}
