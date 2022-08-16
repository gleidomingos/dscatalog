package com.gbsolution.dscatalog.services.exeptions;

public class ResourceNotFoundExeption extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundExeption(String message ){
		super(message);
	}

}
