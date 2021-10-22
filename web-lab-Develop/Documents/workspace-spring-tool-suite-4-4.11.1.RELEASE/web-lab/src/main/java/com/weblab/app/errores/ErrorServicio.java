package com.weblab.app.errores;

public class ErrorServicio extends Exception{

	
	private static final long serialVersionUID = 1L;

	public ErrorServicio(String msn){
        super(msn);
    }

}
