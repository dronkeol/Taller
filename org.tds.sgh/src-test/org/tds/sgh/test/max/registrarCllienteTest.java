package org.tds.sgh.test.max;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.system.IAltaClienteController;
import org.tds.sgh.system.ICadenaController;
import org.tds.sgh.test.TestBase;

@RunWith(JUnit4.class)
public class registrarCllienteTest extends TestBase
{
	@Test
	public void RegistraCliente() throws Exception
	{
		ClienteDTO cliente1 = generarCliente();
		
		
		IAltaClienteController hacerReservaController = controllerFactory.createHacerReservaController();
		
		
		ClienteDTO clienteX = hacerReservaController.registrarCliente(cliente1);
		
		assertTrue("Los datos del cliente registrado no coinciden con los datos provistos al momento de registrar al cliente.",
				   cliente1.equals(clienteX));
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		List<ClienteDTO> clientes = cadenaController.getClientes();
		
		assertTrue("El cliente no qued� registrado.",
				   clientes.size() == 1);
		
		assertTrue("Los datos del cliente registrado no coincide con los datos provistos al momento de registrar al cliente.",
				   cliente1.equals(clientes.get(0)));
	}
	
	@Test(expected=Exception.class)
	public void SistemaNoRegistraClienteConRutDeClienteExistente() throws Exception
	{
		ClienteDTO cliente1 = generarCliente();
		
		ClienteDTO cliente2 = generarCliente(cliente1.getRut());
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		
		IAltaClienteController hacerReservaController = controllerFactory.createHacerReservaController();
		
		hacerReservaController.registrarCliente(cliente2);
	}
}
