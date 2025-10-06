package br.edu.infnet.boletoapi.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import br.edu.infnet.boletoapi.model.domain.Boleto;
import br.edu.infnet.boletoapi.model.domain.EncargoProjetado;
import br.edu.infnet.boletoapi.model.domain.Feriado;
import br.edu.infnet.boletoapi.model.domain.exceptions.BoletoJaPagoException;
import br.edu.infnet.boletoapi.repository.BoletoRepository;
import br.edu.infnet.boletoapi.model.domain.exceptions.BoletoInexistenteException;
import br.edu.infnet.boletoapi.model.domain.exceptions.BoletoInvalidoException;

@Service
public class BoletoService implements CrudService<Boleto, Integer> {

    private final BoletoRepository boletoRepository;
    private final FeriadoService feriadoService;

    public BoletoService(BoletoRepository boletoRepository, FeriadoService feriadoService) {
        this.boletoRepository = boletoRepository;
        this.feriadoService = feriadoService;
    }

    @Override
    @Transactional
    public Boleto incluir(Boleto boleto) throws BoletoInvalidoException {
        if (boleto.getId() != null) {
            throw new BoletoInvalidoException("ID deve ser nulo ao incluir um novo boleto");
        }

        boletoRepository.save(boleto);
        return boleto;
    }

    @Override
    @Transactional
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
    @Transactional
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

    public List<Boleto> obterPorDataVencimento(String from, String to) {
        try {
            if (from == null && to != null) {
                return boletoRepository.findByDataVencimentoLessThanEqual(LocalDate.parse(to));
            }

            if (from != null && to == null) {
                return boletoRepository.findByDataVencimentoGreaterThanEqual(LocalDate.parse(from));
            }

            if (from != null && to != null) {
                return boletoRepository.findByDataVencimentoBetween(LocalDate.parse(from), LocalDate.parse(to));
            }

            return boletoRepository.findAll();
        } catch (IllegalArgumentException e) {
            throw new BoletoInvalidoException("Datas inválidas. Formato esperado: AAAA-MM-DD");
        }
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().getValue() >= 6;
    }

    private static boolean isFeriado(LocalDate date, List<Feriado> feriados) {
        for (Feriado feriado : feriados) {
            if (feriado.getData().isEqual(date)) {
                return true;
            }
        }

        return false;
    }

    public EncargoProjetado calcularEncargosProjetadosPorId(Integer id) {
        Boleto boleto = obterPorId(id);
        String ano = Integer.toString(boleto.getDataVencimento().getYear());
        List<Feriado> feriados = feriadoService.obterFeriados(ano);

        LocalDate vencimentoUtil = boleto.getDataVencimento();
        boolean isFeriado = isFeriado(vencimentoUtil, feriados);
        boolean isWeekend = isWeekend(vencimentoUtil);

        while (isWeekend || isFeriado) {
            vencimentoUtil = vencimentoUtil.plusDays(1);
            isFeriado = isFeriado(vencimentoUtil, feriados);
            isWeekend = isWeekend(vencimentoUtil);
        }

        return EncargoProjetado.builder().boleto(boleto).vencimentoUtil(vencimentoUtil)
                .dataReferencia(LocalDate.now()).build();

    }

}
