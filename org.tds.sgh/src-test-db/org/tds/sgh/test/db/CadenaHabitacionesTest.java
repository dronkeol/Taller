package org.tds.sgh.test.db;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.dtos.HabitacionDTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;
import org.tds.sgh.system.ICadenaController;

@RunWith(JUnit4.class)
public class CadenaHabitacionesTest extends DbTestBase
{
	@Test
	public void HabitacionesDeHotelNoEsNull() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		
		assertTrue("El controlador devolvió null. Debe devolver una lista de habitaciones.",
				   cadenaController.getHabitaciones(hotel1) != null);
	}
	
	@Test
	public void HabitacionesDeHotelEsVaciaAlInicio() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		
		assertTrue("El controlador no devolvió una lista de habitaciones vacía al inicio.",
				   cadenaController.getHabitaciones(hotel1).isEmpty());
	}
	
	@Test(expected=Exception.class)
	public void CadenaNoEntregaHabitacionesDeHotelInexistente() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		
		cadenaController.getHabitaciones(hotel1);
	}
	
	@Test
	public void CadenaRegistraHabitacion() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);

		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		
		List<HabitacionDTO> habitaciones = cadenaController.getHabitaciones(hotel1);
		
		assertTrue("El sistema devolvió null. Debe devolver la lista de habitaciones del hotel.",
				   habitaciones != null);
		
		assertTrue("El sistema devolvió una lista con más de una habitación y solo hay una habitación registrada.",
				   habitaciones.size() == 1);
		
		HabitacionDTO habitacionX = habitaciones.get(0);
		
		assertTrue("El sistema devolvió null. Debe devolver los datos de la habitación registrada.",
				   habitacionX != null);
		
		assertTrue("El sistema registró datos de la habitación que no coinciden con los datos provistos al momento de registrar la habiración.",
				   habitacionX.equals(habitacion1));
	}
	
	@Test(expected=Exception.class)
	public void CadenaNoRegistraHabitacionDeHotelInexistente() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);

		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
	}
	
	@Test(expected=Exception.class)
	public void CadenaNoRegistraHabitacionDeTipoHabitacionInexistente() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarHabitacion(habitacion1);
	}
	
	@Test(expected=Exception.class)
	public void CadenaNoRegistraDosHabitacionesIgualHotelIgualTipoIgualNombre() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = generarHabitacion(hotel1, tipoHabitacion1, habitacion1.getNombre());
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);

		cadenaController.agregarHabitacion(habitacion1);
		
		cadenaController.agregarHabitacion(habitacion2);
	}

	@Test
	public void CadenaRegistraDosHabitacionesIgualHotelIgualTipoDistintoNombre() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = generarHabitacion(hotel1, tipoHabitacion1);
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);

		cadenaController.agregarHabitacion(habitacion1);
		
		cadenaController.agregarHabitacion(habitacion2);
		
		
		List<HabitacionDTO> habitaciones = cadenaController.getHabitaciones(hotel1);
		
		assertTrue("El sistema no registró las dos habitaciones del hotel.",
				   habitaciones.size() == 2);
		
		HabitacionDTO habitacionX = habitaciones.get(0);
		
		assertTrue("El sistema no registró una de las habitaciones.",
				   habitacionX.equals(habitacion1) || habitacionX.equals(habitacion2));
		
		HabitacionDTO habitacionY = habitaciones.get(1);
		
		assertTrue("El sistema no registró una de las habitaciones.",
				   habitacionY.equals(habitacion1) || habitacionY.equals(habitacion2));
	}
	
	@Test(expected=Exception.class)
	public void CadenaNoRegistraDosHabitacionesIgualHotelDistintoTipoIgualNombre() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		TipoHabitacionDTO tipoHabitacion2 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = generarHabitacion(hotel1, tipoHabitacion2, habitacion1.getNombre());
		

		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);

		cadenaController.agregarHabitacion(habitacion1);
		
		cadenaController.agregarHabitacion(habitacion2);
	}
	
	@Test
	public void CadenaRegistraDosHabitacionesIgualHotelDistintoTipoDistintoNombre() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();

		TipoHabitacionDTO tipoHabitacion2 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = generarHabitacion(hotel1, tipoHabitacion2);
		

		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);

		cadenaController.agregarHabitacion(habitacion1);
		
		cadenaController.agregarHabitacion(habitacion2);
		
		
		List<HabitacionDTO> habitaciones = cadenaController.getHabitaciones(hotel1);
		
		assertTrue("El sistema no registró las dos habitaciones del hotel.",
				   habitaciones.size() == 2);
		
		HabitacionDTO habitacionX = habitaciones.get(0);
		
		assertTrue("El sistema no registró una de las habitaciones.",
				   habitacionX.equals(habitacion1) || habitacionX.equals(habitacion2));
		
		HabitacionDTO habitacionY = habitaciones.get(1);
		
		assertTrue("El sistema no registró una de las habitaciones.",
				   habitacionY.equals(habitacion1) || habitacionY.equals(habitacion2));
	}
	
	@Test
	public void CadenaRegistraDosHabitacionesDistintoHotelIgualTipoIgualNombre() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		HotelDTO hotel2 = generarHotel();

		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = generarHabitacion(hotel2, tipoHabitacion1, habitacion1.getNombre());
		

		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		cadenaController.agregarHabitacion(habitacion2);
		
		
		List<HabitacionDTO> habitaciones1 = cadenaController.getHabitaciones(hotel1);
		
		assertTrue("El sistema no registró la habitación de uno de los hoteles.",
				   habitaciones1.size() == 1);
		
		HabitacionDTO habitacionX = habitaciones1.get(0);
		
		assertTrue("El sistema no registró una de las habitaciones.",
				   habitacionX.equals(habitacion1));

		List<HabitacionDTO> habitaciones2 = cadenaController.getHabitaciones(hotel2);

		assertTrue("El sistema no registró la habitación de uno de los hoteles.",
				   habitaciones2.size() == 1);
		
		HabitacionDTO habitacionY = habitaciones2.get(0);
		
		assertTrue("El sistema no registró una de las habitaciones.",
				   habitacionY.equals(habitacion2));
	}

	@Test
	public void CadenaRegistraDosHabitacionesDistintoHotelIgualTipoDistintoNombre() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		HotelDTO hotel2 = generarHotel();

		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = generarHabitacion(hotel2, tipoHabitacion1);
		

		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		cadenaController.agregarHabitacion(habitacion2);
		
		
		List<HabitacionDTO> habitaciones1 = cadenaController.getHabitaciones(hotel1);
		
		assertTrue("El sistema no registró la habitación de uno de los hoteles.",
				   habitaciones1.size() == 1);
		
		HabitacionDTO habitacionX = habitaciones1.get(0);
		
		assertTrue("El sistema no registró una de las habitaciones.",
				   habitacionX.equals(habitacion1));

		List<HabitacionDTO> habitaciones2 = cadenaController.getHabitaciones(hotel2);

		assertTrue("El sistema no registró la habitación de uno de los hoteles.",
				   habitaciones2.size() == 1);
		
		HabitacionDTO habitacionY = habitaciones2.get(0);
		
		assertTrue("El sistema no registró una de las habitaciones.",
				   habitacionY.equals(habitacion2));
	}
	
	@Test
	public void CadenaRegistraDosHabitacionesDistintoHotelDistintoTipoIgualNombre() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		HotelDTO hotel2 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();

		TipoHabitacionDTO tipoHabitacion2 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = generarHabitacion(hotel2, tipoHabitacion2, habitacion1.getNombre());
		

		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);

		cadenaController.agregarHabitacion(habitacion1);
		
		cadenaController.agregarHabitacion(habitacion2);
		
		
		List<HabitacionDTO> habitaciones1 = cadenaController.getHabitaciones(hotel1);
		
		assertTrue("El sistema no registró la habitación de uno de los hoteles.",
				   habitaciones1.size() == 1);
		
		HabitacionDTO habitacionX = habitaciones1.get(0);
		
		assertTrue("El sistema no registró una de las habitaciones.",
				   habitacionX.equals(habitacion1));

		List<HabitacionDTO> habitaciones2 = cadenaController.getHabitaciones(hotel2);

		assertTrue("El sistema no registró la habitación de uno de los hoteles.",
				   habitaciones2.size() == 1);
		
		HabitacionDTO habitacionY = habitaciones2.get(0);
		
		assertTrue("El sistema no registró una de las habitaciones.",
				   habitacionY.equals(habitacion2));
	}
	
	@Test
	public void CadenaRegistraDosHabitacionesDistintoHotelDistintoTipoDistintoNombre() throws Exception
	{
		HotelDTO hotel1 = generarHotel();
		
		HotelDTO hotel2 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();

		TipoHabitacionDTO tipoHabitacion2 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = generarHabitacion(hotel2, tipoHabitacion2);
		

		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);

		cadenaController.agregarHabitacion(habitacion1);
		
		cadenaController.agregarHabitacion(habitacion2);
		
		
		List<HabitacionDTO> habitaciones1 = cadenaController.getHabitaciones(hotel1);
		
		assertTrue("El sistema no registró la habitación de uno de los hoteles.",
				   habitaciones1.size() == 1);
		
		HabitacionDTO habitacionX = habitaciones1.get(0);
		
		assertTrue("El sistema no registró una de las habitaciones.",
				   habitacionX.equals(habitacion1));

		List<HabitacionDTO> habitaciones2 = cadenaController.getHabitaciones(hotel2);

		assertTrue("El sistema no registró la habitación de uno de los hoteles.",
				   habitaciones2.size() == 1);
		
		HabitacionDTO habitacionY = habitaciones2.get(0);
		
		assertTrue("El sistema no registró una de las habitaciones.",
				   habitacionY.equals(habitacion2));
	}
}
