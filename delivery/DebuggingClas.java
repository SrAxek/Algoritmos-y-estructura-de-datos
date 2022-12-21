package aed.delivery;

import es.upm.aedlib.graph.Vertex;
import es.upm.aedlib.positionlist.PositionList;

public class DebuggingClas {

	public static <V> void main(String[] args) {
		// TODO Auto-generated method stub
		Delivery a = new Delivery(new String[] { new String("Cabanillas de la Sierra"),new String("Daganzo de Arriba"),new String("San Agustin del Guadalix"),
				new String("Pelayos de la Presa") },new Integer[][] { { null,95,null,null }, { 58,null,null,null }, { 99,69,null,null }, { 26,100,81,null } });
		PositionList <Vertex<V>> tour= a.tour();
		System.out.println(tour +" " +a.length(tour));
	}

}
