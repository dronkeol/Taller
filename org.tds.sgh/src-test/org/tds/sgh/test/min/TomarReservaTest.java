package org.tds.sgh.test.min;

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
import org.tds.sgh.system.ITomarReservaController;
import org.tds.sgh.test.TestBase;
import org.tds.sgh.test.stubs.SistemaMensajeriaStub.Mail;

@RunWith(JUnit4.class)
public class TomarReservaTest extends TestBase
{
	// curso t�pico ...........................................................

	@Test
	public void TomarReservaCursoTipico() throws Exception
	{
		ClienteDTO cliente1 = generarClienteConNombre("Fulanito");
		
		HotelDTO hotel1 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		HuespedDTO huesped1 = generarHuesped();
		HuespedDTO huesped2 = generarHuesped();
				
		GregorianCalendar fecha1 = calendario.getHoy();
		GregorianCalendar fecha2 = generarFecha(fecha1);

	
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);

		
		IHacerReservaController hacerReservaController = controllerFactory.createHacerReservaController();
		
		
		List<ClienteDTO> clientes = hacerReservaController.buscarCliente(".*lani.*");
		
		assertTrue("El cliente devuelto debe ser el cliente1.",
				   clientes != null && clientes.size() == 1 && cliente1.equals(clientes.get(0)));
		
		ClienteDTO clienteX = hacerReservaController.seleccionarCliente(clientes.get(0));
		
		assertTrue("El cliente seleccionado debe ser el cliente1.",
				   cliente1.equals(clienteX));
		
		
		boolean disponibilidad = hacerReservaController.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		assertTrue("El hotel debe tener disponibilidad ya que no tiene ninguna reserva que interfiera.",
				   disponibilidad);
		
		
		ReservaDTO reservaX = hacerReservaController.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		ReservaDTO reserva = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Pendiente",
			null
		);

		assertTrue("Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
				   reserva.equals(reservaX));

		
		
		ITomarReservaController tomarReservaController = controllerFactory.createTomarReservaController();
		
		List<ReservaDTO> reservas = tomarReservaController.buscarReservasPendientes(hotel1);
		
		assertTrue("La �nica reserva pendiente del hotel debe ser la reserva registrada.",
				   reservas != null && reservas.size() == 1 && reserva.equals(reservas.get(0)));
		
		
		ReservaDTO reservaY1 = tomarReservaController.seleccionarReserva(reservas.get(0));
		
		assertTrue("La reserva seleccionada debe ser la reserva registrada.",
				   reserva.equals(reservaY1));
		
		
		ReservaDTO reservaY2 = tomarReservaController.registrarHuesped(huesped1);

		ReservaDTO reserva2 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Pendiente",
			null,
			huesped1
		);
		
		assertTrue("La reserva debe registrar el hu�sped.",
				   reserva2.equals(reservaY2));
		
		
		ReservaDTO reservaY3 = tomarReservaController.registrarHuesped(huesped2);
		
		ReservaDTO reserva3 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Pendiente",
			null,
			huesped1,
			huesped2
		);
		
		assertTrue("La reserva debe registrar el hu�sped.",
				   reserva3.equals(reservaY3));
		
		
		ReservaDTO reservaZ = tomarReservaController.tomarReserva();
		
		ReservaDTO reserva4 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Tomada",
			habitacion1.getNombre(),
			huesped1,
			huesped2
		);
		
		assertTrue("Los datos de la reserva tomada no coinciden con los esperados.",
				   reserva4.equals(reservaZ));
		
		
		List<Mail> mails = sistemaMensajeria.getMailEnviados();
		
		assertTrue("El sistema debe mandar mails al cliente confirmando la reserva (al registrar y al tomar la reserva).",
				   mails.size() == 2);
		
		assertTrue("El destinatario de los mails debe ser el cliente de la reserva.",
				   mails.stream().allMatch(mail -> mail.getDestinatario().equals(cliente1.getMail())));

		
		assertTrue("El sistema de facturaci�n no recibi� la notificaci�n de que se tom� una reserva.",
				   !sistemaFacturacion.getReservas().isEmpty());
		
		assertTrue("El sistema de facturaci�n recibi� m�s de una notificaci�n de que se tom� una reserva.",
				   sistemaFacturacion.getReservas().size() == 1);
		
		assertTrue("La reserva que recibi� el sistema de facturaci�n no coincide con la reserva tomada.",
				   reserva4.equals(sistemaFacturacion.getReservas().get(0)));
	}
	
	
	// curso alternativo con identificar reserva cliente ......................
	
	@Test
	public void TomarReservaCursoAlternativoConIdentificarReservaCliente() throws Exception
	{
		ClienteDTO cliente1 = generarClienteConNombre("Fulanito");
		
		HotelDTO hotel1 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		HuespedDTO huesped1 = generarHuesped();
		HuespedDTO huesped2 = generarHuesped();
				
		GregorianCalendar fecha1 = calendario.getHoy();
		GregorianCalendar fecha2 = generarFecha(fecha1);

	
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);

		
		IHacerReservaController hacerReservaController = controllerFactory.createHacerReservaController();
		
		
		List<ClienteDTO> clientes = hacerReservaController.buscarCliente(".*lani.*");
		
		assertTrue("El cliente devuelto debe ser el cliente1.",
				   clientes != null && clientes.size() == 1 && cliente1.equals(clientes.get(0)));
		
		ClienteDTO clienteX = hacerReservaController.seleccionarCliente(clientes.get(0));
		
		assertTrue("El cliente seleccionado debe ser el cliente1.",
				   cliente1.equals(clienteX));
		
		
		boolean disponibilidad = hacerReservaController.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		assertTrue("El hotel debe tener disponibilidad ya que no tiene ninguna reserva que interfiera.",
				   disponibilidad);
		
		
		ReservaDTO reservaX = hacerReservaController.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		ReservaDTO reserva = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Pendiente",
			null
		);

		assertTrue("Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
				   reserva.equals(reservaX));

		
		
		ITomarReservaController tomarReservaController = controllerFactory.createTomarReservaController();
		
		List<ClienteDTO> clientes2 = tomarReservaController.buscarCliente(".*lani.*");
		
		assertTrue("El cliente devuelto debe ser el cliente1.",
				   clientes2 != null && clientes2.size() == 1 && cliente1.equals(clientes2.get(0)));
		
		ClienteDTO clienteY = tomarReservaController.seleccionarCliente(clientes2.get(0));
		
		assertTrue("El cliente seleccionado debe ser el cliente1.",
				   cliente1.equals(clienteY));
		
		List<ReservaDTO> reservas = tomarReservaController.buscarReservasDelCliente();
		
		assertTrue("La �nica reserva pendiente del hotel debe ser la reserva registrada.",
				   reservas != null && reservas.size() == 1 && reserva.equals(reservas.get(0)));
		
		ReservaDTO reservaY1 = tomarReservaController.seleccionarReserva(reservas.get(0));
		
		assertTrue("La reserva seleccionada debe ser la reserva registrada.",
				   reserva.equals(reservaY1));
		
		
		ReservaDTO reservaY2 = tomarReservaController.registrarHuesped(huesped1);

		ReservaDTO reserva2 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Pendiente",
			null,
			huesped1
		);
		
		assertTrue("La reserva debe registrar el hu�sped.",
				   reserva2.equals(reservaY2));
		
		
		ReservaDTO reservaY3 = tomarReservaController.registrarHuesped(huesped2);
		
		ReservaDTO reserva3 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Pendiente",
			null,
			huesped1,
			huesped2
		);
		
		assertTrue("La reserva debe registrar el hu�sped.",
				   reserva3.equals(reservaY3));
		
		
		ReservaDTO reservaZ = tomarReservaController.tomarReserva();
		
		ReservaDTO reserva4 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Tomada",
			habitacion1.getNombre(),
			huesped1,
			huesped2
		);
		
		assertTrue("Los datos de la reserva tomada no coinciden con los esperados.",
				   reserva4.equals(reservaZ));
		
		
		List<Mail> mails = sistemaMensajeria.getMailEnviados();
		
		assertTrue("El sistema debe mandar mails al cliente confirmando la reserva (al registrar y al tomar la reserva).",
				   mails.size() == 2);
		
		assertTrue("El destinatario de los mails debe ser el cliente de la reserva.",
				   mails.stream().allMatch(mail -> mail.getDestinatario().equals(cliente1.getMail())));

		
		
		assertTrue("El sistema de facturaci�n no recibi� la notificaci�n de que se tom� una reserva.",
				   !sistemaFacturacion.getReservas().isEmpty());
		
		assertTrue("El sistema de facturaci�n recibi� m�s de una notificaci�n de que se tom� una reserva.",
				   sistemaFacturacion.getReservas().size() == 1);
		
		assertTrue("La reserva que recibi� el sistema de facturaci�n no coincide con la reserva tomada.",
				   reserva4.equals(sistemaFacturacion.getReservas().get(0)));
	}
	
	
	// curso alternativo con hacer reserva ....................................
	
	@Test
	public void TomarReservaCursoAlternativoConHacerReserva() throws Exception
	{
		ClienteDTO cliente1 = generarClienteConNombre("Fulanito");
		
		HotelDTO hotel1 = generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = generarHabitacion(hotel1, tipoHabitacion1);
		
		HuespedDTO huesped1 = generarHuesped();
		HuespedDTO huesped2 = generarHuesped();
				
		GregorianCalendar fecha1 = calendario.getHoy();
		GregorianCalendar fecha2 = generarFecha(fecha1);

	
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);

		
		ITomarReservaController tomarReservaController = controllerFactory.createTomarReservaController();

		List<ClienteDTO> clientes = tomarReservaController.buscarCliente(".*lani.*");
		
		assertTrue("El cliente devuelto debe ser el cliente1.",
				   clientes != null && clientes.size() == 1 && cliente1.equals(clientes.get(0)));
		
		ClienteDTO clienteX = tomarReservaController.seleccionarCliente(clientes.get(0));
		
		assertTrue("El cliente seleccionado debe ser el cliente1.",
				   cliente1.equals(clienteX));
		
		
		boolean disponibilidad = tomarReservaController.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		assertTrue("El hotel debe tener disponibilidad ya que no tiene ninguna reserva que interfiera.",
				   disponibilidad);
		
		
		ReservaDTO reservaX = tomarReservaController.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		ReservaDTO reserva = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Pendiente",
			null
		);

		assertTrue("Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
				   reserva.equals(reservaX));

		
		ReservaDTO reservaY2 = tomarReservaController.registrarHuesped(huesped1);

		ReservaDTO reserva2 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Pendiente",
			null,
			huesped1
		);
		
		assertTrue("La reserva debe registrar el hu�sped.",
				   reserva2.equals(reservaY2));
		
		
		ReservaDTO reservaY3 = tomarReservaController.registrarHuesped(huesped2);
		
		ReservaDTO reserva3 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Pendiente",
			null,
			huesped1,
			huesped2
		);
		
		assertTrue("La reserva debe registrar el hu�sped.",
				   reserva3.equals(reservaY3));
		
		
		ReservaDTO reservaZ = tomarReservaController.tomarReserva();
		
		ReservaDTO reserva4 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Tomada",
			habitacion1.getNombre(),
			huesped1,
			huesped2
		);
		
		assertTrue("Los datos de la reserva tomada no coinciden con los esperados.",
				   reserva4.equals(reservaZ));
		
		
		List<Mail> mails = sistemaMensajeria.getMailEnviados();
		
		assertTrue("El sistema debe mandar mails al cliente confirmando la reserva (al registrar y al tomar la reserva).",
				   mails.size() == 2);
		
		assertTrue("El destinatario de los mails debe ser el cliente de la reserva.",
				   mails.stream().allMatch(mail -> mail.getDestinatario().equals(cliente1.getMail())));

		
		assertTrue("El sistema de facturaci�n no recibi� la notificaci�n de que se tom� una reserva.",
				   !sistemaFacturacion.getReservas().isEmpty());
		
		assertTrue("El sistema de facturaci�n recibi� m�s de una notificaci�n de que se tom� una reserva.",
				   sistemaFacturacion.getReservas().size() == 1);
		
		assertTrue("La reserva que recibi� el sistema de facturaci�n no coincide con la reserva tomada.",
				   reserva4.equals(sistemaFacturacion.getReservas().get(0)));
	}
}
