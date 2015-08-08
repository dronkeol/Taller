package org.tds.sgh.business;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.tds.sgh.infrastructure.Infrastructure;

public class CadenaHotelera {
  // Attributes (private) -----------------------------------------------------------------------

  private String nombre;

  private Map<String, Cliente> clientes;

  private Map<String, Hotel> hoteles;

  private Map<String, TipoHabitacion> tiposHabitacion;


  // Constructors (public) ----------------------------------------------------------------------

  public CadenaHotelera(String nombre) {
    this.nombre = nombre;

    this.clientes = new HashMap<String, Cliente>();

    this.hoteles = new HashMap<String, Hotel>();

    this.tiposHabitacion = new HashMap<String, TipoHabitacion>();
  }


  // Properties (public) ------------------------------------------------------------------------

  public String getNombre() {
    return nombre;
  }


  // Operations (public) ------------------------------------------------------------------------

  public Cliente agregarCliente(String rut, String nombre, String direccion, String telefono,
      String mail) throws Exception {
    if (clientes.containsKey(rut))
      throw new Exception("Ya existe un cliente con el RUT indicado.");

    Cliente cliente = new Cliente(rut, nombre, direccion, telefono, mail);

    clientes.put(cliente.getRut(), cliente);

    return cliente;
  }

  public Cliente buscarCliente(String rut) throws Exception {
    Cliente cliente = clientes.get(rut);

    if (cliente == null)
      throw new Exception("No existe un cliente con el nombre indicado.");

    return cliente;
  }

  public Stream<Cliente> buscarClientes(String patronNombreCliente) {
    return clientes.values().stream()
        .filter(cliente -> cliente.getNombre().matches(patronNombreCliente));
  }

  public Stream<Cliente> listarClientes() {
    return clientes.values().stream();
  }

  public Hotel agregarHotel(String nombre, String pais) throws Exception {
    if (hoteles.containsKey(nombre))
      throw new Exception("Ya existe un hotel con el nombre indicado.");

    Hotel hotel = new Hotel(nombre, pais);

    hoteles.put(hotel.getNombre(), hotel);

    return hotel;
  }

  public Hotel buscarHotel(String nombre) throws Exception {
    Hotel hotel = hoteles.get(nombre);

    if (hotel == null)
      throw new Exception("No existe un hotel con el nombre indicado.");

    return hotel;
  }

  public Stream<Hotel> listarHoteles() {
    return hoteles.values().stream();
  }

  public TipoHabitacion agregarTipoHabitacion(String nombre) throws Exception {
    if (tiposHabitacion.containsKey(nombre))
      throw new Exception("Ya existe un tipo de habitaci�n con el nombre indicado.");

    TipoHabitacion tipoHabitacion = new TipoHabitacion(nombre);

    tiposHabitacion.put(tipoHabitacion.getNombre(), tipoHabitacion);

    return tipoHabitacion;
  }

  public TipoHabitacion buscarTipoHabitacion(String nombre) throws Exception {
    TipoHabitacion tipoHabitacion = tiposHabitacion.get(nombre);

    if (tipoHabitacion == null)
      throw new Exception("No existe un tipo de habitaci�n con el nombre indicado.");

    return tipoHabitacion;
  }

  public Stream<TipoHabitacion> listarTiposHabitacion() {
    return tiposHabitacion.values().stream();
  }

  public void lala() {

  }

  public boolean confirmaDisponibilidad(String nombreHotel, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {


    Hotel oHotel = null;
    try {
      oHotel = this.buscarHotel(nombreHotel);
    } catch (Exception e) {
      return false;
    }

    Stream<Habitacion> lstHabitaciones =
        oHotel.listarHabitaciones().filter(
            h -> h.getTipoHabitacion().getNombre().equals(nombreTipoHabitacion));
    long countHabitaciones = lstHabitaciones.count();

    Stream<Reserva> lstReservas =
        oHotel.listarReservas().filter(p ->
          {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd"); // lowercase "dd"

            boolean equalsTipoHabitacion =
                p.getTipoHabitacion().getNombre().equals(nombreTipoHabitacion);

            boolean isPendiente = EstadoReserva.Pendiente.equals(p.getEstado());

            System.out.println(formatter.format(fechaFin.getTime()));
            System.out.println(formatter.format(p.getFechaInicio().getTime()));
            boolean fechaFinAnterior =
                Infrastructure.getInstance().getCalendario()
                    .esAnterior(fechaFin, p.getFechaInicio());

            System.out.println("**");
            System.out.println(formatter.format(fechaInicio.getTime()));
            System.out.println(formatter.format(p.getFechaFin().getTime()));
            boolean fechaInicioPosterior =
                Infrastructure.getInstance().getCalendario()
                    .esPosterior(fechaInicio, p.getFechaFin());

            return equalsTipoHabitacion && isPendiente
                && !(fechaFinAnterior && fechaInicioPosterior);

          }



        );
    long countReservas = lstReservas.count();
    return countHabitaciones > countReservas;
  }

  public Stream<Hotel> sugerirAlternativas(String pais, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
    return this.hoteles
        .values()
        .stream()
        .filter(h -> h.getPais().equals(pais))
        .filter(
            h -> this.confirmaDisponibilidad(h.getNombre(), nombreTipoHabitacion, fechaInicio,
                fechaFin));
  }

  public Stream<Reserva> buscarReservasDelCliente(Hotel hotel, Cliente cliente) {
    return hotel.listarReservas().filter(r -> r.getCliente().equals(cliente));

  }

  public Reserva seleccionarReserva(long codigo) {
    for (Hotel hotel : hoteles.values()) {
      Optional<Reserva> opt =
          hotel.listarReservas().filter(r -> r.getCodigo() == codigo).findFirst();
      if (opt.isPresent()) {
        return opt.get();
      }
    }
    return null;
  }

}
