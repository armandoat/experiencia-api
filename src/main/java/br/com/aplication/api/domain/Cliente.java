package br.com.aplication.api.domain;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	@NotEmpty
	private String nome;

	@Column(nullable = false)
	@NotEmpty
	@Email
	private String email;
	
    @Column(nullable = false)
    private LocalDateTime dtInclusao;
    
    @Column
    private LocalDateTime dtAlteracao;

    @OneToMany(mappedBy = "cliente", cascade = {CascadeType.ALL})
    private List<Endereco> enderecos;

}