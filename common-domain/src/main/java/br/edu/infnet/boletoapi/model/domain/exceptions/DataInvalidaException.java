package br.edu.infnet.boletoapi.model.domain.exceptions;

public class DataInvalidaException extends RuntimeException {

    public DataInvalidaException(String message) {
        super(message);
    }

}
