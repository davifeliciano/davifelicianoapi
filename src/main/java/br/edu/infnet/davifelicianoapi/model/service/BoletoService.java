package br.edu.infnet.davifelicianoapi.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import br.edu.infnet.davifelicianoapi.model.domain.Boleto;
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoJaPagoException;
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoInvalidoException;
import br.edu.infnet.davifelicianoapi.utils.DateValidator;

@Service
public class BoletoService implements CrudService<Boleto, Integer> {

    private final Map<Integer, Boleto> boletos = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    private void validar(Boleto boleto) throws BoletoInvalidoException {
        if (boleto == null) {
            throw new BoletoInvalidoException("Boleto não pode ser nulo");
        }

        if (boleto.getCodigoDeBarras() == null || boleto.getCodigoDeBarras().isEmpty()) {
            throw new BoletoInvalidoException("Código de barras é obrigatório");
        }

        if (boleto.getValor() <= 0) {
            throw new BoletoInvalidoException("Valor deve ser maior que zero");
        }

        if (boleto.getDataVencimento() == null) {
            throw new BoletoInvalidoException("Data de vencimento é obrigatória");
        }

        if (!DateValidator.isValidDate(boleto.getDataVencimento(), "yyyy-MM-dd")) {
            throw new BoletoInvalidoException("Data de vencimento inválida: " + boleto.getDataVencimento());
        }

        // TODO: Adicionar validações de cedente e sacado
    }

    @Override
    public Boleto incluir(Boleto boleto) throws BoletoInvalidoException {
        validar(boleto);

        if (boleto.getId() != null) {
            throw new BoletoInvalidoException("ID deve ser nulo ao incluir um novo boleto");
        }

        boleto.setId(idGenerator.getAndIncrement());
        boletos.put(boleto.getId(), boleto);
        return boleto;
    }

    @Override
    public Boleto excluir(Integer id) throws BoletoJaPagoException {
        if (!boletos.containsKey(id)) {
            throw new BoletoJaPagoException("Boleto não encontrado com id " + id);
        }

        return boletos.remove(id);
    }

    @Override
    public Boleto obterPorId(Integer id) throws BoletoJaPagoException {
        Boleto boleto = boletos.get(id);

        if (boleto == null) {
            throw new BoletoJaPagoException("Boleto não encontrado com id " + id);
        }

        return boleto;
    }

    @Override
    public List<Boleto> obterTodos() {
        return new ArrayList<Boleto>(boletos.values());
    }

    @Override
    public Boleto alterar(Integer id, Boleto boleto) throws BoletoJaPagoException, BoletoInvalidoException {
        if (!boletos.containsKey(id)) {
            throw new BoletoJaPagoException("Boleto não encontrado com id " + id);
        }

        validar(boleto);
        boleto.setId(id);
        return boletos.put(id, boleto);
    }

    public Boleto pagar(Integer id) throws BoletoJaPagoException, BoletoJaPagoException {
        if (!boletos.containsKey(id)) {
            throw new BoletoJaPagoException("Boleto não encontrado com id " + id);
        }

        Boleto boleto = obterPorId(id);

        if (boleto.isPago()) {
            throw new BoletoJaPagoException("Boleto já está pago");
        }

        boleto.setPago(true);
        boletos.put(id, boleto);
        return boleto;
    }

}
