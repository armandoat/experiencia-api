# Cliente API

### Requisitos

1. JDK 8

### Rodando
1. Clone o projeto: `https://github.com/alan-hac/cliente-crud-avaliacao.git`
1. Entre na pasta `cliente-crud-avaliacao` e execute: `mvnw spring-boot:run` (windows) ou `mvn spring-boot:run` (linux) 
1. Acesse: `http://localhost:8080/clientes`

### CRUD ###
### Incluir um cliente
1. Endpoint: localhost:8080/clientes
1. Método HTTP: POST
1. Request Payload: 
{
	"nome":"Preencher o nome",
	"email":"Preencher o e-mail (nome@email.com)"
}

## Atualizar o nome de um cliente
1. Endpoint: localhost:8080/clientes
1. Método HTTP: PUT
1. Request Payload: 
{
	"id":"Preencher com Id válido",
	"nome":"Preencher novo nome"
}

## Excluir um cliente
1. Endpoint: localhost:8080/clientes/{Informar o ID do cliente}
1. Método HTTP: DELETE

## Listar os clientes cadastrados
1. Endpoint: localhost:8080/clientes
1. Método HTTP: GET
