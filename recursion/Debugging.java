package aed.recursion;

import es.upm.aedlib.indexedlist.ArrayIndexedList;
import es.upm.aedlib.indexedlist.IndexedList;

public class Debugging {

	public static void main(String[] args) {

		IndexedList<Integer> prueba = new ArrayIndexedList<Integer>();
		
		prueba.add(0, 1);
		prueba.add(1, 1);
		prueba.add(2, 1);
		prueba.add(3, 1);
		prueba.add(4, 1);
		
		findBottom(prueba);
	}

}
