package aed.hotel;

import java.util.Comparator;

import es.upm.aedlib.indexedlist.*;


/**
 * Implementa el interfaz Hotel, para realisar y cancelar reservas en un hotel,
 * y para realisar preguntas sobre reservas en vigor.
 */
public class MiHotel implements Hotel {
  /**
   * Usa esta estructura para guardar las habitaciones creados.
   */
  private IndexedList<Habitacion> habitaciones;

  /**
   * Crea una instancia del hotel. 
   */
  public MiHotel() {
    // No se debe cambiar este codigo
    this.habitaciones = new ArrayIndexedList<>();
  }
  @Override
  public void anadirHabitacion(Habitacion habitacion) throws IllegalArgumentException{
	  if(habitaciones.size()==0) habitaciones.add(0,habitacion);
	  
	  else {
		  int pos=busquedaBinaria(habitaciones,habitacion,new comparadorHabitacion());
		  
		  if(habitaciones.get(pos).compareTo(habitacion)<0) habitaciones.add(pos,habitacion);
		  else if(habitaciones.get(pos).compareTo(habitacion)>0 ) habitaciones.add(habitaciones.size(),
				  habitacion);
		  else throw new IllegalArgumentException();  
		  }
}	
  

  @Override
public boolean reservaHabitacion(Reserva reserva) throws IllegalArgumentException {
	  boolean res=false;
	  try {
		  IndexedList<Habitacion> hab = disponibilidadHabitaciones(reserva.getDiaEntrada(),
				  reserva.getDiaSalida());
		  for(int j=0;j<hab.size() && !res;j++)  if(hab.get(j).getNombre().
				  compareTo(reserva.getHabitacion())== 0) res = true;
		  
	  } catch(IllegalArgumentException e) {
		  res = true;
		  for(int j=0;j<habitaciones.size() && res;j++) {
			  if(habitaciones.get(j).getNombre().compareTo(reserva.getHabitacion())==0) {
				  res = false;
			  }
		  }
		  if(res) throw new IllegalArgumentException();
		 }
	return res;
  }
@Override
 public boolean cancelarReserva(Reserva reserva) throws IllegalArgumentException{
	  boolean res=true;
	  try {
		  for(int j=0;j<habitaciones.size();j++) {
			  for(int i=0;i<habitaciones.get(j).getReservas().size() && res;i++) {
				  if(habitaciones.get(j).getReservas().get(i).compareTo(reserva)!=0) res=false;
			  }
		  }
	  } catch(IllegalArgumentException e) {
		  for(int j=0;j<habitaciones.size();j++) {
			  if(habitaciones.get(j).getNombre().compareTo(reserva.getHabitacion())!=0) throw new IllegalArgumentException();
		  }
	  }
	  
	  return res;
 }

 @Override
 public IndexedList<Habitacion> disponibilidadHabitaciones(String diaEntrada, String diaSalida){
	  IndexedList<Habitacion> disponibles=new ArrayIndexedList<>();
	  try{ 
		for(int j=0;j<habitaciones.size();j++) {
		   for(int i=0;i<habitaciones.get(j).getReservas().size();i++) {
			   if(habitaciones.get(j).getReservas().get(i).getDiaEntrada().compareTo(diaEntrada)>=0 &&
					   habitaciones.get(j).getReservas().get(i).getDiaSalida().compareTo(diaSalida)<=0) {
				   busquedaBinaria(disponibles, disponibles.get(i), null);
				   int inicio=0;
				   int max=disponibles.size()-1;
				   boolean continuar=true;
				   while(continuar) {
					   int medio=(int)((max-inicio)/2);
					   if(disponibles.get(i).getPrecio()<(habitaciones.get(medio).getPrecio())) {
						   inicio=medio; 
					   }
					   else if(habitaciones.get(i).getPrecio()<(habitaciones.get(medio).getPrecio())){
						   max=medio;
					   }
					   else if(max==inicio){
						   disponibles.add(medio, habitaciones.get(j));
						   continuar=false;
					   }
				   }
			   }
					
				}
			}
	  }
	  catch(IllegalArgumentException e) {
		  for(int j=0;j<habitaciones.size();j++) {
			  for(int i=0;i<disponibles.size();i++) {
				  if(habitaciones.get(j).compareTo(disponibles.get(i))!=0) {
					  throw new IllegalArgumentException();
				  }
			  }
		  }
	  }
	  
	return disponibles;
 }
 /**
  * Devuelve una lista de todas las reservas de un cliente (identificado
  * por su dniPasaporte. La lista contiene pares, habitacion y fecha entrada,
  * ordenada usando las fechas de entrada (orden ascendiente).
  *
  * @param dniPasaporte la idenficacion del cliente
  * @return una lista con las reservas realisados por el cliente
  */
@Override
public IndexedList<Reserva> reservasPorCliente(String dniPasaporte) {
	IndexedList<Reserva> res=null;
	for(int i=0;i<habitaciones.size();i++) {
		for(int j=0;j<habitaciones.get(i).getReservas().size();j++) {
			if(habitaciones.get(i).getReservas().get(j).getDniPasaporte().compareTo(dniPasaporte)==0) {
			int pos=busquedaBinaria(habitaciones.get(i).getReservas(),habitaciones.get(i).getReservas().get(j), new comparadorReserva());
					res.add(pos, habitaciones.get(i).getReservas().get(j));
			}
		}
	}
	return res;
}

@Override
public IndexedList<Habitacion> habitacionesParaLimpiar(String hoyDia) {
    IndexedList<Habitacion> res = null;
    for (int i = 0; i < habitaciones.size(); i++) {
        for(int j = 0; j < habitaciones.get(i).getReservas().size();j++) {
            if(hoyDia.compareTo(habitaciones.get(i).getReservas().get(j).getDiaEntrada())>0 && hoyDia.compareTo(habitaciones.get(i).getReservas().get(j).getDiaSalida())<0) {
                int pos = busquedaBinaria(habitaciones.get(i).getReservas(),habitaciones.get(i).getReservas().get(j), new comparadorReserva());
                if(pos!=0) res.add(pos, habitaciones.get(i));
            }
        }
    }

    return res;
}

/**
 * Devuelve la reserva para una habitacion en la fecha indicada,
 * y si no habia una reserva, devuelve null. Notad que el dia de la salida no 
 * se toma en cuenta para devolver una reserva.
 *
 * @param nombreHabitacion el nombre de habitacion 
 * @param dia el dia
 * @return devuelve la reserva de la habitacion en el dia especificado
 * @throws IllegalArgumentException si la habitacion de la reserva no existe.
 */
@Override
public Reserva reservaDeHabitacion(String nombreHabitacion, String dia) throws IllegalArgumentException{
	Reserva res=null;
	for(int i=0;i<habitaciones.size();i++) {
		for(int j=0;j<habitaciones.get(i).getReservas().size();j++) {
			if(habitaciones.get(i).getNombre().compareTo(nombreHabitacion)!=0 &&
				habitaciones.get(i).getReservas().get(i).getDiaEntrada().compareTo(dia)!=0) {
				res=habitaciones.get(i).getReservas().get(j);
			}
			
		}	
	}
	if(res.getDiaEntrada().compareTo(dia)==0 || res.getHabitacion().compareTo(nombreHabitacion)==0) {
		throw new IllegalArgumentException();  
	}
	return res;
}
public <E> int busquedaBinaria(IndexedList<E> lista, E objeto, Comparator<E> comparador) throws IllegalArgumentException{
	
	boolean continuar=true;
	int res=0;
	int inicio=0;
	int max=lista.size()-1;
	int i=0;
	while(inicio<=max && continuar && i<lista.size()) {
		
		int medio=(int)((max-inicio)/2);
		try {
			
		if(comparador.compare(objeto ,lista.get(medio))>0) inicio=medio; 
		else if(comparador.compare(objeto,lista.get(medio))<0) max=medio-1;
		else if(max==inicio){
			res=medio;
			continuar=false;
		}
		else {
			res=lista.size()-1;
			continuar=false;
		}
		
		i++; 
		} catch(IllegalArgumentException e) {
			if(comparador.compare(lista.get(medio), objeto)==0) throw new IllegalArgumentException();
		}
	}
	return res;
}

static class comparadorHabitacion implements Comparator<Habitacion>{
	@Override
	public int compare(Habitacion o1, Habitacion o2) {
		// TODO Auto-generated method stub
		return o1.compareTo(o2);
	}
}
static class comparadorReserva implements Comparator<Reserva>{
	@Override
	public int compare(Reserva o1, Reserva o2) {
		// TODO Auto-generated method stub
		return o1.compareTo(o2);
	}	
}
}
 


