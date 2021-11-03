package com.caixacrud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.caixacrud.exception.CadastroDuplicadoException;
import com.caixacrud.exception.ValorInvalidoException;
import com.caixacrud.model.Message;

@ControllerAdvice
public class CaixaCrudControllerAdvice {
	
	@ResponseBody
	@ExceptionHandler(ValorInvalidoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	Message valorInvalidoExceptionHandler(ValorInvalidoException ex) {
		return new Message("error", ex.getMessage());
	}
	@ResponseBody
	@ExceptionHandler(CadastroDuplicadoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	Message caixaEletronicoExceptionHandler(CadastroDuplicadoException ex) {
		return new Message("error", "Cadastro duplicado: " + ex.getMessage());
	}

}
