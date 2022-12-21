package aed.individual5;

import java.util.Iterator;

import es.upm.aedlib.Entry;
import es.upm.aedlib.EntryImpl;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.*;
import es.upm.aedlib.map.*;

public class Utils {
  
  public static <E> PositionList<E> deleteRepeated(PositionList<E> l) {
	  
	  PositionList<E> res = new NodePositionList<E>();
	  Position<E> cursor = l.first();
	  Position<E> cursor2;
	  boolean salida;
	  
	  while(cursor != null) {
		  salida = true;
		  cursor2 = l.first();
		  
		  while(cursor != cursor2 & salida) {
			  if(cursor.element() != null & cursor2.element() != null) {
				  if(cursor2.element().equals(cursor.element())) salida = false;
			  }
			  else if (cursor.element() == null & cursor2.element() == null) 
				  salida = false;
			  cursor2 = l.next(cursor2);
		  }
		  
		  if(salida) res.addLast(cursor.element());
		  cursor = l.next(cursor);
	  }
	  return res;
  }
  
  public static <E> PositionList<E> compactar (Iterable<E> lista) {
	  if(lista == null) throw new IllegalArgumentException();
	  PositionList<E> res = new NodePositionList<E>();
	  Iterator<E> it = lista.iterator();
	  E cambioDeValor;
	  
	  if(it.hasNext()) {
		  cambioDeValor = it.next();
		  res.addLast(cambioDeValor);
	  }
	  else return res;
	  
	  while(it.hasNext()) {
		  E p = it.next();
		  if(cambioDeValor != null && p != null && !p.equals(cambioDeValor)) {
			  cambioDeValor = p;
			  res.addLast(cambioDeValor);
		  }
		  else if((cambioDeValor != null && p == null) || (cambioDeValor == null &&
				  p != null)) {
			  cambioDeValor = p;
			  res.addLast(cambioDeValor);
		  }
	  }
	  
	  return res;
  }
  
  public static Map<String,Integer> maxTemperatures(TempData[] tempData) {
	  Map<String, Integer> a = new HashTableMap<String, Integer>();
	  if(tempData.length == 0) return a;
	  else {
		  for(int i = 0; i < tempData.length; i++) {
			  boolean salida = true;
			  int p = 0;
			  for(int j = 0; j < i && salida; j++) {
				  if(tempData[i].getLocation().compareTo(tempData[j].getLocation()) == 0)
					  salida = false;
			  }
			  if(salida) {
				  for(int j = 0; j < tempData.length; j++) {
					  if(tempData[i].getLocation().compareTo(tempData[j].getLocation()) == 0 && p <
							  tempData[j].getTemperature()) 
						  p = tempData[j].getTemperature();
				  }
				  a.put(tempData[i].getLocation(), p);
			  }
		  }
		  return a;
	  }
  }

}



