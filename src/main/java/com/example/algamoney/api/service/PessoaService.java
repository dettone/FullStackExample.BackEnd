package com.example.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {
	@Autowired
	PessoaRepository pessoaRepository;
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = ProcurarPessoaSalva(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva,"codigo");
		return pessoaRepository.save(pessoaSalva);
	}

	public void atualizaPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = ProcurarPessoaSalva(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}
	
	
	public Pessoa ProcurarPessoaSalva(Long codigo) {
		Pessoa pessoaSalva = this.pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return pessoaSalva;
	}
}
