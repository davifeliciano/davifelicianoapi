package br.edu.infnet.davifelicianoapi.model.domain;

import java.sql.Date;
import java.time.Period;
import java.util.List;

import br.edu.infnet.davifelicianoapi.controller.exceptions.EncargoProjetadoInvalidoException;

public class EncargoProjetado {

    private final Boleto boleto;
    private final Date vencimentoUtil;
    private final Date dataReferencia;
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
        private Date vencimentoUtil;
        private Date dataReferencia;

        public Builder boleto(Boleto boleto) {
            this.boleto = boleto;
            return this;
        }

        public Builder vencimentoUtil(Date vencimentoUtil) {
            this.vencimentoUtil = vencimentoUtil;
            return this;
        }

        public Builder dataReferencia(Date dataReferencia) {
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

    public Date getVencimentoUtil() {
        return vencimentoUtil;
    }

    public Date getDataReferencia() {
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
                .filter(p -> p.getDataPagamento().after(vencimentoUtil)
                        && (p.getDataPagamento().before(dataReferencia) || p.getDataPagamento().equals(dataReferencia)))
                .sorted((p1, p2) -> p1.getDataPagamento().compareTo(p2.getDataPagamento()))
                .toList();
    }

    private void calcularSaldoDividaInicial() {
        saldoDivida = boleto.getValor() - boleto.getPagamentos().stream()
                .filter(p -> p.getDataPagamento().before(vencimentoUtil) || p.getDataPagamento().equals(vencimentoUtil))
                .reduce(0.0, (subtotal, pagamento) -> subtotal + pagamento.getValor(), Double::sum);
    }

    private void calcularDiasAtraso() {
        if (saldoDivida == 0) {
            return;
        }

        Date dataFinal = dataReferencia;
        double somaPagamentos = 0.0;

        for (Pagamento pagamento : pagamentosAposVencimento) {
            somaPagamentos += pagamento.getValor();

            if (somaPagamentos >= saldoDivida) {
                dataFinal = pagamento.getDataPagamento();
                break;
            }
        }

        diasAtraso = Period.between(vencimentoUtil.toLocalDate(), dataFinal.toLocalDate()).getDays();
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
                .filter(p -> p.getDataPagamento().before(vencimentoUtil) || p.getDataPagamento().equals(vencimentoUtil))
                .reduce(0.0, (subtotal, pagamento) -> subtotal + pagamento.getValor(), Double::sum);

        Date dataInicio = vencimentoUtil;

        for (Pagamento pagamento : pagamentosAposVencimento) {
            int diasAtrasoPagamento = Period
                    .between(dataInicio.toLocalDate(), pagamento.getDataPagamento().toLocalDate()).getDays();

            saldoDivida = saldoDivida * Math.pow((1.0 + boleto.getEncargo().getJurosDiarios()), diasAtrasoPagamento);
            saldoDivida -= pagamento.getValor();
            dataInicio = pagamento.getDataPagamento();
        }

        int diasAtrasoDesdeUltimoPagamento = Period
                .between(dataInicio.toLocalDate(), dataReferencia.toLocalDate()).getDays();

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

        if (dataReferencia.before(vencimentoUtil)) {
            throw new EncargoProjetadoInvalidoException("Data de referência não deve ser anterior ao vencimento útil");
        }

        if (vencimentoUtil.before(boleto.getDataVencimento())) {
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
