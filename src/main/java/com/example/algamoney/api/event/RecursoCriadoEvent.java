package com.example.algamoney.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

/* Cria um evento, que no caso modifica o Location*/
public class RecursoCriadoEvent extends ApplicationEvent{

	private HttpServletResponse response;
	private Long codigo;

	public RecursoCriadoEvent(Object source, HttpServletResponse servletResponse, Long codigo) {
		super(source);
		this.response = servletResponse;
		this.codigo = codigo;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Long getCodigo() {
		return codigo;
	}
	private static final long serialVersionUID = 1L;

}
