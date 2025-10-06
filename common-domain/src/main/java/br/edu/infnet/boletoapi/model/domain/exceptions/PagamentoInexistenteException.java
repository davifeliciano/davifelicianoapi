package br.edu.infnet.boletoapi.model.domain.exceptions;

public class PagamentoInexistenteException extends RuntimeException {

    public PagamentoInexistenteException(String message) {
        super(message);
    }

}
