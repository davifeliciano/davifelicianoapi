package br.edu.infnet.boletoapi.model.domain.exceptions;

public class BoletoInvalidoException extends RuntimeException {

    public BoletoInvalidoException(String message) {
        super(message);
    }

}
