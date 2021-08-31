package br.com.aplication.api.exception.handler;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.aplication.api.exception.CampoObrigatorioException;
import br.com.aplication.api.exception.EntidadeNaoEncontradaException;
import br.com.aplication.api.exception.apierror.ApiError;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
    		javax.validation.ConstraintViolationException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        return buildResponseEntity(apiError);
    }
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	protected ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex) {
		ApiError apiError = new ApiError(NOT_FOUND);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(CampoObrigatorioException.class)
	protected ResponseEntity<Object> handleCampoObrigatorioException(CampoObrigatorioException ex) {
		ApiError apiError = new ApiError(BAD_REQUEST);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
