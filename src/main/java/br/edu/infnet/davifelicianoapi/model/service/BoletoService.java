package br.edu.infnet.davifelicianoapi.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import br.edu.infnet.davifelicianoapi.model.domain.Boleto;

@Service
public class BoletoService implements CrudService<Boleto, Integer> {

    private final Map<Integer, Boleto> boletos = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public Boleto incluir(Boleto boleto) {
        boleto.setId(idGenerator.getAndIncrement());
        boletos.put(boleto.getId(), boleto);
        return boleto;
    }

    @Override
    public void excluir(Integer id) {
        boletos.remove(id);
    }

    @Override
    public Boleto obterPorId(Integer id) {
        return boletos.get(id);
    }

    @Override
    public List<Boleto> obterTodos() {
        return new ArrayList<Boleto>(boletos.values());
    }

}
