package br.com.aplication.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@NotEmpty
	private String cep;     
	
	@Column
	private String localidade;
	
	@Column
	private String uf;
	
	@Column
	private String logradouro;
	
	@Column
	private String bairro;
	
	@Column
	@NotEmpty
	private String numero;
	
	@Column
	@NotEmpty
	private String complemento;
	
    @ManyToOne
    @JoinColumn(name="id_cliente")
	private Cliente cliente;
}