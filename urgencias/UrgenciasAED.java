package aed.urgencias;

import es.upm.aedlib.Pair;
import es.upm.aedlib.indexedlist.ArrayIndexedList;
import es.upm.aedlib.indexedlist.IndexedList;

public class UrgenciasAED implements Urgencias{
	
	IndexedList<Paciente> lista; int tiempoEsperado = 0; int pacientesAtendidos = 0; 
	public UrgenciasAED() {
		lista = new ArrayIndexedList<Paciente>();
		
	}
	// Añadimos un paciente a la lista
	public Paciente admitirPaciente(String DNI, int prioridad, int hora) throws PacienteExisteException{
		Paciente res = new Paciente(DNI, prioridad, hora, hora); 
		if(posicionPaciente(res) != -1) throw new PacienteExisteException();
		
		//Una vez que confirmamos que el paciente no existe en la cola lo insertamos en orden ascendiente
		if(lista.size() == 0) lista.add(0, res); 
		else {
			int posHijo = lista.size(); lista.add(posHijo, res); 
			reordenarNivel(posicionPaciente(res));posHijo = posicionPaciente(res);
			upHeapBubbling((posHijo-1)/2,posHijo, res);
		}
		return res;
	}
	// Ordenamos en orden ascendiente la cola para verificar el heap-order priority	
	public void upHeapBubbling(int posPadre, int posHijo, Paciente res) {
		if(posPadre == 0 && lista.get(posPadre).getPrioridad() > lista.get(posHijo).getPrioridad()) {
			lista.set(posHijo, lista.get(posPadre)); lista.set(0, res); return;
		}
		if (posPadre > 0) {
			int pos = nivel(posPadre);
			if(pos > 0 && lista.get(pos).getPrioridad() > lista.get(posHijo).getPrioridad()) {
				lista.set(posHijo, lista.get(pos)); lista.set(pos, res);
				reordenarNivel(pos); pos = posicionPaciente(res);
				System.out.println(res + " " + pos);
				posPadre = (pos -1)/2; posHijo = pos; 
				upHeapBubbling(posPadre, posHijo, res);
			}
		}
	}
	
	// Devuelve la posición del último valor del nivel en el que se halla pos
	public int nivel(int pos) {
		int n = 1; boolean salida = true; int nivel = (int)Math.pow(2,n);int valor = 0;
		while(salida) {
			if(pos <= nivel) {
				valor = nivel; salida = false;
			} nivel += (int)Math.pow(2,n + 1); n++;
		} return valor;
	}
	
	// reordena un nivel en función de la prioridad
	public void reordenarNivel(int pos) {
		if(pos == 0) return;
		int valor = nivel(pos); int a = valor; int b = 1; int max = valor; 
		while(a != 0) {
			a -= Math.pow(2,b); b++;
		} b--; valor -= Math.pow(2,b); valor ++;
		
		for(int i = valor; i <= max && i < lista.size() && i >= 1; i++) {
			for(int j = valor; j < i; j++) {
				if(lista.get(j).getPrioridad() > lista.get(i).getPrioridad()) {
					Paciente p = lista.get(j); lista.set(j, lista.get(i)); lista.set(i, p);
				}
			}
		}
	}
	
	// Sacamos a un paciente de la lista
	public Paciente salirPaciente(String DNI, int hora) throws PacienteNoExisteException{
		Paciente res = new Paciente(DNI, 0, hora, hora); int posPadre = posicionPaciente(res);
		if(posPadre == -1) throw new PacienteNoExisteException();
		res = lista.get(posPadre); lista.removeElementAt(posPadre);
		return res;
	}
	
	// Atiende a un paciente
	public Paciente atenderPaciente(int hora) {
		if(!lista.isEmpty()) {
			Paciente res = lista.get(0); 
			try { salirPaciente(res.getDNI(),res.getTiempoAdmision());}
			catch (PacienteNoExisteException e1) { e1.printStackTrace();}
			tiempoEsperado += hora - res.getTiempoAdmision(); pacientesAtendidos++; return res;
		} else return null;
	}
	
	// Cambia la prioridad de un paciente
	public Paciente cambiarPrioridad(String DNI, int nuevaPrioridad, int hora) throws PacienteNoExisteException{
		Paciente res = new Paciente(DNI, nuevaPrioridad, 0, hora); int pos = posicionPaciente(res);
		if(pos == -1) throw new PacienteNoExisteException(); 
		boolean prioridadCambiada = false;
		if(hora >= lista.get(pos).getTiempoAdmision()) {
			prioridadCambiada = true; res = new Paciente(DNI, nuevaPrioridad, lista.get(pos).getTiempoAdmision(), hora);
		}
		if(prioridadCambiada) {
			lista.set(pos,res); 
			reordenarNivel(pos); pos = posicionPaciente(res);
			upHeapBubbling((pos-1)/2, pos, res); pos = posicionPaciente(res); downHeapBubbling(pos); pos = posicionPaciente(res);
			reordenarNivel(pos);
		}
		return res;
	}
	
	// Ordenamos en orden descendiente para verificar el down-heap priority
	public void downHeapBubbling(int pos) {
		reordenarNivel(pos);
		int a = nivel(pos) +1;
		if(pos >0 && a < lista.size() && lista.get(pos).getPrioridad() > lista.get(a).getPrioridad() ) {
			Paciente p = lista.get(pos); lista.set(pos, lista.get(a)); lista.set(a, p);
			downHeapBubbling(a);
		}
		else if(pos == 0 && 1 < lista.size() &&  lista.get(0).getPrioridad() > lista.get(1).getPrioridad()) {
			Paciente p = lista.get(0); lista.set(0, lista.get(1)); lista.set(1, p);
			downHeapBubbling(1);
		}
	}
	
	// Devuelve un paciente mediante su DNI
	public Paciente getPaciente(String DNI) {
		if(!lista.isEmpty() && posicionPaciente(new Paciente(DNI, 0,0,0)) != -1) 
			return lista.get(posicionPaciente(new Paciente(DNI, 0,0,0)));
		else return null;
	}
	
	// Devuelve un pair con el tiempo esperado total y la cantidad de pacientes atendidos.
	public Pair<Integer, Integer> informacionEspera(){ return new Pair<Integer, Integer>(tiempoEsperado, pacientesAtendidos);}
	
	// Aumenta la prioridad de todos los pacientes que hayan esperado mas que macTiempoEspera
	public void aumentaPrioridad(int macTiempoEspera, int hora){ 
		IndexedList<Paciente> a = new ArrayIndexedList<Paciente>(); int i = 0;
		for(Paciente e : lista) {
			if(hora - e.getTiempoAdmision() > macTiempoEspera) {
				a.add(i, e); i++;
			}
		}
		for(Paciente e: a) {
			if(e.getPrioridad() > 0) {
				try {
					cambiarPrioridad(e.getDNI(), e.getPrioridad()-1, hora);
				} catch (PacienteNoExisteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				try {
					cambiarPrioridad(e.getDNI(), e.getPrioridad(), hora);
				} catch (PacienteNoExisteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	// Devuelve un iterable de la cola
	public Iterable<Paciente> pacientesEsperando(){ return lista; }
	// Devuelve la posicion de un paciente en concreto (int)
	public int  posicionPaciente(Paciente paciente) {
		for(int i = 0; i < lista.size(); i++) {
			if(paciente.equals(lista.get(i))) return i;
		}
		return -1;
	}
}
