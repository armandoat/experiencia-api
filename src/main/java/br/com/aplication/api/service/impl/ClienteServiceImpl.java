package br.com.aplication.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import br.com.aplication.api.domain.Cliente;
import br.com.aplication.api.domain.Endereco;
import br.com.aplication.api.exception.CampoObrigatorioException;
import br.com.aplication.api.exception.EntidadeNaoEncontradaException;
import br.com.aplication.api.repository.ClienteRepository;
import br.com.aplication.api.service.ClienteService;
import br.com.aplication.api.service.ViacepService;

@Service
public class ClienteServiceImpl implements ClienteService {

	private static final String CLIENTENAOENCONTRADO = "Cliente não encontrado";

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ViacepService viaCepService;

	@Override
	public Page<Cliente> buscarTodos(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	@Override
	public Cliente incluir(Cliente cliente) {
		this.validarInclusao(cliente);
		cliente.setDtInclusao(LocalDateTime.now());
		this.consultarCep(cliente);
		return clienteRepository.save(cliente);
	}
	
	private List<Endereco> consultarCep(Cliente cliente) {
		if(!CollectionUtils.isEmpty(cliente.getEnderecos())) {
			cliente.getEnderecos().forEach(e -> {  
				Endereco endObtido = viaCepService.buscarCepPorNumeroCep(e.getCep());
		        //
		    	modelMapper.typeMap(Endereco.class,Endereco.class).addMappings(mapper -> {
			    	//
		    		if(StringUtils.isEmpty(endObtido.getNumero()))
		    			mapper.skip(Endereco::setNumero);
		    		//
		    		if(StringUtils.isEmpty(endObtido.getComplemento()))
		    			mapper.skip(Endereco::setComplemento);
		    		//
		    	});
				modelMapper.map(endObtido, e);
			});

		}
		return cliente.getEnderecos();
	}

	private void validarInclusao(Cliente cliente) {
		if (StringUtils.isEmpty(cliente.getNome())) {
			throw new CampoObrigatorioException("Nome vazio!");
		}
		if (StringUtils.isEmpty(cliente.getEmail())) {
			throw new CampoObrigatorioException("Email vazio!");
		}
		if(!CollectionUtils.isEmpty(cliente.getEnderecos())) {
			List<Endereco> endComCepNulo = cliente.getEnderecos().stream().filter(e -> StringUtils.isEmpty(e.getCep()) || StringUtils.isEmpty(e.getNumero())).collect(Collectors.toList());
			if(!endComCepNulo.isEmpty()) {
				throw new CampoObrigatorioException("Cep/Numero vazio!");
			}
		}
	}

	@Override
	public Cliente atualizar(Cliente cliente) {
		this.validarAlteracao(cliente);
		if (this.existeCliente(cliente)) {
			return clienteRepository.save(this.prepararDadosCliente(cliente));
		} else
			throw new EntidadeNaoEncontradaException(CLIENTENAOENCONTRADO);
	}

	private void validarAlteracao(Cliente cliente) {
		if (Objects.isNull(cliente.getId())) {
			throw new CampoObrigatorioException("Id vazio!");
		}
		if (StringUtils.isEmpty(cliente.getNome()) && StringUtils.isEmpty(cliente.getEmail()))
			throw new CampoObrigatorioException("Eh necessario informar pelo menos um nome ou email para edicao!");
	}

	@Override
	public void excluir(Long id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(CLIENTENAOENCONTRADO));
		clienteRepository.delete(cliente);
	}

	private Cliente prepararDadosCliente(Cliente cliente) {
		Cliente clienteAtualizado = clienteRepository.findById(cliente.getId())
				.orElseThrow(() -> new EntidadeNaoEncontradaException(CLIENTENAOENCONTRADO));
		clienteAtualizado.setDtAlteracao(LocalDateTime.now());
		//
		if (!StringUtils.isEmpty(cliente.getNome()))
			clienteAtualizado.setNome(cliente.getNome());
		//
		if (!StringUtils.isEmpty(cliente.getEmail()))
			clienteAtualizado.setEmail(cliente.getEmail());

		return clienteAtualizado;
	}

	private boolean existeCliente(Cliente cliente) {
		Optional<Cliente> clienteEncontrado = clienteRepository.findById(cliente.getId());
		return clienteEncontrado.isPresent();
	}
}
