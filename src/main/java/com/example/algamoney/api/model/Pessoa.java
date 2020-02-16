package com.example.algamoney.api.model;

import java.beans.Transient;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "pessoa")
public class Pessoa {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long codigo;
@NotNull
@Size(min = 3, max = 30)
private String nome;
@Embedded
private Endereco endereco;
@NotNull
private Boolean ativo;
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((ativo == null) ? 0 : ativo.hashCode());
	return result;
}
@JsonIgnore
@Transient
public Boolean isAtivo() {
	return !this.ativo;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Pessoa other = (Pessoa) obj;
	if (ativo == null) {
		if (other.ativo != null)
			return false;
	} else if (!ativo.equals(other.ativo))
		return false;
	return true;
}
public Long getCodigo() {
	return codigo;
}
public void setCodigo(Long codigo) {
	this.codigo = codigo;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public Endereco getEndereco() {
	return endereco;
}
public void setEndereco(Endereco endereco) {
	this.endereco = endereco;
}
public Boolean getAtivo() {
	return ativo;
}
public void setAtivo(Boolean ativo) {
	this.ativo = ativo;
}
}
