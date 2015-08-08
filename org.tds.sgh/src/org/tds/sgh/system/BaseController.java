package org.tds.sgh.system;

import java.util.List;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Cliente;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.DTO;

public class BaseController {

  private CadenaHotelera cadenaHotelera;
  private Cliente cliente;

  public BaseController(CadenaHotelera cadenaHotelera) {
    this.cadenaHotelera = cadenaHotelera;
  }

  public CadenaHotelera getCadenaHotelera() {
    return cadenaHotelera;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCadenaHotelera(CadenaHotelera cadenaHotelera) {
    this.cadenaHotelera = cadenaHotelera;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public List<ClienteDTO> buscarCliente(String patronNombreCliente) {
    return DTO.getInstance().mapClientes(
        this.getCadenaHotelera().buscarClientes(patronNombreCliente));
  }

  public ClienteDTO seleccionarCliente(String rut) {
    Cliente cliente = null;
    try {
      cliente = this.getCadenaHotelera().buscarCliente(rut);
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.cliente = cliente;
    return DTO.getInstance().map(cliente);
  }


}
