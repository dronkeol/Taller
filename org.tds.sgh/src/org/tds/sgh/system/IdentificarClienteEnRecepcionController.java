package org.tds.sgh.system;

import java.util.List;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.dtos.ClienteDTO;

public class IdentificarClienteEnRecepcionController implements
    IIdentificarClienteEnRecepcionController {

  private CadenaHotelera cadenaHotelera;

  public IdentificarClienteEnRecepcionController(CadenaHotelera cadenaHotelera) {
    super();
    this.cadenaHotelera = cadenaHotelera;
  }

  @Override
  public List<ClienteDTO> buscarCliente(String patronNombreCliente) {
    return null;
  }

  @Override
  public ClienteDTO seleccionarCliente(String rut) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

}