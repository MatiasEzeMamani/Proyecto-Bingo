package com.matias.bingo.exceptions;

public class OurException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public OurException(String message) {
		super(message);
	}
}