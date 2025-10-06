package br.edu.infnet.boletoapi.model.domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import br.edu.infnet.boletoapi.model.domain.exceptions.EncargoProjetadoInvalidoException;

public class EncargoProjetado {

    private final Boleto boleto;
    private final LocalDate vencimentoUtil;
    private final LocalDate dataReferencia;
    private int diasAtraso;
    private double saldoDivida;
    private double multaAtrasoFixa;
    private double multaAtrasoPercentual;
    private double totalMultaAtraso;
    private double totalEncargos;
    private List<Pagamento> pagamentosAposVencimento;

    private EncargoProjetado(Builder builder) {
        this.boleto = builder.boleto;
        this.vencimentoUtil = builder.vencimentoUtil;
        this.dataReferencia = builder.dataReferencia;
        validar();
        calcular();
    }

    public static class Builder {
        private Boleto boleto;
        private LocalDate vencimentoUtil;
        private LocalDate dataReferencia;

        public Builder boleto(Boleto boleto) {
            this.boleto = boleto;
            return this;
        }

        public Builder vencimentoUtil(LocalDate vencimentoUtil) {
            this.vencimentoUtil = vencimentoUtil;
            return this;
        }

        public Builder dataReferencia(LocalDate dataReferencia) {
            this.dataReferencia = dataReferencia;
            return this;
        }

        public EncargoProjetado build() {
            return new EncargoProjetado(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public LocalDate getVencimentoUtil() {
        return vencimentoUtil;
    }

    public LocalDate getDataReferencia() {
        return dataReferencia;
    }

    public int getDiasAtraso() {
        return diasAtraso;
    }

    public double getSaldoDivida() {
        return saldoDivida;
    }

    public double getMultaAtrasoFixa() {
        return multaAtrasoFixa;
    }

    public double getMultaAtrasoPercentual() {
        return multaAtrasoPercentual;
    }

    public double getTotalMultaAtraso() {
        return totalMultaAtraso;
    }

    public double getTotalEncargos() {
        return totalEncargos;
    }

    private void ordenarPagamentosAposVencimento() {
        pagamentosAposVencimento = boleto.getPagamentos().stream()
                .filter(p -> p.getDataPagamento().isAfter(vencimentoUtil)
                        && (p.getDataPagamento().isBefore(dataReferencia)
                                || p.getDataPagamento().equals(dataReferencia)))
                .sorted((p1, p2) -> p1.getDataPagamento().compareTo(p2.getDataPagamento()))
                .toList();
    }

    private void calcularSaldoDividaInicial() {
        saldoDivida = boleto.getValor() - boleto.getPagamentos().stream()
                .filter(p -> p.getDataPagamento().isBefore(vencimentoUtil)
                        || p.getDataPagamento().equals(vencimentoUtil))
                .reduce(0.0, (subtotal, pagamento) -> subtotal + pagamento.getValor(), Double::sum);
    }

    private void calcularDiasAtraso() {
        if (saldoDivida == 0) {
            return;
        }

        LocalDate dataFinal = dataReferencia;
        double somaPagamentos = 0.0;

        for (Pagamento pagamento : pagamentosAposVencimento) {
            somaPagamentos += pagamento.getValor();

            if (somaPagamentos >= saldoDivida) {
                dataFinal = pagamento.getDataPagamento();
                break;
            }
        }

        diasAtraso = Period.between(vencimentoUtil, dataFinal).getDays();
    }

    private void calcularMultaAtrasoPercentual() {
        if (diasAtraso > 0) {
            multaAtrasoPercentual = boleto.getValor() * boleto.getEncargo().getMultaAtrasoPercentual();
        }
    }

    private void calcularMultaAtrasoFixa() {
        if (diasAtraso > 0) {
            multaAtrasoFixa = boleto.getEncargo().getMultaAtrasoFixa();
        }
    }

    private void calcularTotalMultaAtraso() {
        totalMultaAtraso = multaAtrasoFixa + multaAtrasoPercentual;
    }

    private void calcularSaldoDivida() {
        saldoDivida = boleto.getValor() - boleto.getPagamentos().stream()
                .filter(p -> p.getDataPagamento().isBefore(vencimentoUtil)
                        || p.getDataPagamento().equals(vencimentoUtil))
                .reduce(0.0, (subtotal, pagamento) -> subtotal + pagamento.getValor(), Double::sum);

        LocalDate dataInicio = vencimentoUtil;

        for (Pagamento pagamento : pagamentosAposVencimento) {
            int diasAtrasoPagamento = Period
                    .between(dataInicio, pagamento.getDataPagamento()).getDays();

            saldoDivida = saldoDivida * Math.pow((1.0 + boleto.getEncargo().getJurosDiarios()), diasAtrasoPagamento);
            saldoDivida -= pagamento.getValor();
            dataInicio = pagamento.getDataPagamento();
        }

        int diasAtrasoDesdeUltimoPagamento = Period
                .between(dataInicio, dataReferencia).getDays();

        saldoDivida = saldoDivida
                * Math.pow((1.0 + boleto.getEncargo().getJurosDiarios()), diasAtrasoDesdeUltimoPagamento);
    }

    private void calcularTotalEncargos() {
        totalEncargos = saldoDivida + totalMultaAtraso - boleto.getValor();
    }

    private void validar() throws EncargoProjetadoInvalidoException {
        if (boleto == null) {
            throw new EncargoProjetadoInvalidoException("Boleto não deve ser nulo");
        }

        if (vencimentoUtil == null) {
            throw new EncargoProjetadoInvalidoException("Vencimento útil não deve ser nulo");
        }

        if (dataReferencia == null) {
            throw new EncargoProjetadoInvalidoException("Data de referência não deve ser nula");
        }

        if (boleto.getEncargo() == null) {
            throw new EncargoProjetadoInvalidoException("Encargo do boleto não deve ser nulo");
        }

        if (dataReferencia.isBefore(vencimentoUtil)) {
            throw new EncargoProjetadoInvalidoException("Data de referência não deve ser anterior ao vencimento útil");
        }

        if (vencimentoUtil.isBefore(boleto.getDataVencimento())) {
            throw new EncargoProjetadoInvalidoException(
                    "Vencimento útil não deve ser anterior ao vencimento do boleto");
        }
    }

    private void calcular() {
        ordenarPagamentosAposVencimento();
        calcularSaldoDividaInicial();
        calcularDiasAtraso();
        calcularMultaAtrasoPercentual();
        calcularMultaAtrasoFixa();
        calcularTotalMultaAtraso();
        calcularSaldoDivida();
        calcularTotalEncargos();
    }

}
