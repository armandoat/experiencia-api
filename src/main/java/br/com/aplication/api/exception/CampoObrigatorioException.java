package br.com.aplication.api.exception;

public class CampoObrigatorioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public CampoObrigatorioException(String mensagem) {
        super(mensagem);
    }

    public CampoObrigatorioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
