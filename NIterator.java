package aed.positionlistiterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.PositionList;


public class NIterator<E> implements Iterator<E> {
  // Esta permitido anadir atributos nuevos
	PositionList<E> list;
	int n;
	boolean posicion1;
	Position<E> cursor;
	
  public NIterator(PositionList<E> list, int n) {
    // Modifica
	  this.list = list;
	  cursor = list.first();
	  this.n = n;
	  posicion1 = true;
  }

  @Override
  public boolean hasNext() {
    // Modifica
	  
	  if(busquedaElemento(cursor, false)!= null) return true;
	  else return false;
  }
  public E busquedaElemento(Position<E> puntero, boolean con) 
		  throws NoSuchElementException{
	  
	  E res = null;
	  
	  if(posicion1) {
		  boolean salida = true;
		  while(puntero != null &&salida) {
			  if(puntero.element() != null) {
				  res = puntero.element();
				  salida = false;
			  }
			  puntero = list.next(puntero);
		  }
		  if(!salida && con) posicion1 = false;
	  }
	  
	  else {
		  int contador = 0;
		  while(contador != n && puntero != null) {
			  contador++;
			  res = puntero.element();
			  puntero = list.next(puntero);
		  }
		  while(res == null && puntero != null) {
			  res = puntero.element();
			  puntero = list.next(puntero);
		  }
		  if(res == null || contador != n) return null;
	  }
	  if(con) cursor = puntero;
	  return res;
	  
  }
  
  @Override
  public E next() throws NoSuchElementException {
    // Modifica
	  E res = busquedaElemento(cursor, true);
	  if(res==null) throw new NoSuchElementException();
	  return res;
  }
}

