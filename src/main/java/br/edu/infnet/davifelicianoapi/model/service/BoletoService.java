package br.edu.infnet.davifelicianoapi.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.davifelicianoapi.model.domain.Boleto;
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoJaPagoException;
import br.edu.infnet.davifelicianoapi.model.repository.BoletoRepository;
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoInexistenteException;
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoInvalidoException;
import br.edu.infnet.davifelicianoapi.utils.DateValidator;

@Service
public class BoletoService implements CrudService<Boleto, Integer> {

    private final BoletoRepository boletoRepository;

    public BoletoService(BoletoRepository boletoRepository) {
        this.boletoRepository = boletoRepository;
    }

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

        validar(boleto);
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
