package org.tds.sgh.system;

import java.util.List;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.ReservaDTO;

public class IdentificarReservasClienteController extends IdentificarClenteEnRecepcionController
    implements IIdentificarReservaClienteController {

  public IdentificarReservasClienteController(CadenaHotelera cadenaHotelera) {
    super(cadenaHotelera);
  }


  @Override
  public List<ClienteDTO> buscarCliente(String patronNombreCliente) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ClienteDTO seleccionarCliente(String rut) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ReservaDTO> buscarReservasDelCliente() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ReservaDTO seleccionarReserva(long codigoReserva) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

}
