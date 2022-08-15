package com.gbsolution.dscatalog.services.exeptions;

public class ServiceNotFoundExeption extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ServiceNotFoundExeption(String message ){
		super(message);
	}

}
