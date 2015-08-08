package org.tds.sgh.system;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Cliente;
import org.tds.sgh.business.Hotel;
import org.tds.sgh.business.Reserva;
import org.tds.sgh.business.TipoHabitacion;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.infrastructure.Infrastructure;

public class TomarReservaController extends ModificarReservaController implements
    ITomarReservaController {

  private Reserva reserva;

  private Cliente cliente;

  private CadenaHotelera cadenaHotelera;

  public TomarReservaController(CadenaHotelera cadenaHotelera) {
    this.cadenaHotelera = cadenaHotelera;
  }

  public List<ReservaDTO> buscarReservasPendientes(String nombreHotel) {
    Hotel hotel;

    try {
      hotel = this.cadenaHotelera.buscarHotel(nombreHotel);
    } catch (Exception e) {
      return new ArrayList<ReservaDTO>();
    }

    return DTO.getInstance().mapReservas(hotel.listarReservas().filter(p -> p.isPendiente()));
  }

  public ReservaDTO registrarHuesped(String ombre, String documento) {
    return DTO.getInstance().map(reserva.registrarHuesped(ombre, documento));
  }

  public ReservaDTO tomarReserva() {
    throw new UnsupportedOperationException();
  }

  public ClienteDTO buscarClientePorPatron(String rut) {
    throw new UnsupportedOperationException();
  }

  public ClienteDTO registrarCliente(String rut, String nombre, String direccion, String telefono,
      String mail) {

    Cliente cliente = null;
    try {
      cliente = cadenaHotelera.agregarCliente(rut, nombre, direccion, telefono, mail);
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.cliente = cliente;
    return DTO.getInstance().map(cliente);
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
  public ReservaDTO registrarReserva(String nombreHotel, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped)
      throws Exception {
    Hotel h = null;
    TipoHabitacion th = null;
    try {
      h = this.cadenaHotelera.buscarHotel(nombreHotel);
      th = this.cadenaHotelera.buscarTipoHabitacion(nombreTipoHabitacion);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Reserva r = h.registrarReserva(cliente, th, fechaInicio, fechaFin, modificablePorHuesped);

    Infrastructure.getInstance().getSistemaMensajeria()
        .enviarMail(cliente.getMail(), "Reserva agendada.", "Reserva agendada OK.");
    return DTO.getInstance().map(r);
  }
}
