package br.edu.infnet.boletoapi.model.domain.exceptions;

public class BoletoJaPagoException extends RuntimeException {

    public BoletoJaPagoException(String message) {
        super(message);
    }

}
