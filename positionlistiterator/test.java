package aed.positionlistiterator;

import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PositionList<Integer> list = new NodePositionList<Integer>();
		
		list.addLast(1);
		list.addLast(null);
		list.addLast(2);
		list.addLast(null);
		list.addLast(null);
		list.addLast(null);
		list.addLast(3);
		list.addLast(4);
		
		NIterator<Integer> prueba = new NIterator<Integer>(list, 2);
		System.out.println(prueba.next());
		System.out.println(prueba.next());
		System.out.println(prueba.next());
		System.out.println(prueba.hasNext());
	}

}
