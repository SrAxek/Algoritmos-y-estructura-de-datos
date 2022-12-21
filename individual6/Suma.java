package aed.individual6;

import es.upm.aedlib.graph.Edge;
import es.upm.aedlib.graph.Vertex;
import es.upm.aedlib.indexedlist.ArrayIndexedList;
import es.upm.aedlib.indexedlist.IndexedList;

import java.util.Iterator;

import es.upm.aedlib.graph.DirectedGraph;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.map.HashTableMap;


public class Suma {
	public static <E> Map<Vertex<Integer>,Integer> sumVertices(DirectedGraph<Integer,E> g) {
		Map<Vertex<Integer>, Integer> res = new HashTableMap<Vertex<Integer>, Integer>();
		Iterator<Vertex<Integer>> it = g.vertices().iterator();
		
		while(it.hasNext()) {
			Vertex<Integer> e = it.next(); int a = e.element(); 
			IndexedList<Vertex<Integer>> recorrido = new ArrayIndexedList<Vertex<Integer>>(); 
			recorrido.add(recorrido.size(), e);
			a += sumaElementos(e, g, recorrido);
			res.put(e, a);
		}
		return res;
	}
	public static <E> int sumaElementos(Vertex<Integer> e, DirectedGraph<Integer,E> g, IndexedList<Vertex<Integer>> recorrido) {
		int n = 0;
		for(Edge<E> u : g.outgoingEdges(e)) {
			Vertex<Integer> a = g.endVertex(u);
			if(!contains(a, recorrido, 0)) {
				recorrido.add(recorrido.size(), a); 
				n += a.element() + sumaElementos(a,g,recorrido); 
			}
		}
		return n;
	}
	public static boolean contains(Vertex<Integer> e, IndexedList<Vertex<Integer>> recorrido, int n) {
		boolean res = false;
		if(n < recorrido.size()) {
			if(recorrido.get(n).equals(e)) return true;
			else res = contains(e,recorrido, n+1);
		}
		return res;
	}
}
