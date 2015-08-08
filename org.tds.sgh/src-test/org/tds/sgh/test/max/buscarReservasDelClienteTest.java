package org.tds.sgh.test.max;

import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.HabitacionDTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.HuespedDTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;
import org.tds.sgh.system.ICadenaController;
import org.tds.sgh.system.IHacerReservaController;
import org.tds.sgh.system.IIdentificarReservaClienteController;
import org.tds.sgh.system.ITomarReservaController;
import org.tds.sgh.test.TestBase;

@RunWith(JUnit4.class)
public class buscarReservasDelClienteTest extends TestBase
{
	@Test(expected=Exception.class)
	public void SistemaNoBuscaReservasDeClienteNoSeleccionado() throws Exception
	{
		ClienteDTO cliente1 = generarCliente();
		
		HotelDTO hotel1 = generarHotel();
		HotelDTO hotel2 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		TipoHabitacionDTO tipoHabitacion2 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		HabitacionDTO habitacion2 = generarHabitacion(hotel2, tipoHabitacion2);
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);
		
		cadenaController.agregarHabitacion(habitacion1);
		cadenaController.agregarHabitacion(habitacion2);
		
		
		IIdentificarReservaClienteController modificarReservaController = controllerFactory.createModificarReservaController();
		
		modificarReservaController.buscarReservasDelCliente();
	}
	
	@Test
	public void NoBuscaReservasCuandoNoHayReservasDeClienteSeleccionado() throws Exception
	{
		ClienteDTO cliente1 = generarCliente();
		
		HotelDTO hotel1 = generarHotel();
		HotelDTO hotel2 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		TipoHabitacionDTO tipoHabitacion2 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		HabitacionDTO habitacion2 = generarHabitacion(hotel2, tipoHabitacion2);
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);
		
		cadenaController.agregarHabitacion(habitacion1);
		cadenaController.agregarHabitacion(habitacion2);
		
		
		IIdentificarReservaClienteController modificarReservaController = controllerFactory.createModificarReservaController();
		
		modificarReservaController.seleccionarCliente(cliente1);
		
		List<ReservaDTO> reservas = modificarReservaController.buscarReservasDelCliente();
		
		assertTrue("Debe devolver una lista vacía de reservas ya que no hay reservas para el cliente seleccionado.",
				   reservas != null && reservas.isEmpty());
	}
		
	@Test
	public void NoBuscaReservasPasadasDeClienteSeleccionado() throws Exception
	{
		ClienteDTO cliente1 = generarCliente();
		
		HotelDTO hotel1 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		GregorianCalendar fechaHoy1 = calendario.getHoy();
		
		GregorianCalendar fecha1 = generarFecha(fechaHoy1);
		GregorianCalendar fecha2 = generarFecha(fecha1);
		
		GregorianCalendar fechaHoy2 = generarFecha(fecha2);
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		
		this.cambiarHoyEnCalendario(fechaHoy1);
		
		IHacerReservaController hacerReservaController = controllerFactory.createHacerReservaController();
		
		hacerReservaController.seleccionarCliente(cliente1);
		
		hacerReservaController.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		
		this.cambiarHoyEnCalendario(fechaHoy2);

		IIdentificarReservaClienteController modificarReservaController = controllerFactory.createModificarReservaController();
		
		modificarReservaController.seleccionarCliente(cliente1);
		
		List<ReservaDTO> reservas = modificarReservaController.buscarReservasDelCliente();
		
		assertTrue("Debe devolver una lista vacía de reservas ya que la reserva existente está en el pasado.",
				   reservas != null && reservas.isEmpty());
	}
	
	@Test
	public void BuscarReservasFuturasDeClienteSeleccionado() throws Exception
	{
		ClienteDTO cliente1 = generarCliente();
		
		HotelDTO hotel1 = generarHotel();
		HotelDTO hotel2 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		TipoHabitacionDTO tipoHabitacion2 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		HabitacionDTO habitacion2 = generarHabitacion(hotel2, tipoHabitacion2);
		
		GregorianCalendar fecha1 = generarFecha();
		GregorianCalendar fecha2 = generarFecha(fecha1);
		GregorianCalendar fecha3 = generarFecha();
		GregorianCalendar fecha4 = generarFecha(fecha3);
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);
		
		cadenaController.agregarHabitacion(habitacion1);
		cadenaController.agregarHabitacion(habitacion2);
		
		
		IHacerReservaController hacerReservaController = controllerFactory.createHacerReservaController();
		
		hacerReservaController.seleccionarCliente(cliente1);
		
		hacerReservaController.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		

		hacerReservaController = controllerFactory.createHacerReservaController();
		
		hacerReservaController.seleccionarCliente(cliente1);
		
		hacerReservaController.registrarReserva(hotel2, tipoHabitacion2, fecha3, fecha4, true);
		

		IIdentificarReservaClienteController modificarReservaController = controllerFactory.createModificarReservaController();
		
		modificarReservaController.seleccionarCliente(cliente1);
		
		List<ReservaDTO> reservas = modificarReservaController.buscarReservasDelCliente();
		
		assertTrue("Debe devolver una lista con las dos reservas registradas.",
				   reservas != null && reservas.size() == 2);
	}

	@Test
	public void NoBuscaReservasQueNoEstanPendientes() throws Exception
	{
		ClienteDTO cliente1 = generarCliente();
		
		HotelDTO hotel1 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		HuespedDTO huesped1 = generarHuesped();
		
		GregorianCalendar fecha1 = generarFecha();
		GregorianCalendar fecha2 = generarFecha(fecha1);
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		

		IHacerReservaController hacerReservaController1 = controllerFactory.createHacerReservaController();
		
		hacerReservaController1.seleccionarCliente(cliente1);
		
		ReservaDTO reservaX = hacerReservaController1.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		
		
		this.cambiarHoyEnCalendario(fecha1);
		
		ITomarReservaController tomarReservaController2 = controllerFactory.createTomarReservaController();
		
		tomarReservaController2.seleccionarCliente(cliente1);
		
		tomarReservaController2.seleccionarReserva(reservaX.getCodigo());
		
		tomarReservaController2.registrarHuesped(huesped1);
		
		tomarReservaController2.tomarReserva();
		
		

		IIdentificarReservaClienteController modificarReservaController3 = controllerFactory.createModificarReservaController();
		
		modificarReservaController3.seleccionarCliente(cliente1);
		
		List<ReservaDTO> reservas = modificarReservaController3.buscarReservasDelCliente();
		
		assertTrue("Debe devolver una lista vacía de reservas ya que la reserva existente está tomada.",
				   reservas != null && reservas.isEmpty());
	}
}
