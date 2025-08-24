package br.edu.infnet.davifelicianoapi.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.davifelicianoapi.model.domain.Boleto;
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoJaPagoException;
import br.edu.infnet.davifelicianoapi.model.repository.BoletoRepository;
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoInexistenteException;
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoInvalidoException;

@Service
public class BoletoService implements CrudService<Boleto, Integer> {

    private final BoletoRepository boletoRepository;

    public BoletoService(BoletoRepository boletoRepository) {
        this.boletoRepository = boletoRepository;
    }

    @Override
    public Boleto incluir(Boleto boleto) throws BoletoInvalidoException {
        if (boleto.getId() != null) {
            throw new BoletoInvalidoException("ID deve ser nulo ao incluir um novo boleto");
        }

        boletoRepository.save(boleto);
        return boleto;
    }

    @Override
    public void excluir(Integer id) throws BoletoInexistenteException {
        if (!boletoRepository.existsById(id)) {
            throw new BoletoInexistenteException("Boleto não encontrado com id " + id);
        }

        boletoRepository.deleteById(id);
    }

    @Override
    public Boleto obterPorId(Integer id) throws BoletoInexistenteException {
        Boleto boleto = boletoRepository.findById(id)
                .orElseThrow(() -> new BoletoInexistenteException("Boleto não encontrado com id " + id));

        return boleto;
    }

    @Override
    public List<Boleto> obterTodos() {
        return boletoRepository.findAll();
    }

    @Override
    public Boleto alterar(Integer id, Boleto boleto) throws BoletoInexistenteException, BoletoInvalidoException {
        if (!boletoRepository.existsById(id)) {
            throw new BoletoInexistenteException("Boleto não encontrado com id " + id);
        }

        boleto.setId(id);
        return boletoRepository.save(boleto);
    }

    public Boleto pagar(Integer id) throws BoletoInexistenteException, BoletoJaPagoException {
        Boleto boleto = obterPorId(id);

        if (boleto.isPago()) {
            throw new BoletoJaPagoException("Boleto já está pago");
        }

        boleto.setPago(true);
        return boletoRepository.save(boleto);
    }

}
