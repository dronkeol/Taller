package org.tds.sgh.test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.HabitacionDTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.HuespedDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;
import org.tds.sgh.infrastructure.Infrastructure;
import org.tds.sgh.system.ControllerFactory;
import org.tds.sgh.system.IControllerFactory;
import org.tds.sgh.test.stubs.CalendarioStub;
import org.tds.sgh.test.stubs.SistemaFacturacionStub;
import org.tds.sgh.test.stubs.SistemaMensajeriaStub;

@RunWith(JUnit4.class)
public abstract class TestBase
{
	// Data -------------------------------------------------------------------
	
	private static Random random = new Random();
	
	private static AtomicInteger lastId = new AtomicInteger(0);
	
	
	// Attributes (protected) -------------------------------------------------
	
	protected CadenaHotelera cadenaHotelera;

	protected CalendarioStub calendario;
	
	protected Consumer<GregorianCalendar> calendarioCambiarHoy;

	protected IControllerFactory controllerFactory;

	protected SistemaMensajeriaStub sistemaMensajeria;
	
	protected SistemaFacturacionStub sistemaFacturacion;
	
	
	// Setup ------------------------------------------------------------------
	
	@Before
	public void setUp() throws Exception
	{
		calendario = new CalendarioStub(c -> calendarioCambiarHoy = c);
		
		sistemaMensajeria = new SistemaMensajeriaStub();
		
		sistemaFacturacion = new SistemaFacturacionStub();
		
		Infrastructure.configure(sistemaFacturacion, sistemaMensajeria, calendario);

		cadenaHotelera = crearCadenaHotelera();
		
		controllerFactory = new ControllerFactory(cadenaHotelera);
	}
	
	@After
	public void tearDown() throws Exception
	{
		Infrastructure.clear();
	}
	
	
	// Operations (protected) -------------------------------------------------

	protected void cambiarHoyEnCalendario(GregorianCalendar fechaHoy)
	{
		calendarioCambiarHoy.accept(fechaHoy);
	}

	protected CadenaHotelera crearCadenaHotelera()
	{
		String id = generarId();
		
		return new CadenaHotelera("Cadena-" + id);
	}
	
	protected int generarInt()
	{
		return random.nextInt();
	}
	
	protected int generarInt(int maximum)
	{
		return random.nextInt(maximum + 1);
	}
	
	protected int generarInt(int minimum, int maximum)
	{
		return random.nextInt(maximum - minimum + 1) + minimum; 
	}

	protected synchronized int generarIntId()
	{
		return lastId.addAndGet(generarInt(10) + 1);
	}
	
	protected String generarId()
	{
		return String.valueOf(generarIntId());
	}
	
	protected ClienteDTO generarCliente()
	{
		String id = generarId();
		
		return new ClienteDTO("rut" + id, "nombre" + id, "direccion" + id, "telefono" + id, "mail" + id);
	}
	
	protected ClienteDTO generarCliente(String rut)
	{
		String id = generarId();
		
		return new ClienteDTO(rut, "nombre" + id, "direccion" + id, "telefono" + id, "mail" + id);
	}
	
	protected ClienteDTO generarClienteConNombre(String nombre)
	{
		String id = generarId();
		
		return new ClienteDTO("rut" + id, nombre, "direccion" + id, "telefono" + id, "mail" + id);
	}
	
	protected HotelDTO generarHotel()
	{
		String id = generarId();
		
		return new HotelDTO("nombre" + id, "pais" + id);
	}
	
	protected HotelDTO generarHotel(String nombre)
	{
		String id = generarId();
		
		return new HotelDTO(nombre, "pais" + id);
	}
	
	protected HotelDTO generarHotelEnPais(String pais)
	{
		String id = generarId();
		
		return new HotelDTO("nombre" + id, pais);
	}
	
	protected HabitacionDTO generarHabitacion(HotelDTO hotel, TipoHabitacionDTO tipoHabitacion)
	{
		return generarHabitacion(hotel, tipoHabitacion, "nombre" + generarId());
	}
	
	protected HabitacionDTO generarHabitacion(HotelDTO hotel, TipoHabitacionDTO tipoHabitacion, String nombre)
	{
		return new HabitacionDTO(hotel.getNombre(), tipoHabitacion.getNombre(), nombre);
	}
	
	protected TipoHabitacionDTO generarTipoHabitacion()
	{
		String id = generarId();
		
		return new TipoHabitacionDTO("nombre" + id);
	}
	
	protected TipoHabitacionDTO generarTipoHabitacion(String nombre)
	{
		return new TipoHabitacionDTO(nombre);
	}
	
	protected HuespedDTO generarHuesped()
	{
		String id = generarId();
		
		return new HuespedDTO("nombre" + id, "documento" + id);
	}
	
	protected GregorianCalendar generarFecha()
	{
		return generarFecha(calendario.getHoy());
	}
	
	protected GregorianCalendar generarFecha(GregorianCalendar fechaDesde)
	{
		return generarFecha(fechaDesde, 1, generarInt(1, 33));
	}
	
	protected GregorianCalendar generarFecha(GregorianCalendar fechaDesde, int cantidadMaximaDeDias)
	{
		return generarFecha(fechaDesde, 1, cantidadMaximaDeDias);
	}
	
	protected GregorianCalendar generarFecha(GregorianCalendar fechaDesde, int cantidadMinimaDeDias, int cantidadMaximaDeDias)
	{
		GregorianCalendar fecha = (GregorianCalendar)fechaDesde.clone();
		
		fecha.add(Calendar.DAY_OF_YEAR, generarInt(cantidadMaximaDeDias - cantidadMinimaDeDias) + cantidadMinimaDeDias + 1);
		
		return fecha;
	}
	
	protected GregorianCalendar generarFechaEnElPasado()
	{
		GregorianCalendar fecha = new GregorianCalendar();
		
		fecha.add(Calendar.DAY_OF_YEAR, - generarInt(33) - 1);
		
		return fecha;
	}
}
