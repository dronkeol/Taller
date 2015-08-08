package org.tds.sgh.system;

import java.util.GregorianCalendar;
import java.util.List;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Cliente;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;

public class ModificarReservaController extends IdentificarReservasClienteController implements
    IModificarReservaController {

  private Cliente cliente;

  public ModificarReservaController(CadenaHotelera cadenaHotelera) {
    super(cadenaHotelera);
  }


  @Override
  public List<HotelDTO> sugerirAlternativas(String pais, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
    return DTO.getInstance().mapHoteles(
        super.getCadenaHotelera().sugerirAlternativas(pais, nombreTipoHabitacion, fechaInicio,
            fechaFin));
  }

  @Override
  public List<ClienteDTO> buscarCliente(String patronNombreCliente) {
    return DTO.getInstance().mapClientes(
        super.getCadenaHotelera().buscarClientes(patronNombreCliente));
  }

  public ClienteDTO seleccionarCliente(String rut) {
    Cliente cliente = null;
    try {
      cliente = super.getCadenaHotelera().buscarCliente(rut);
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.cliente = cliente;
    return DTO.getInstance().map(cliente);
  }

  public List<ReservaDTO> buscarReservasDelCliente() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ReservaDTO seleccionarReserva(long codigoReserva) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
    return super.getCadenaHotelera().confirmaDisponibilidad(nombreHotel, nombreTipoHabitacion,
        fechaInicio, fechaFin);
  }

  @Override
  public ReservaDTO modificarReserva(String nombreHotel, String nombreTipoHabitacion,
      GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
}
