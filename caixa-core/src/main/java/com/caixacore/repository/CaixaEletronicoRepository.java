package com.caixacore.repository;

import com.caixacore.model.CaixaEletronico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CaixaEletronicoRepository extends JpaRepository<CaixaEletronico, Long>{

	CaixaEletronico findByNome(String nome);

}
