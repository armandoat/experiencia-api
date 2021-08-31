package br.com.aplication.api.service;

import br.com.aplication.api.domain.Endereco;

public interface ViacepService {
	
	Endereco buscarCepPorNumeroCep(String cep);
}
