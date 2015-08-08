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

  public TomarReservaController(CadenaHotelera cadenaHotelera) {
    super(cadenaHotelera);
  }

  public List<ReservaDTO> buscarReservasPendientes(String nombreHotel) {
    Hotel hotel;

    try {
      hotel = super.getCadenaHotelera().buscarHotel(nombreHotel);
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
      cliente = super.getCadenaHotelera().agregarCliente(rut, nombre, direccion, telefono, mail);
    } catch (Exception e) {
      e.printStackTrace();
    }
    super.setCliente(cliente);
    return DTO.getInstance().map(cliente);
  }

  public Reserva getReserva() {
    return reserva;
  }

  public void setReserva(Reserva reserva) {
    this.reserva = reserva;
  }

  @Override
  public ReservaDTO registrarReserva(String nombreHotel, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped)
      throws Exception {
    Hotel h = null;
    TipoHabitacion th = null;
    try {
      h = super.getCadenaHotelera().buscarHotel(nombreHotel);
      th = super.getCadenaHotelera().buscarTipoHabitacion(nombreTipoHabitacion);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Reserva r =
        h.registrarReserva(super.getCliente(), th, fechaInicio, fechaFin, modificablePorHuesped);

    Infrastructure.getInstance().getSistemaMensajeria()
        .enviarMail(super.getCliente().getMail(), "Reserva agendada.", "Reserva agendada OK.");
    return DTO.getInstance().map(r);
  }

}
