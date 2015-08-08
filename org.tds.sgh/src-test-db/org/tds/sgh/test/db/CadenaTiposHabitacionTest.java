package org.tds.sgh.test.db;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.dtos.TipoHabitacionDTO;
import org.tds.sgh.system.ICadenaController;

@RunWith(JUnit4.class)
public class CadenaTiposHabitacionTest extends DbTestBase
{
	@Test
	public void TiposHabitacionNoEsNull()
	{
		ICadenaController cadenaController = controllerFactory.createCadenaController();
	
		
		assertTrue("El controlador devolvió null. Debe devolver una lista de tipos de habitación.",
				   cadenaController.getTiposHabitacion() != null);
	}
	
	@Test
	public void TiposHabitacionEsVacio()
	{
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		
		assertTrue("El controlador no devolvió una lista de tipos de habitación vacía al inicio.",
				   cadenaController.getTiposHabitacion().isEmpty());
	}

	@Test
	public void CadenaRegistraTipoHabitacion() throws Exception
	{
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		
		List<TipoHabitacionDTO> tiposHabitacion = cadenaController.getTiposHabitacion();
		
		assertTrue("El sistema no registró al tipo de habitación",
				   !tiposHabitacion.isEmpty());
		
		assertTrue("El sistema registró más de un tipo de habitación.",
				   tiposHabitacion.size() == 1);
		
		TipoHabitacionDTO tipoHabitacionX = tiposHabitacion.get(0);
		
		assertTrue("El sistema devolvió null, debe devolver los datos del tipo de habitación registrado.",
				   tipoHabitacionX != null);
		
		assertTrue("El sistema reutilizó instancias de test; el sistema debe preservar su propia información.",
				   tipoHabitacionX != tipoHabitacion1);
		
		assertTrue("El sistema registró datos del tipo de habitación que no coinciden con los datos provistos al momento de registrar al tipo de habitación.",
				   tipoHabitacionX.equals(tipoHabitacion1));
	}
	
	@Test
	public void CadenaRegistraDosTiposHabitacion() throws Exception
	{
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		TipoHabitacionDTO tipoHabitacion2 = generarTipoHabitacion();

		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);
		
		
		List<TipoHabitacionDTO> tiposHabitacion = cadenaController.getTiposHabitacion();
		
		assertTrue("El sistema no devolvió los dos tipos de habitación registrados.",
				   tiposHabitacion.size() == 2);
		
		TipoHabitacionDTO tipoHabitacionX = tiposHabitacion.get(0);
		
		TipoHabitacionDTO tipoHabitacionY = tiposHabitacion.get(1);
		
		assertTrue("El sistema devolvió datos de tipos de habitación que no coinciden con los registrados",
				   (tipoHabitacionX.equals(tipoHabitacion1) || tipoHabitacionX.equals(tipoHabitacion2)) &&
				   (tipoHabitacionY.equals(tipoHabitacion1) || tipoHabitacionY.equals(tipoHabitacion2)));
	}

	@Test(expected=Exception.class)
	public void CadenaNoRegistraDosTiposHabitacionConElMismoNombre() throws Exception
	{
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		TipoHabitacionDTO tipoHabitacion2 = generarTipoHabitacion(tipoHabitacion1.getNombre());
		
		
		ICadenaController cadenaController = controllerFactory.createCadenaController();
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);
	}
	
	@Test
	public void CadenaNoPierdeInformacionDeTiposHabitacion() throws Exception
	{
		TipoHabitacionDTO tipoHabitacion1 = generarTipoHabitacion();
		
		
		ICadenaController cadenaController1 = controllerFactory.createCadenaController();
		
		assertTrue("El controlador no devolvió una lista de tipos de habitación vacía al inicio.",
				   cadenaController1.getTiposHabitacion().isEmpty());
		
		cadenaController1.agregarTipoHabitacion(tipoHabitacion1.getNombre());
		
		assertTrue("El sistema no registró al tipo de habitación.",
				   !cadenaController1.getTiposHabitacion().isEmpty());
		
		assertTrue("El sistema registró más de un tipo de habitación.",
				   cadenaController1.getTiposHabitacion().size() == 1);
		
		assertTrue("El sistema registró datos del tipo de habitación que no coinciden con los datos provistos al momento de registrar al tipo de habitación.",
				   cadenaController1.getTiposHabitacion().get(0).equals(tipoHabitacion1));
				

		ICadenaController cadenaController2 = controllerFactory.createCadenaController();

		assertTrue("El sistema no registró al tipo de habitación. Usando un segundo controlador, éste debe devolver el tipo de habitación registrado mediante el primer controlador.",
				   !cadenaController2.getTiposHabitacion().isEmpty());
		
		assertTrue("El sistema registró más de un tipo de habitación. Usando un segundo controlador, éste debe devolver el único tipo de habitación registrado mediante el primer controlador.",
				   cadenaController1.getTiposHabitacion().size() == 1);

		assertTrue("El sistema registró datos del tipo de habitación que no coinciden con los datos provistos. Los datos del tipo de habitación que devuelve el segundo controlador deben ser los mismos que los registrados mediante el primer controlador.",
				   cadenaController2.getTiposHabitacion().get(0).equals(tipoHabitacion1));
	}
}
