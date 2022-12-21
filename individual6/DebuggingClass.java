package aed.individual6;

import es.upm.aedlib.graph.DirectedAdjacencyListGraph;
import es.upm.aedlib.graph.Vertex;

public class DebuggingClass {
	public static void main(String[] args) {
		DirectedAdjacencyListGraph<Integer,Integer> graph = new DirectedAdjacencyListGraph<Integer,Integer>() ;
		Vertex<Integer> v_0 = graph.insertVertex(2) ;
		Vertex<Integer> v_1 = graph.insertVertex(4) ;
		Vertex<Integer> v_2 = graph.insertVertex(2) ;
		Vertex<Integer> v_3 = graph.insertVertex(2) ;
		Vertex<Integer> v_4 = graph.insertVertex(3) ;
		graph.insertDirectedEdge(v_4,v_1,null) ;
		graph.insertDirectedEdge(v_4,v_2,null) ;
		graph.insertDirectedEdge(v_3,v_0,null) ;
		graph.insertDirectedEdge(v_2,v_1,null) ;
		graph.insertDirectedEdge(v_2,v_2,null) ;
		graph.insertDirectedEdge(v_1,v_3,null) ;
		graph.insertDirectedEdge(v_0,v_2,null) ;
		
		System.out.println(Suma.sumVertices(graph));
	}
}
