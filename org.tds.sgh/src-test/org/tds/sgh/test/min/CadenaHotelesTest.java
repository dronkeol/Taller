package org.tds.sgh.test.min;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.system.ICadenaController;
import org.tds.sgh.test.TestBase;

@RunWith(JUnit4.class)
public class CadenaHotelesTest extends TestBase
{
	@Test
	public void HotelesNoEsNull()
	{
		ICadenaController controller = controllerFactory.createCadenaController();
	
		
		assertTrue("El controlador devolvió null. Debe devolver una lista de hoteles.",
				   controller.getHoteles() != null);
	}
	
	@Test
	public void HotelesEsVacio()
	{
		ICadenaController controller = controllerFactory.createCadenaController();
		
		
		assertTrue("El controlador no devolvió una lista de hoteles vacía al inicio.",
				   controller.getHoteles().isEmpty());
	}

	@Test
	public void CadenaRegistraHotel() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		
		ICadenaController controller = controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		
		List<HotelDTO> hoteles = controller.getHoteles();
		
		assertTrue("El sistema no registró al hotel",
				   !hoteles.isEmpty());
		
		assertTrue("El sistema registró más de un hotel.",
				   hoteles.size() == 1);
		
		HotelDTO hotelX = hoteles.get(0);
		
		assertTrue("El sistema devolvió null. Debe devolver los datos del hotel registrado.",
				   hotelX != null);
		
		assertTrue("El sistema reutilizó instancias de test; el sistema debe preservar su propia información.",
				   hotelX != hotel1);
		
		assertTrue("El sistema registró datos del hotel que no coinciden con los datos provistos al momento de registrar al hotel.",
				   hotelX.equals(hotel1));
	}
	
	@Test
	public void CadenaRegistraDosHoteles() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		HotelDTO hotel2 = generarHotel();
		
		
		ICadenaController controller = controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		controller.agregarHotel(hotel2);
		
		
		List<HotelDTO> hoteles = controller.getHoteles();
		
		assertTrue("El sistema no devolvió los dos hoteles registrados.",
				   hoteles.size() == 2);
		
		HotelDTO hotelX = hoteles.get(0);
		
		HotelDTO hotelY = hoteles.get(1);
		
		assertTrue("El sistema devolvió datos de hoteles que no coinciden con los registrados",
				   (hotelX.equals(hotel1) || hotelX.equals(hotel2)) &&
				   (hotelY.equals(hotel1) || hotelY.equals(hotel2)));
	}

	@Test(expected=Exception.class)
	public void CadenaNoRegistraDosHotelesConElMismoNombre() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		HotelDTO hotel2 = generarHotel(hotel1.getNombre());
		
		
		ICadenaController controller = controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		controller.agregarHotel(hotel2);
	}
	
	@Test
	public void CadenaNoPierdeInformacionDeHoteles() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
	
		
		ICadenaController controller1 = controllerFactory.createCadenaController();
		
		assertTrue("El controlador no devolvió una lista de hoteles vacía al inicio.",
				   controller1.getHoteles().isEmpty());
				
		controller1.agregarHotel(hotel1);
		
		assertTrue("El sistema no registró al hotel.",
				   !controller1.getHoteles().isEmpty());
		
		assertTrue("El sistema registró más de un hotel.",
				   controller1.getHoteles().size() == 1);
		
		assertTrue("El sistema registró datos del hotel que no coinciden con los datos provistos al momento de registrar al hotel.",
				   controller1.getHoteles().get(0).equals(hotel1));
				

		ICadenaController controller2 = controllerFactory.createCadenaController();

		assertTrue("El sistema no registró al hotel. Usando un segundo controlador, éste debe devolver el hotel registrado mediante el primer controlador.",
				   !controller2.getHoteles().isEmpty());
		
		assertTrue("El sistema registró más de un hotel. Usando un segundo controlador, éste debe devolver el único hotel registrado mediante el primer controlador.",
				   controller1.getHoteles().size() == 1);

		assertTrue("El sistema registró datos del hotel que no coinciden con los datos provistos. Los datos del hotel que devuelve el segundo controlador deben ser los mismos que los registrados mediante el primer controlador.",
				   controller2.getHoteles().get(0).equals(hotel1));
	}
}
