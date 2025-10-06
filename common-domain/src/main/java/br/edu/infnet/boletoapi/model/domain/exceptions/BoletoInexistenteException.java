package br.edu.infnet.boletoapi.model.domain.exceptions;

public class BoletoInexistenteException extends RuntimeException {

    public BoletoInexistenteException(String message) {
        super(message);
    }

}
