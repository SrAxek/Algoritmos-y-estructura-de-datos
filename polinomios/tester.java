package aed.polinomios;

import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.NodePositionList;

public class tester {

	public static void main(String[] args) {
		
		Polinomio prueba1 = new Polinomio();
		
		prueba1.terms.addFirst(new Monomio(1,1));
		prueba1.terms.addFirst(new Monomio(4,0));
		
		Polinomio prueba2 = new Polinomio();
		prueba2.terms.addFirst(new Monomio(5,0));
		
		Polinomio prueba3 = Polinomio.suma(prueba1, prueba2);
		Position<Monomio> cursor = prueba3.terms.first();
		
		while(cursor != null) {
			System.out.println(cursor.element().getCoeficiente() + " "
					+ cursor.element().getExponente());
			cursor = prueba3.terms.next(cursor);
		}
	}

}
