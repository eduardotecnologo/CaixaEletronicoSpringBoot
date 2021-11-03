package com.caixacore.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.caixacore.exception.CadastroDuplicadoException;
import com.caixacore.exception.CaixaEletronicoNotFoundException;
import com.caixacore.exception.ValorInvalidoException;
import com.caixacore.model.Message;

import org.springframework.http.HttpStatus;

@ControllerAdvice
public class CaixaEletronicoControllerAdvice {
	
	@ResponseBody
	@ExceptionHandler(ValorInvalidoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	Message valorInvalidoExceptionHandler(ValorInvalidoException ex) {
		return new Message("error", "Valor inválido:" + ex.getMessage());
	}
	@ResponseBody
	@ExceptionHandler(CaixaEletronicoNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	Message caixaEletronicoExceptionHandler(CaixaEletronicoNotFoundException ex) {
		return new Message("error", "Caixa Eletrônico não encontrado:" + ex.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(CadastroDuplicadoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	Message cadastroDuplicadoExceptionHandler(CadastroDuplicadoException ex) {
		return new Message("error", "Cadastro duplicado:" + ex.getMessage());
	}

}
