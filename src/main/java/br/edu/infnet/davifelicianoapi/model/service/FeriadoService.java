package br.edu.infnet.davifelicianoapi.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.davifelicianoapi.model.clients.FeriadosFeignClient;
import br.edu.infnet.davifelicianoapi.model.domain.Feriado;
import br.edu.infnet.davifelicianoapi.model.exceptions.AnoInvalidoException;

@Service
public class FeriadoService {

    private final FeriadosFeignClient feriadosFeignClient;

    public FeriadoService(FeriadosFeignClient feriadosFeignClient) {
        this.feriadosFeignClient = feriadosFeignClient;
    }

    private static void validarAno(int ano) throws AnoInvalidoException {
        if (ano < 1900 || ano > 2199) {
            throw new AnoInvalidoException("Ano " + ano + " é inválido. Deve estar entre 1900 e 2199.");
        }
    }

    public List<Feriado> obterFeriados(String ano) {
        try {
            int anoInt = Integer.parseInt(ano);
            validarAno(anoInt);
        } catch (NumberFormatException e) {
            throw new AnoInvalidoException("Ano " + ano + " é inválido. Deve ser um número.");
        }

        return feriadosFeignClient.obterFeriados(ano);
    }

}
