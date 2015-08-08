package org.tds.sgh.test.db;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CadenaTest extends DbTestBase
{
	@Test
	public void CadenaControllerNoEsNull()
	{
		assertTrue("El sistema devolvió null. Debe devolver un controlador.",
				   controllerFactory.createCadenaController() != null);
	}
}
