package aed.hashtable;

import java.util.Iterator;
import java.util.Map.Entry;

import es.upm.aedlib.Position;
import es.upm.aedlib.map.HashTableMap;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashTable<Integer,String> a = new HashTable<Integer, String>(1);
		Map<Integer, Integer> b = new HashTableMap<Integer,Integer>();
		b.put(1, 1);
		b.put(1, 2);
		System.out.println(b.get(1));
		a.put(6, "Herrera");
		a.put(7, "Sanchez");
		for(int i = 0; i < a.size; i++)
			System.out.println(i + "" + a.get(i));
	}

}
