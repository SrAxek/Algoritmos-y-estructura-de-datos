package aed.filter;

import java.util.Iterator;
import java.util.function.Predicate;

import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;


public class Utils {

  public static <E> Iterable<E> filter(Iterable<E> d, Predicate<E> pred) {
    	PositionList<E> list = new NodePositionList<E>();
	  	Iterator<E> it = d.iterator();
		while(it.hasNext()) {
			E e = it.next();
			if(e!=null && pred.test(e)) list.addLast(e);	
		}
		Iterable<E> res = list;
    	return res;
  }
}

