package aed.tries;

import java.util.Iterator;

import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.PositionList;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DictImpl a = new DictImpl();
		String b = null;
		a.add("p"); a.add("alv"); a.add("p"); a.add("pa"); 

		Iterator<Pair<Character, Boolean>> iterator = a.tree.iterator();
		while(iterator.hasNext()) System.out.println(iterator.next());
		
		System.out.println(b==null);
	}
}
