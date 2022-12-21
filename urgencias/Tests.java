package aed.urgencias;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {

  @Test
  public void test_01() throws PacienteExisteException {
    Urgencias u = new UrgenciasAED();
    u.admitirPaciente("111", 5, 1);
    Paciente p = u.atenderPaciente(10);

    // Check expected DNI ("111") == observed DNI (p.getDNI())
    assertEquals("111", p.getDNI());
  }
  
  @Test
  public void test_02() throws PacienteExisteException {
	  Urgencias u = new UrgenciasAED();
	  u.admitirPaciente("111", 5, 1);
	  u.admitirPaciente("222", 5, 1);
	  Paciente p = u.atenderPaciente(1);
	  
	  assertEquals("111", p.getDNI());
  }
  
  @Test
  public void test_03() throws PacienteExisteException{
	  Urgencias u = new UrgenciasAED();
	  u.admitirPaciente("111", 5, 1);
	  u.admitirPaciente("222", 5, 1);
	  u.atenderPaciente(1); 
	  Paciente p = u.atenderPaciente(1);
	  assertEquals("222", p.getDNI());
  }
  
  @Test
  public void test_04() throws PacienteExisteException{
	  Urgencias u = new UrgenciasAED();
	  u.admitirPaciente("111", 5, 1);
	  u.admitirPaciente("222", 1, 1);
	  Paciente p = u.atenderPaciente(1);
	  
	  assertEquals("222", p.getDNI());
  }
  
  @Test
  public void test_05() throws PacienteExisteException, PacienteNoExisteException {
	  Urgencias u = new UrgenciasAED();
	  u.admitirPaciente("111", 5, 1);
	  u.admitirPaciente("222", 5, 1);
	  Paciente p = u.salirPaciente("111",1);
	  
	  assertEquals(u.atenderPaciente(1), new Paciente("222", 5, 1, 1));
  }
  @Test
  public void test_06() throws PacienteExisteException, PacienteNoExisteException {
	  Urgencias u = new UrgenciasAED();
	  u.admitirPaciente("111", 5, 1);
	  u.admitirPaciente("222", 5, 1);
	  u.cambiarPrioridad("222", 1, 1);
	  
	  assertEquals(u.atenderPaciente(1), new Paciente("222", 1, 1, 1));
	  
  }
  
}

