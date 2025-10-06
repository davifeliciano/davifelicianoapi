package br.edu.infnet.boletoapi.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.boletoapi.model.domain.Pagamento;
import br.edu.infnet.boletoapi.model.domain.exceptions.BoletoInexistenteException;
import br.edu.infnet.boletoapi.model.domain.exceptions.PagamentoInexistenteException;
import br.edu.infnet.boletoapi.model.domain.exceptions.PagamentoInvalidoException;
import br.edu.infnet.boletoapi.repository.BoletoRepository;
import br.edu.infnet.boletoapi.repository.PagamentoRepository;
import jakarta.transaction.Transactional;

@Service
public class PagamentoService implements CrudService<Pagamento, Integer> {

    private final PagamentoRepository pagamentoRepository;
    private final BoletoRepository boletoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, BoletoRepository boletoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.boletoRepository = boletoRepository;
    }

    @Override
    @Transactional
    public Pagamento incluir(Pagamento pagamento) {
        if (pagamento.getId() != null) {
            throw new PagamentoInvalidoException("ID deve ser nulo ao incluir um novo pagamento");
        }

        pagamentoRepository.save(pagamento);
        return pagamento;
    }

    @Override
    @Transactional
    public Pagamento alterar(Integer id, Pagamento pagamento) {
        if (!pagamentoRepository.existsById(id)) {
            throw new PagamentoInexistenteException("Pagamento não encontrado com id " + id);
        }

        pagamento.setId(id);
        return pagamentoRepository.save(pagamento);
    }

    @Override
    @Transactional
    public void excluir(Integer id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new PagamentoInexistenteException("Pagamento não encontrado com id " + id);
        }

        pagamentoRepository.deleteById(id);
    }

    @Override
    public Pagamento obterPorId(Integer id) throws PagamentoInexistenteException {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new PagamentoInexistenteException("Pagamento não encontrado com id " + id));

        return pagamento;
    }

    @Override
    public List<Pagamento> obterTodos() {
        return pagamentoRepository.findAll();
    }

    public List<Pagamento> obterPorBoletoId(Integer boletoId) {
        if (!boletoRepository.existsById(boletoId)) {
            throw new BoletoInexistenteException("Boleto não encontrado com id " + boletoId);
        }

        return pagamentoRepository.findByBoletoId(boletoId);
    }

    public List<Pagamento> obterPorDataPagamento(String from, String to) {
        try {
            if (from == null && to != null) {
                return pagamentoRepository.findByDataPagamentoLessThanEqual(LocalDate.parse(to));
            }

            if (from != null && to == null) {
                return pagamentoRepository.findByDataPagamentoGreaterThanEqual(LocalDate.parse(from));
            }

            if (from != null && to != null) {
                return pagamentoRepository.findByDataPagamentoBetween(LocalDate.parse(from), LocalDate.parse(to));
            }

            return pagamentoRepository.findAll();
        } catch (IllegalArgumentException e) {
            throw new PagamentoInvalidoException("Datas inválidas. Formato esperado: AAAA-MM-DD");
        }
    }

}
