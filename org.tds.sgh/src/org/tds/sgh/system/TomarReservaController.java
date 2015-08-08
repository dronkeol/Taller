package org.tds.sgh.system;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Cliente;
import org.tds.sgh.business.Hotel;
import org.tds.sgh.business.Reserva;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;

public class TomarReservaController implements ITomarReservaController {

  private Reserva reserva;

  private Cliente cliente;

  private CadenaHotelera cadenaHotelera;


  public List<ReservaDTO> buscarReservasPendientes(String nombreHotel) {
    throw new UnsupportedOperationException();
  }

  public Reserva seleccionarReserva() {
    throw new UnsupportedOperationException();
  }


  public ReservaDTO registrarHuesped(String ombre, String documento) {
    throw new UnsupportedOperationException();
  }

  public ReservaDTO tomarReserva() {
    throw new UnsupportedOperationException();
  }

  public void modificarReserva(String nombreHotel, String nombreTipoHabitacion, Date fechaInicio,
      Date fecha) {
    throw new UnsupportedOperationException();
  }

  public List<ReservaDTO> buscarReservasDelCliente() {
    throw new UnsupportedOperationException();
  }

  public ClienteDTO seleccionarCliente(String rut) {
    throw new UnsupportedOperationException();
  }

  public ClienteDTO buscarClientePorPatron(String rut) {
    throw new UnsupportedOperationException();
  }

  public ClienteDTO registrarCliente(String rut, String nombre, String direccion, String telefono,
      String mail) {
    throw new UnsupportedOperationException();
  }

  public boolean confirmaDisponibilidad(String nombreHotel, String nombreTipoHabitacion,
      java.util.Date fechaInicio, java.util.Date fechaFin) {
    // TODO - implement HacerReservaController.confirmaDisponibilidad
    throw new UnsupportedOperationException();
  }

  public Reserva registrarReserva(String nombreHotel, String nombreTipoHabitacio, Date fechaInicio,
      Date fechaFin, boolean modificablePorHuesped) {
    throw new UnsupportedOperationException();
  }

  public Collection<Hotel> sugerirAlternativa(String pais, String nombreTipoHabitacion,
      Date fechaInicio, Date fechaFin) {
    throw new UnsupportedOperationException();
  }


  public CadenaHotelera getCadenaHotelera() {
    return cadenaHotelera;
  }


  public void setCadenaHotelera(CadenaHotelera cadenaHotelera) {
    this.cadenaHotelera = cadenaHotelera;
  }


  public Reserva getReserva() {
    return reserva;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setReserva(Reserva reserva) {
    this.reserva = reserva;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  @Override
  public List<ClienteDTO> buscarCliente(String patronNombreCliente) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<HotelDTO> sugerirAlternativas(String pais, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ReservaDTO registrarReserva(String nombreHotel, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ReservaDTO modificarReserva(String nombreHotel, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ReservaDTO seleccionarReserva(long codigoReserva) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }



}