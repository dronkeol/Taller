package org.tds.sgh.test.db;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.system.ICadenaController;

@RunWith(JUnit4.class)
public class CadenaClientesTest extends DbTestBase
{
	@Test
	public void ClientesNoEsNull()
	{
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		
		assertTrue("El controlador devolvió null. Debe devolver una lista de clientes.",
				   cadenaController.getClientes() != null);
	}
	
	@Test
	public void ClientesEsVacio()
	{
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		
		assertTrue("El controlador no devolvió una lista de clientes vacía al inicio.",
				   cadenaController.getClientes().isEmpty());
	}

	@Test
	public void CadenaRegistraCliente() throws Exception
	{
		ClienteDTO cliente1 = generarCliente();
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);

		
		List<ClienteDTO> clientes = cadenaController.getClientes();
		
		assertTrue("El sistema no registró al cliente",
				   !clientes.isEmpty());
		
		assertTrue("El sistema registró más de un cliente.",
				   clientes.size() == 1);
		
		ClienteDTO cliente = clientes.get(0);
		
		assertTrue("El sistema devolvió null, debe devolver los datos del cliente registrado.",
				   cliente != null);
		
		assertTrue("El sistema reutilizó instancias de test; el sistema debe preservar su propia información.",
				   cliente != cliente1);
		
		assertTrue("El sistema registró datos del cliente que no coinciden con los datos provistos al momento de registrar al cliente.",
				   cliente.equals(cliente1));
	}
	
	@Test
	public void CadenaRegistraDosClientes() throws Exception
	{
		ClienteDTO cliente1 = generarCliente();
		
		ClienteDTO cliente2 = generarCliente();
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarCliente(cliente2);
		
		
		List<ClienteDTO> clientes = cadenaController.getClientes();
		
		assertTrue("El sistema no devolvió los dos clientes registrados.",
				   clientes.size() == 2);
		
		ClienteDTO clienteX = clientes.get(0);
		
		ClienteDTO clienteY = clientes.get(1);
		
		assertTrue("El sistema devolvió datos de clientes que no coinciden con los registrados",
				   (clienteX.equals(cliente1) || clienteX.equals(cliente2)) &&
				   (clienteY.equals(cliente1) || clienteY.equals(cliente2)));
	}

	@Test(expected=Exception.class)
	public void CadenaNoRegistraDosClientesConElMismoRut() throws Exception
	{
		ClienteDTO cliente1 = generarCliente();

		ClienteDTO cliente2 = generarCliente(cliente1.getRut());
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarCliente(cliente2);
	}
	
	@Test
	public void CadenaNoPierdeInformacionDeClientes() throws Exception
	{
		ClienteDTO cliente1 = generarCliente();

		
		ICadenaController cadenaController1 = controllerFactory.createCadenaController();
		
		assertTrue("El controlador no devolvió una lista de clientes vacía al inicio.",
				   cadenaController1.getClientes().isEmpty());
		
		cadenaController1.agregarCliente(cliente1.getRut(), cliente1.getNombre(), cliente1.getDireccion(), cliente1.getTelefono(), cliente1.getMail());
		
		assertTrue("El sistema no registró al cliente.",
				   !cadenaController1.getClientes().isEmpty());
		
		assertTrue("El sistema registró más de un cliente.",
				   cadenaController1.getClientes().size() == 1);
		
		assertTrue("El sistema registró datos del cliente que no coinciden con los datos provistos al momento de registrar al cliente.",
				   cadenaController1.getClientes().get(0).equals(cliente1));


		ICadenaController cadenaController2 = controllerFactory.createCadenaController();

		assertTrue("El sistema no registró al cliente. Usando un segundo controlador, éste debe devolver el cliente registrado mediante el primer controlador.",
				   !cadenaController2.getClientes().isEmpty());
		
		assertTrue("El sistema registró más de un cliente. Usando un segundo controlador, éste debe devolver el único cliente registrado mediante el primer controlador.",
				   cadenaController1.getClientes().size() == 1);

		assertTrue("El sistema registró datos del cliente que no coinciden con los datos provistos. Los datos del cliente que devuelve el segundo controlador deben ser los mismos que los registrados mediante el primer controlador.",
				   cadenaController2.getClientes().get(0).equals(cliente1));
	}
}
