package com.caixacore.service;

import java.util.List;

import com.caixacore.exception.CadastroDuplicadoException;
import com.caixacore.exception.CaixaEletronicoNotFoundException;
import com.caixacore.exception.ValorInvalidoException;
import com.caixacore.model.CaixaEletronico;
import com.caixacore.model.Nota;
import com.caixacore.model.Usuario;
import com.caixacore.repository.CaixaEletronicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CaixaEletronicoService {

	@Autowired
	private CaixaEletronicoRepository caixaEletronicoRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public void depositar(String nome, List<Nota> notas) {
		CaixaEletronico ce = caixaEletronicoRepository.findByNome(nome);
		if(ce == null){
			ce  = new CaixaEletronico(nome);
		}
		ce.depositarNotas(notas);
		caixaEletronicoRepository.save(ce);
	}

	public List<Nota> sacar(String nome, Long idUsuario, int valor) {
		CaixaEletronico ce = caixaEletronicoRepository.findByNome(nome);
		if(ce == null){
			throw new CaixaEletronicoNotFoundException(nome);
		}
		Usuario usuario = restTemplate.getForObject("http://localhost:8082/usuarios/" + idUsuario, Usuario.class);
		if(usuario == null){
			throw new CaixaEletronicoNotFoundException("Usuario não encontrado: " + idUsuario);
		}
		if(!usuario.temSaldo(valor)){
			throw new ValorInvalidoException("Saldo insuficiente: " + usuario.getNome());
		}
		List<Nota> notas = ce.sacar(valor);
		caixaEletronicoRepository.save(ce);
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8082/usuarios/" + idUsuario+ "/sacar/"+valor, String.class);
		if(!responseEntity.getStatusCode().is2xxSuccessful()){
			throw new ValorInvalidoException(responseEntity.getBody().toString());
		}
		return notas;
	}

	public void criar(CaixaEletronico caixaEletronico) {
		CaixaEletronico ce = caixaEletronicoRepository.findByNome(caixaEletronico.getNome());
		if(ce != null){
			throw new CadastroDuplicadoException("Já existe cadastro com o nome " + caixaEletronico.getNome());
		}
		caixaEletronicoRepository.save(caixaEletronico);
	}

	public List<CaixaEletronico> findAll() {
		return caixaEletronicoRepository.findAll();
	}

}
