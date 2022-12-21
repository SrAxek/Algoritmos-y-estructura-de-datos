package aed.urgencias;

import java.util.Iterator;

import aed.urgencias.TesterLab6.AdmitirPaciente;
import aed.urgencias.TesterLab6.AtenderPaciente;
import aed.urgencias.TesterLab6.AumentaPrioridad;
import aed.urgencias.TesterLab6.CambiarPrioridad;
import aed.urgencias.TesterLab6.GetPaciente;
import aed.urgencias.TesterLab6.InformacionEspera;
import aed.urgencias.TesterLab6.PacientesEsperando;
import aed.urgencias.TesterLab6.SalirPaciente;
import es.upm.aedlib.Entry;
import es.upm.aedlib.Pair;
import es.upm.aedlib.indexedlist.ArrayIndexedList;
import es.upm.aedlib.indexedlist.IndexedList;
import es.upm.aedlib.priorityqueue.HeapPriorityQueue;
import es.upm.aedlib.priorityqueue.PriorityQueue;

public class Main {

	public static void main(String[] args) throws PacienteExisteException, PacienteNoExisteException {
		
		PriorityQueue<Integer,Paciente> cola = new HeapPriorityQueue<Integer, Paciente>(); Entry<Integer, Integer> a;
		
		cola.enqueue(1, new Paciente("1", 1, 1, 1)); cola.enqueue(2 ,new Paciente("2", 2, 2, 2)); 
		cola.replaceKey(cola.first(), 3);
		
		Iterator<Entry<Integer, Paciente>> iterator = cola.iterator();
		while(iterator.hasNext()) System.out.println(iterator.next());

		
		
		
		/**
		Urgencias e = new UrgenciasAED(); 
		
		System.out.println("----");
		
		Iterator<Paciente> u = e.pacientesEsperando().iterator();
		int n = 0; int i = 1; 
		
		while(u.hasNext()) {
			if(i == Math.pow(2,n)) {
				System.out.println(u.next());
				n++; i = 1;
			}
			else {
				System.out.print(u.next() + " "); i++;
			}
		}
		**/
	}

}
