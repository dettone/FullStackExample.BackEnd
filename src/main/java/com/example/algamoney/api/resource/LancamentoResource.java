package com.example.algamoney.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler.Error;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.service.LancamentoService;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Controller
@RequestMapping("/lancamentos")
public class LancamentoResource {
	@Autowired
	LancamentoRepository lancamentoRepository;
	@Autowired
	private MessageSource messageSource;
	LancamentoService lancamentoService;
	@Autowired //publicador de eventos de applicacao
	private ApplicationEventPublisher publicher;
@GetMapping
@ResponseBody //salvou minha vida
public List<Lancamento> pesquisar(){
	return lancamentoRepository.findAll();
}

@GetMapping("/{codigo}")

public ResponseEntity<Lancamento> listarPorId(@PathVariable Long codigo){
	Optional <Lancamento> lancamento = lancamentoRepository.findById(codigo);
	return lancamento.isPresent() ? ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
}

@PostMapping("/{codigo}")
public ResponseEntity<Lancamento> criar(@PathVariable Long codigo, @Valid @RequestBody Lancamento lancamento, HttpServletResponse servletResponse){
	Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);	
	publicher.publishEvent(new RecursoCriadoEvent(this, servletResponse, lancamentoSalvo.getCodigo()));	
	return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
}

@ExceptionHandler({PessoaInexistenteOuInativaException.class})
public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex){
	String mensagemUsuario = messageSource.getMessage("mensagem.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
	//Se for nula a causa ele pega a toString senao pega a propria causa
	String mensagemDesenvolvedor = ex.toString();
	List<Error> erros = Arrays.asList(new Error(mensagemUsuario, mensagemDesenvolvedor));
	return ResponseEntity.badRequest().body(erros);
}
}
