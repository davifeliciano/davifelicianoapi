package br.edu.infnet.boletoapi.model.domain.exceptions;

public class PagamentoInvalidoException extends RuntimeException {

    public PagamentoInvalidoException(String message) {
        super(message);
    }

}
