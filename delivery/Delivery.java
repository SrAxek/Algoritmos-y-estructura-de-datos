package aed.delivery;

import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.graph.DirectedGraph;
import es.upm.aedlib.graph.DirectedAdjacencyListGraph;
import es.upm.aedlib.graph.Vertex;
import es.upm.aedlib.graph.Edge;
import es.upm.aedlib.map.HashTableMap;
import es.upm.aedlib.set.HashTableMapSet;
import es.upm.aedlib.set.Set;
import java.util.Iterator;
	
public class Delivery<V> {
	DirectedAdjacencyListGraph<V,Integer> graph = new DirectedAdjacencyListGraph<V,Integer>() ;
	// Construct a graph out of a series of vertices and an adjacency matrix.
	// There are 'len' vertices. A negative number means no connection. A non-negative
	// number represents distance between nodes.
	public Delivery(V[] places, Integer[][] gmat) {
		for(V u : places) graph.insertVertex(u);	
		// Introducimos los vertices en el grafo.
		Iterator<Vertex<V>> it1 = graph.vertices().iterator(); Iterator<Vertex<V>> it2 = graph.vertices().iterator();
		for(Integer i = 0; i < gmat.length; i++) {
			Vertex<V> e1 = it1.next();
			for(Integer j = 0; j < gmat[i].length; j++) {
				Vertex<V> e2 = it2.next(); Integer n = gmat[i][j];
				if(n != null) graph.insertDirectedEdge(e1, e2, n);	
				// Si el valor que representa la unión de e1 y e2 es distinto de null, insertamos una arista entre e1 y e2 con el valor de n.
			}
			it2 = graph.vertices().iterator();
		}
	}
  
	// Just return the graph that was constructed
	public DirectedGraph<V, Integer> getGraph() {
		return graph;
	}

	// Return a Hamiltonian path for the stored graph, or null if there is none.
	// The list containts a series of vertices, with no repetitions (even if the path
	// can be expanded to a cycle).
	public PositionList <Vertex<V>> tour() {
		PositionList<Vertex<V>> res = new NodePositionList<Vertex<V>>();
		Iterator<Vertex<V>> iterator = graph.vertices().iterator();
		while(iterator.hasNext() && res.size() < graph.numVertices()) {
			// Para cada uno de los vertices buscamos si hay un camino hamiltoniano mediante el método auxiliar recorrido.
			res = new NodePositionList<Vertex<V>>(); Vertex<V> v=iterator.next(); res.addLast(v); res = recorrido(res, v);
			// Si se verifica que es en verdad un camino hamiltoniano, terminamos el bucle y devolvemos el resultado (o si no quedan vertices).
		}
		if(res.size() < graph.numVertices()) return null;	// Si no es un camino hamiltoniano devolvemos null.
		return res;
	}
	public PositionList<Vertex<V>> recorrido(PositionList<Vertex<V>> lista, Vertex<V> v){
		Iterator<Edge<Integer>> it = graph.outgoingEdges(v).iterator(); boolean salida = false; 
		/** Empleamos un método recursivo en el que, al recorrer las aristas del último vértice, si el endVertex no se encuentra en la lista,
		 * pues añadimos el vértice a la lista y ejecutamos el método con esta nueva arista.
		 */
		
		while(it.hasNext() && !salida) {
			Edge<Integer> e = it.next(); Vertex<V> vertex = graph.endVertex(e); PositionList<Vertex<V>> copia = new NodePositionList<Vertex<V>>(lista);
			if(!contains(lista, vertex)) {
				lista.addLast(vertex); lista = recorrido(lista, vertex);
				if(lista.size() == graph.numVertices()) salida = true;
				else lista = copia;
				// Si la lista hallada no es hamiltoniana, devolvemos la lista a su valor anterior.
			} 
		}
		return lista;
	}
	
	public boolean contains(PositionList<Vertex<V>> lista, Vertex<V> v) {
		boolean res = false; Iterator<Vertex<V>> iterator = lista.iterator();
		while(iterator.hasNext() && !res) if(iterator.next().equals(v)) res = true;
		return res;
	}

	public int length(PositionList<Vertex<V>> path) {
		// Vamos recorriendo path y sumando los valores de las aristas que los unen mediante el método auxiliar edgeValue.
		int res = 0; Position<Vertex<V>> u = path.next(path.first());
		while(u != null) {
			res += edgeValue(path.prev(u).element(), u.element());
			u = path.next(u);
		}
		return res;
	}
	/** Vamos recorriendo las aristas salientes de e1 y las entrantes de e2 hasta encontrar la indicada (la que es la misma), 
	 * en cuyo caso devolvemos su valor.
	 */
	public int edgeValue(Vertex<V> e1, Vertex<V> e2) {
		Iterator<Edge<Integer>> it1 = graph.outgoingEdges(e1).iterator(); Iterator<Edge<Integer>> it2 = graph.incomingEdges(e2).iterator();
		while(it1.hasNext()) {
			Edge<Integer> u1 = it1.next();
			while(it2.hasNext()) {
				Edge<Integer> u2 = it2.next();
				if(u1.equals(u2)) return u1.element();
			}
			it2 = graph.incomingEdges(e2).iterator();
		}
		return 0;
	}

	public String toString() {
		return "Delivery";
	}
}
