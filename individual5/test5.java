package aed.individual5;

import java.util.Iterator;

import es.upm.aedlib.Entry;
import es.upm.aedlib.Position;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;

public class test5 {

	public static void main(String[] args) {
		Iterable<Integer> a = new NodePositionList<Integer>();
		
		Iterator<Integer> it = a.iterator();
		it.next();
		Integer i = 1;
		
		TempData[] op = new TempData[11];
		op[0] = new TempData(new String("Sevilla"),111L,15);
		op[1] = new TempData(new String("La Linea"),111L,26);
		op[2] = new TempData(new String("Mostoles"),111L,11);
		op[3] = new TempData(new String("Puerto de Navacerrada"),111L,21);
		op[4] = new TempData(new String("Salamanca"),111L,10);
		op[5] = new TempData(new String("Soria"),111L,23);
		op[6] = new TempData(new String("Mulhacen"),112L,21);
		op[7] = new TempData(new String("Sevilla"),112L,14);
		op[8] = new TempData(new String("Puerto de Navacerrada"),112L,25);
		op[9] = new TempData(new String("Soria"),112L,25);
		op[10] = new TempData(new String("Ponferrada"),112L,25);
				
		PositionList<Entry<String,Integer>>[] p = Utils.clasificacion(op);
		for(int i = 0; i < p.length; i++) {
			if(p[i] != null) {
				Position<Entry<String,Integer>> it = p[i].first();
				for(int j = 0; j < p[i].size(); j++) {
					System.out.println(it.element().getKey());
					it = p[i].next(it);
				}
			}
		}
	}

}
