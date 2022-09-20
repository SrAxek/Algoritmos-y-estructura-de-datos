 package aed.individual2;

import es.upm.aedlib.indexedlist.*;

public class IndexedListCheckSumUtils {

  // a no es null, podria tener tamaño 0, n>0
  public static IndexedList<Integer> indexedListCheckSum(IndexedList<Integer> list, int n) {
	  
	  int checksum = 0;
	  int contador = n;
	  int iteracion = 0;
	  int longitud;
	  if(list.size()%n!=0) longitud = list.size() + list.size()/n + 1;
	  else longitud = list.size() + list.size()/n;
	  IndexedList<Integer> res = new ArrayIndexedList<Integer>();
	  
	  while(contador < longitud) {
		  
		  for(int i = contador - n; i <= contador; i++) {
			  if(i < contador) {
			  res.add(i, list.get(i-iteracion));
			  checksum += list.get(i-iteracion);
			  } else res.add(i, checksum);
		  }
		  checksum = 0;
		  iteracion++;
		  contador += n + 1; 
	  }
	  
	  if(list.size()%n != 0) {
		  for(int i = contador - n; i < longitud; i++) {
			  if(i < longitud -1) {
				  res.add(i, list.get(i-iteracion));
				  checksum+=list.get(i-iteracion);
			  }
			  else res.add(i, checksum);
		  }
	  }
	  
	  return res;
  }

  // list no es null, podria tener tamaño 0, n>0
  public static boolean checkIndexedListCheckSum(IndexedList<Integer> list, int n) {
	  
	  boolean res = true;
	  int checksum = 0;
	  int posicion = n;
	  
	  while(res && posicion < list.size()) {
		  for(int i = posicion - 1; i >= posicion - n -1 && res; i--) {
			  
			  if (i >= posicion -n) checksum += list.get(i);
			  else if(checksum!=list.get(posicion)) res = false;
			  
		  }

		  checksum = 0;
		  posicion += n + 1;
		  
	  }
	  
	  if(((list.size()*n)/(n+1)) % n != 0) {

		  for(int i = list.size() - 2; i > posicion - n -1; i--) {
			  if(i >= posicion - n) checksum += list.get(i);
		  }
		  if(checksum!=list.get(list.size() - 1)) res = false;
		  
	  }
	  
	  return res;
  }
}

