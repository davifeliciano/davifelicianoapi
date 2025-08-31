package br.edu.infnet.davifelicianoapi.model.exceptions;

public class PagamentoInexistenteException extends RuntimeException {

    public PagamentoInexistenteException(String message) {
        super(message);
    }

}
