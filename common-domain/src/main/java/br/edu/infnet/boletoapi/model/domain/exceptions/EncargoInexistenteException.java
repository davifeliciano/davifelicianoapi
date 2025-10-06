package br.edu.infnet.boletoapi.model.domain.exceptions;

public class EncargoInexistenteException extends RuntimeException {

    public EncargoInexistenteException(String message) {
        super(message);
    }

}
