package br.com.aplication.api.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public EntidadeNaoEncontradaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
