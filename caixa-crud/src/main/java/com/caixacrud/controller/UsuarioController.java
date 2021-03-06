package com.caixacrud.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.caixacrud.exception.ValorInvalidoException;
import com.caixacrud.model.Usuario;
import com.caixacrud.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> salvarUsuarioESaldo(@RequestBody Usuario usuario,
			UriComponentsBuilder ucBuilder) {
		usuarioService.save(usuario);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/usuarios/{id}")
				.buildAndExpand(usuario.getId()).toUri());

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Collection<Usuario> buscarUsuarios() {
		return usuarioService.findAll();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public Usuario buscarUsuarios(@PathVariable Long id) {
		return usuarioService.findOne(id);
	}

	@RequestMapping(path = "/search", method = RequestMethod.GET)
	public Collection<Usuario> search(@RequestParam("q") String queryTerm) {
		Collection<Usuario> usuarios = usuarioService.search("%" + queryTerm + "%");
		return usuarios == null ? new ArrayList<>() : usuarios;
	}
	
	@RequestMapping(path = "/{id}/sacar/{valor}", method = RequestMethod.GET)
	public ResponseEntity<?>  sacar(@PathVariable Long id, @PathVariable Double valor) {
		try {
			usuarioService.sacar(id, valor);
		} catch (ValorInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("ok",HttpStatus.OK);
	}

}
