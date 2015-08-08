package org.tds.sgh.dtos;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.tds.sgh.business.Cliente;
import org.tds.sgh.business.Habitacion;
import org.tds.sgh.business.Hotel;
import org.tds.sgh.business.Huesped;
import org.tds.sgh.business.Reserva;
import org.tds.sgh.business.TipoHabitacion;

public class DTO {
  // Attributes (static private)
  // ----------------------------------------------------------------

  private static final DTO Instance = new DTO();

  // Operations (static public)
  // -----------------------------------------------------------------

  public static DTO getInstance() {
    return Instance;
  }

  // Constructors (private)
  // ---------------------------------------------------------------------

  private DTO() {}

  // Operations (public)
  // ------------------------------------------------------------------------

  public ClienteDTO map(Cliente cliente) {
    return new ClienteDTO(cliente.getRut(), cliente.getNombre(), cliente.getDireccion(),
        cliente.getTelefono(), cliente.getMail());
  }

  public List<ClienteDTO> mapClientes(Stream<Cliente> clientes) {
    return clientes.map(cliente -> map(cliente)).collect(Collectors.toList());
  }

  public HotelDTO map(Hotel hotel) {
    return new HotelDTO(hotel.getNombre(), hotel.getPais());
  }

  public List<HotelDTO> mapHoteles(Stream<Hotel> hoteles) {
    return hoteles.map(hotel -> map(hotel)).collect(Collectors.toList());
  }

  public HabitacionDTO map(Hotel hotel, Habitacion habitacion) {
    return new HabitacionDTO(hotel.getNombre(), habitacion.getTipoHabitacion().getNombre(),
        habitacion.getNombre());
  }

  public List<HabitacionDTO> mapHabitaciones(Hotel hotel, Stream<Habitacion> habitaciones) {
    return habitaciones.map(habitacion -> map(hotel, habitacion)).collect(Collectors.toList());
  }

  public TipoHabitacionDTO map(TipoHabitacion tipoHabitation) {
    return new TipoHabitacionDTO(tipoHabitation.getNombre());
  }

  public List<TipoHabitacionDTO> mapTiposHabitacion(Stream<TipoHabitacion> tiposHabitacion) {
    return tiposHabitacion.map(tipoHabitacion -> map(tipoHabitacion)).collect(Collectors.toList());
  }

  public HuespedDTO map(Huesped huesped) {
    return new HuespedDTO(huesped.getNombre(), huesped.getDocumento());
  }

  public List<HuespedDTO> mapHuespedes(Stream<Huesped> huespedes) {
    return huespedes.map(huesped -> map(huesped)).collect(Collectors.toList());
  }

  public ReservaDTO map(Reserva reserva) {
    return new ReservaDTO(reserva.getCodigo(), reserva.getCliente().getRut(), reserva.getHotel()
        .getNombre(), reserva.getTipoHabitacion().getNombre(), reserva.getFechaInicio(),
        reserva.getFechaFin(), reserva.isModificablePorHuesped(), reserva.getEstado().toString(),
        reserva.getHabitacion() != null ? reserva.getHabitacion().getNombre() : null, reserva
            .getHuespedes().toArray(new HuespedDTO[reserva.getHuespedes().size()]));
  }

  public List<ReservaDTO> mapReservas(Stream<Reserva> reservas) {
    return reservas.map(reserva -> map(reserva)).collect(Collectors.toList());
  }
}
