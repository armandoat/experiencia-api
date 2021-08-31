package br.com.aplication.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.aplication.api.domain.Cliente;
import br.com.aplication.api.service.ClienteService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("clientes")
@Slf4j
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping()
	public ResponseEntity<Object> listarClientes(Pageable pageable) {
		
		log.info("Inicio Listar Cliente");
		
		Page<Cliente> clientes = clienteService.buscarTodos(pageable);
		
		log.info("Fim Listar Cliente");
		
		return ResponseEntity.ok(clientes);
	}

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<Object> incluir(@RequestBody Cliente cliente) {

		log.info("Inicio Incluir Cliente");
		
		Cliente novoCliente = clienteService.incluir(cliente);

		log.info("Fim Incluir Cliente");
		
		return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Object> alterar(@RequestBody Cliente cliente) {

		log.info("Inicio Atualizar Cliente");
		
		clienteService.atualizar(cliente);

		log.info("Fim Atualizar Cliente");
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> excluir(@PathVariable Long id) {
		
		log.info("Inicio Excluir Cliente");
		
		clienteService.excluir(id);
		
		log.info("Fim Excluir Cliente");
		
		return ResponseEntity.noContent().build();
	}
}
