package org.tds.sgh.system;

public interface IControllerFactory
{
	// Operations ---------------------------------------------------------------------------------
	
	ICadenaController createCadenaController();
	
	IHacerReservaController createHacerReservaController();
	
	IModificarReservaController createModificarReservaController();
	
	ICancelarReservaController createCancelarReservaController();

	ITomarReservaController createTomarReservaController();
}
