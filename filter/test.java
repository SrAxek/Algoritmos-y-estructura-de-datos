package aed.filter;

import java.util.Iterator;

import aed.filter.TesterInd3.Filter;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;

public class test {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		
		Iterable<Integer[]> prueba = (Iterable<Integer[]>) new Filter(new NodePositionList<Integer[]>(), new GreaterThan<E>(0));
		
		Iterator<Integer> it = prueba.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}

}
